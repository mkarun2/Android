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
import com.ids.database.model.OfficeHours;
import com.ids.database.model.Schedules;

public class SchedulesDAO {
	// Table Name
	public static final String TABLE_SCHEDULES = "schedules";
	private static final String LOG = "SchedulesDAO";

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
	
	public long insertSchedule(Schedules scheduleObj) {
		if (scheduleObj == null)
			return (long) -1;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
		ContentValues values = new ContentValues();
		values.put(FKEY_CLASS_ID, scheduleObj.getCourse_id());
		values.put(CLASS_DAY, scheduleObj.getClass_day().toString());
		values.put(SCHEDULE_START_TIME, sdf.format(scheduleObj.getStart_time()));
		values.put(SCHEDULE_END_TIME, sdf.format(scheduleObj.getEnd_time()));
		
		// insert row
		return database.insert(TABLE_SCHEDULES, null, values);
	}

	
	public int updateSchedulesRawQuery(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	public int deleteAllSchedules() {				
		return database.delete(TABLE_SCHEDULES,"1",null);
    }
	
	public int deleteSchedule(Integer schedule_id) {		
		if(schedule_id == null) return -1;
		
		return database.delete(TABLE_SCHEDULES, KEY_SCHEDULE_ID + " = ?",
                new String[] { String.valueOf(schedule_id) });
    }
	
	public List<Schedules> getAllSchedules() throws ParseException{
        List<Schedules> scheduleList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULES; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	scheduleList = new ArrayList<Schedules>();
            do {
            	Schedules scheduleObj = new Schedules();
            	scheduleObj.setSchedule_id(c.getInt(c.getColumnIndex(KEY_SCHEDULE_ID)));
            	scheduleObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
            	scheduleObj.setStart_time(sdf.parse(c.getString(c.getColumnIndex(SCHEDULE_START_TIME))));
            	scheduleObj.setEnd_time(sdf.parse(c.getString(c.getColumnIndex(SCHEDULE_END_TIME))));
            	scheduleObj.setClass_day(c.getString(c.getColumnIndex(CLASS_DAY)));
 
                // adding to student list
                scheduleList.add(scheduleObj);
            } while (c.moveToNext());
        } 
        return scheduleList;
    }

	
	public Schedules getSchedule(Integer schedule_id) throws ParseException{
		if(schedule_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULES + " WHERE " + KEY_SCHEDULE_ID + " = " + schedule_id; 
        Log.e(LOG, "getSchedule(Integer schedule_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Schedules scheduleObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
        if (c != null){
            c.moveToFirst();  
            scheduleObj = new Schedules();
            scheduleObj.setSchedule_id(c.getInt(c.getColumnIndex(KEY_SCHEDULE_ID)));
        	scheduleObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	scheduleObj.setStart_time(sdf.parse(c.getString(c.getColumnIndex(SCHEDULE_START_TIME))));
        	scheduleObj.setEnd_time(sdf.parse(c.getString(c.getColumnIndex(SCHEDULE_END_TIME))));
        	scheduleObj.setClass_day(c.getString(c.getColumnIndex(CLASS_DAY)));
        }   
        return scheduleObj;
    }
	
	public Schedules getScheduleForAClass(Integer class_id) throws ParseException{
		if(class_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULES + " WHERE " + KEY_SCHEDULE_ID + " = " + class_id; 
        Log.e(LOG, "getScheduleForAClass(Integer class_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Schedules scheduleObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
        if (c != null){
            c.moveToFirst();  
            scheduleObj = new Schedules();
            scheduleObj.setSchedule_id(c.getInt(c.getColumnIndex(KEY_SCHEDULE_ID)));
        	scheduleObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	scheduleObj.setStart_time(sdf.parse(c.getString(c.getColumnIndex(SCHEDULE_START_TIME))));
        	scheduleObj.setEnd_time(sdf.parse(c.getString(c.getColumnIndex(SCHEDULE_END_TIME))));
        	scheduleObj.setClass_day(c.getString(c.getColumnIndex(CLASS_DAY)));
        }   
        return scheduleObj;
    }
}
