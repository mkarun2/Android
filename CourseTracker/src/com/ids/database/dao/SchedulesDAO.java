package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.connector.DatabaseHandler;

public class SchedulesDAO {
	// Table Name
	public static final String TABLE_SCHEDULES = "schedules";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public SchedulesDAO(Context context) {
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
	
	public static final String KEY_SCHEDULE_ID = "schedule_id";
	public static final String FKEY_CLASS_ID = "course_id";
	public static final String CLASS_DAY = "class_day";
	public static final String SCHEDULE_START_TIME = "start_time";
	public static final String SCHEDULE_END_TIME = "end_time";
	
	public static final String CREATE_TABLE_SCHEDULE = SchedulesDAO.createScheduleTableScript();
	
	private static final String createScheduleTableScript(){
		StringBuilder scheduleTableScript = new StringBuilder();
		scheduleTableScript.append("CREATE TABLE "+TABLE_SCHEDULES +" (");
		scheduleTableScript.append(KEY_SCHEDULE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,");
		scheduleTableScript.append(FKEY_CLASS_ID +" INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES+" ("+ClassesDAO.KEY_CLASS_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE,");
		scheduleTableScript.append(CLASS_DAY + " TEXT NOT NULL,");
		scheduleTableScript.append(SCHEDULE_START_TIME + " DATETIME NOT NULL,");
		scheduleTableScript.append(SCHEDULE_END_TIME + " DATETIME NOT NULL ,");
		scheduleTableScript.append("CONSTRAINT schedule_date_check CHECK ( " + SCHEDULE_START_TIME +" <= "+ SCHEDULE_END_TIME +"), ");
		scheduleTableScript.append("CONSTRAINT schedule_day_check CHECK ( lower("+ CLASS_DAY +")  in "+
				"('monday','tuesday','wednesday','thursday','friday','saturday','sunday')));");
		
		return scheduleTableScript.toString();
	}
}
