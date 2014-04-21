package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.connector.DatabaseHandler;

public class AttendanceDAO {
	// Table Name
	public static final String TABLE_ATTENDANCE = "attendance";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public AttendanceDAO(Context context) {
		dbHelper = new DatabaseHandler(context);
	}

	// open the database connection
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	// close the database connection
	public void close() {
		dbHelper.close();
	}
	
	public static final String KEY_ATTENDANCE_ID = "attendance_id";
	public static final String FKEY_CLASS_ID = "course_id";
	public static final String IS_ATTENDEND = "is_attended";
	public static final String IS_CANCELLED = "is_cancelled";
	public static final String ATTENDANCE_DATE = "attendance_date";
	
	public static final String CREATE_TABLE_SCHEDULE = AttendanceDAO.createAttendanceTableScript();
	
	private static final String createAttendanceTableScript(){
		StringBuilder attendanceTableScript = new StringBuilder();
		attendanceTableScript.append("CREATE TABLE "+TABLE_ATTENDANCE+" (");
		attendanceTableScript.append(KEY_ATTENDANCE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,");
		attendanceTableScript.append(FKEY_CLASS_ID +" INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES+" ("+ClassesDAO.KEY_CLASS_ID+" ) " +
				"ON DELETE CASCADE ON UPDATE CASCADE,");
		attendanceTableScript.append(IS_ATTENDEND + " TEXT NOT NULL,");
		attendanceTableScript.append(IS_CANCELLED + " TEXT NOT NULL,");
		attendanceTableScript.append(ATTENDANCE_DATE + " DATETIME NOT NULL ,");
		attendanceTableScript.append("CONSTRAINT attendace_attend_check CHECK ( lower("+ IS_ATTENDEND +" )  in ('y','n')),");
		attendanceTableScript.append("CONSTRAINT attendace_cancel_check CHECK ( lower("+ IS_CANCELLED +" )  in ('y','n')));");
		return attendanceTableScript.toString();
	}
}
