package com.ids.database.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ids.database.connector.DatabaseHandler;
import com.ids.database.model.Attendance;

public class AttendanceDAO {
	// Table Name
	public static final String TABLE_ATTENDANCE = "attendance";
	
	private static final String LOG = "AttendancesDAO";

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
	
	// insert a given a attendance object
	public long insertAttendance(Attendance attendanceObj) {
		if (attendanceObj == null)
			return (long) -1;

		ContentValues values = new ContentValues();
		values.put(FKEY_CLASS_ID, attendanceObj.getCourse_id());
		values.put(IS_ATTENDEND, attendanceObj.getIs_attended());
		values.put(IS_CANCELLED, attendanceObj.getIs_cancelled());
		values.put(ATTENDANCE_DATE, attendanceObj.getAttendance_date().toString());
		
		// insert row
		return database.insert(TABLE_ATTENDANCE, null, values);
	}
	
	public int updateAttendance(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	public int deleteAllActivities() {				
		return database.delete(TABLE_ATTENDANCE,"1",null);
    }
	
	public int deleteAttendance(Integer attendance_id) {		
		if(attendance_id == null) return -1;
		
		return database.delete(TABLE_ATTENDANCE, KEY_ATTENDANCE_ID + " = ?",
                new String[] { String.valueOf(attendance_id) });
    }
	
	public List<Attendance> getAllAttendance() throws ParseException{
        List<Attendance> attendanceList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_ATTENDANCE; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	attendanceList = new ArrayList<Attendance>();
            do {
            	Attendance attendance = new Attendance();
            	attendance.setAttendance_id(c.getInt(c.getColumnIndex(KEY_ATTENDANCE_ID)));
            	attendance.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
            	attendance.setIs_attended(c.getString(c.getColumnIndex(IS_ATTENDEND)));
            	attendance.setIs_cancelled(c.getString(c.getColumnIndex(IS_CANCELLED)));
            	attendance.setAttendance_date(sdf.parse(c.getString(c.getColumnIndex(ATTENDANCE_DATE))));
 
                // adding to student list
                attendanceList.add(attendance);
            } while (c.moveToNext());
        } 
        return attendanceList;
    }
	
	public Attendance getAttendance(Integer attendance_id) throws ParseException{
		if(attendance_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_ATTENDANCE + " WHERE " + KEY_ATTENDANCE_ID + " = " + attendance_id; 
        Log.e(LOG, "getAttendance(Integer attendance_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Attendance attendance = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        if (c != null){
            c.moveToFirst();  
            attendance = new Attendance();
        	attendance.setAttendance_id(c.getInt(c.getColumnIndex(KEY_ATTENDANCE_ID)));
        	attendance.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	attendance.setIs_attended(c.getString(c.getColumnIndex(IS_ATTENDEND)));
        	attendance.setIs_cancelled(c.getString(c.getColumnIndex(IS_CANCELLED)));
        	attendance.setAttendance_date(sdf.parse(c.getString(c.getColumnIndex(ATTENDANCE_DATE))));
        }   
        return attendance;
    }

}
