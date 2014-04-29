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
import com.ids.database.model.Classes;

public class ClassesDAO {
	// Table Name
	public static final String TABLE_CLASSES = "classes";

	private static final String LOG = "UsersDAO";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public ClassesDAO(Context context) {
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

	// table columns
	public static final String KEY_CLASS_ID = "course_id";
	public static final String COURSE_NUMBER = "course_number";
	public static final String COURSE_NAME = "course_name";
	public static final String COURSE_DESCRIPTION = "description";
	public static final String COURSE_LOCATION = "location";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";
	public static final String NUMBER_TARGETED_WEEKLY_SESSIONS = "num_target_week_sessions";

	public static final String CREATE_TABLE_CLASSES = "CREATE TABLE "
			+ TABLE_CLASSES + "( " + KEY_CLASS_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COURSE_NUMBER
			+ " TEXT NOT NULL," + COURSE_NAME + " TEXT NOT NULL,"
			+ COURSE_DESCRIPTION + " TEXT ," + COURSE_LOCATION + " TEXT ,"
			+ START_DATE + " DATETIME NOT NULL, " + END_DATE
			+ " DATETIME NOT NULL, " + NUMBER_TARGETED_WEEKLY_SESSIONS
			+ " INTEGER NOT NULL, " + "CONSTRAINT date_check_classes CHECK ("
			+ START_DATE + " <= " + END_DATE + " ));";

	/*** Database query methods ***/

	// insert a given a course object
	public long insertUser(Classes classObj) {
		if (classObj == null)
			return (long) -1;

		ContentValues values = new ContentValues();
		values.put(COURSE_NUMBER, classObj.getCourse_number());
		values.put(COURSE_NAME, classObj.getCourse_name());
		values.put(COURSE_DESCRIPTION, classObj.getDescription().toString());
		values.put(COURSE_LOCATION, classObj.getLocation().toString());
		values.put(START_DATE, classObj.getStart_date().toString());
		values.put(END_DATE, classObj.getEnd_date().toString());
		values.put(NUMBER_TARGETED_WEEKLY_SESSIONS,	classObj.getNum_target_week_sessions());

		// insert row
		return database.insert(TABLE_CLASSES, null, values);
	}

	public int updateClass(Classes classObj) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
		ContentValues values = new ContentValues();
		values.put(COURSE_NUMBER, classObj.getCourse_number());
		values.put(COURSE_NAME, classObj.getCourse_name());
		values.put(COURSE_DESCRIPTION, classObj.getDescription());
		values.put(COURSE_LOCATION, classObj.getLocation());
		values.put(START_DATE, sdf.format(classObj.getStart_date()));
		values.put(END_DATE,sdf.format(classObj.getEnd_date()));
		values.put(NUMBER_TARGETED_WEEKLY_SESSIONS, classObj.getNum_target_week_sessions());

		// updating row
		return database.update(TABLE_CLASSES, values, KEY_CLASS_ID + " = ?",
				new String[] { String.valueOf(classObj.getCourse_id()) });
	}
	
	public int updateClassWithRawQuery(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	public int deleteAllClasses() {				
		return database.delete(TABLE_CLASSES,"1",null);
    }
	
	public int deleteClass(Integer course_id) {		
		if(course_id == null) return -1;
		
		return database.delete(TABLE_CLASSES, KEY_CLASS_ID + " = ?",
                new String[] { String.valueOf(course_id) });
    }
	
	public List<Classes> getAllClasses() throws ParseException{
        List<Classes> classList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CLASSES; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	classList = new ArrayList<Classes>();
            do {
            	Classes classObj = new Classes();
            	classObj.setCourse_id(c.getInt(c.getColumnIndex(KEY_CLASS_ID)));
            	classObj.setCourse_number(c.getString(c.getColumnIndex(COURSE_NUMBER)));
            	classObj.setCourse_name(c.getString(c.getColumnIndex(COURSE_NAME)));
            	classObj.setDescription(c.getString(c.getColumnIndex(COURSE_DESCRIPTION)));
            	classObj.setLocation(c.getString(c.getColumnIndex(COURSE_LOCATION)));
            	classObj.setNum_target_week_sessions(c.getInt(c.getColumnIndex(NUMBER_TARGETED_WEEKLY_SESSIONS)));
            	classObj.setStart_date(sdf.parse(c.getString(c.getColumnIndex(START_DATE))));
            	classObj.setEnd_date(sdf.parse(c.getString(c.getColumnIndex(END_DATE))));
 
                // adding to student list
                classList.add(classObj);
            } while (c.moveToNext());
        } 
        return classList;
    }
	
	public Classes getSingleClass(Integer course_id) throws ParseException{
		if(course_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_CLASSES + " WHERE " + KEY_CLASS_ID + " = " + course_id; 
        Log.e(LOG, "getSingleClass(Integer course_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Classes classObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        if (c != null){
            c.moveToFirst();  
            classObj = new Classes();
            classObj.setCourse_id(c.getInt(c.getColumnIndex(KEY_CLASS_ID)));
        	classObj.setCourse_number(c.getString(c.getColumnIndex(COURSE_NUMBER)));
        	classObj.setCourse_name(c.getString(c.getColumnIndex(COURSE_NAME)));
        	classObj.setDescription(c.getString(c.getColumnIndex(COURSE_DESCRIPTION)));
        	classObj.setLocation(c.getString(c.getColumnIndex(COURSE_LOCATION)));
        	classObj.setNum_target_week_sessions(c.getInt(c.getColumnIndex(NUMBER_TARGETED_WEEKLY_SESSIONS)));
        	classObj.setStart_date(sdf.parse(c.getString(c.getColumnIndex(START_DATE))));
        	classObj.setEnd_date(sdf.parse(c.getString(c.getColumnIndex(END_DATE))));
        }   
        return classObj;
    }
}

