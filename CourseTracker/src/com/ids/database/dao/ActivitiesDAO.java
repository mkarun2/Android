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
import com.ids.database.model.Activities;
import com.ids.database.model.Users;

public class ActivitiesDAO {
	// Table Name
	public static final String TABLE_ACTIVITIES = "activities";
	
	private static final String LOG = "ActivitiesDAO";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public ActivitiesDAO(Context context) {
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

	public static final String KEY_ACTIVITY_ID = "activity_id";
	public static final String FKEY_CLASS_ID = "course_id";
	public static final String ACTIVITY_NAME = "name";
	public static final String ACTIVITY_TYPE = "type";
	public static final String DUE_DATE = "due_date";
	public static final String ACTIVITY_DESCRIPTION = "description";
	public static final String IS_COMPLETED = "is_completed";
	public static final String GRADE = "grade";
	public static final String MAX_GRADE = "max_grade";
	
	public static final String CREATE_TABLE_ACTIVITIES = ActivitiesDAO.createActivityTableScript();
	
	private static final String createActivityTableScript(){
		StringBuilder activityTableScript = new StringBuilder();
		activityTableScript.append("CREATE TABLE "+TABLE_ACTIVITIES+" (");
		activityTableScript.append(KEY_ACTIVITY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,");
		activityTableScript.append(FKEY_CLASS_ID +" INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES+" ("+ClassesDAO.KEY_CLASS_ID+" ) " +
				"ON DELETE CASCADE ON UPDATE CASCADE,");
		activityTableScript.append(ACTIVITY_NAME + " TEXT NOT NULL,");
		activityTableScript.append(ACTIVITY_TYPE + " TEXT NOT NULL,");
		activityTableScript.append(DUE_DATE + " DATETIME NOT NULL,");
		activityTableScript.append(ACTIVITY_DESCRIPTION + " TEXT NOT NULL ,");
		activityTableScript.append(IS_COMPLETED + " TEXT DEFAULT 'N' ,");
		activityTableScript.append(GRADE + " REAL DEFAULT 0.0 ,");
		activityTableScript.append(MAX_GRADE + " REAL NOT NULL ,");
		activityTableScript.append("CONSTRAINT activity_completed_check CHECK ( lower("+ IS_COMPLETED +" )  in ('y','n')),");
		activityTableScript.append("CONSTRAINT activity_grade_check CHECK ("+GRADE+" <= "+MAX_GRADE+" ));");
		return activityTableScript.toString();
	}
	
	/*** Database query methods ***/
	
	// insert a given a activity object
	public long insertUser(Activities activityObj) {
		if (activityObj == null)
			return (long) -1;

		ContentValues values = new ContentValues();
		values.put(FKEY_CLASS_ID, activityObj.getCourse_id());
		values.put(ACTIVITY_NAME, activityObj.getActivity_name());
		values.put(ACTIVITY_TYPE, activityObj.getActivity_type());
		values.put(DUE_DATE, activityObj.getDue_date().toString());
		values.put(ACTIVITY_DESCRIPTION, activityObj.getDescription());
		values.put(IS_COMPLETED, activityObj.getIs_completed());
		values.put(GRADE, activityObj.getGrade());
		values.put(MAX_GRADE, activityObj.getMax_grade());

		// insert row
		return database.insert(TABLE_ACTIVITIES, null, values);
	}

	/*
	 * This method accepts a String sql query and performs the update 
	 */
	public int updateActivity(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	/*
	 * Delete all activities
	 * returns the number of rows deleted 
	 */
	public int deleteAllActivities() {				
		return database.delete(TABLE_ACTIVITIES,"1",null);
    }
	
	
	/*
	 * Delete a single activity given an activity id
	 */
	public int deleteActivity(Integer activity_id) {		
		if(activity_id == null) return -1;
		
		return database.delete(TABLE_ACTIVITIES, KEY_ACTIVITY_ID + " = ?",
                new String[] { String.valueOf(activity_id) });
    }
	
	public List<Activities> getAllActivities() throws ParseException{
        List<Activities> activityList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	activityList = new ArrayList<Activities>();
            do {
            	Activities activity = new Activities();
            	activity.setActivity_id(c.getInt(c.getColumnIndex(KEY_ACTIVITY_ID)));
            	activity.setActivity_name(c.getString(c.getColumnIndex(ACTIVITY_NAME)));
            	activity.setActivity_type(c.getString(c.getColumnIndex(ACTIVITY_TYPE)));
            	activity.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
            	activity.setDescription(c.getString(c.getColumnIndex(ACTIVITY_DESCRIPTION)));
            	activity.setDue_date(sdf.parse(c.getString(c.getColumnIndex(DUE_DATE))));
            	activity.setGrade(c.getDouble(c.getColumnIndex(GRADE)));
            	activity.setIs_completed(c.getString(c.getColumnIndex(IS_COMPLETED)));
            	activity.setMax_grade(c.getDouble(c.getColumnIndex(MAX_GRADE)));
 
                // adding to student list
                activityList.add(activity);
            } while (c.moveToNext());
        } 
        return activityList;
    }

	public Activities getActivity(Integer activity_id) throws ParseException{
		if(activity_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_ACTIVITIES + " WHERE " + KEY_ACTIVITY_ID + " = " + activity_id; 
        Log.e(LOG, "getActivity(Integer activity_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Activities activity = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        if (c != null){
            c.moveToFirst();  
            activity = new Activities();
        	activity.setActivity_id(c.getInt(c.getColumnIndex(KEY_ACTIVITY_ID)));
        	activity.setActivity_name(c.getString(c.getColumnIndex(ACTIVITY_NAME)));
        	activity.setActivity_type(c.getString(c.getColumnIndex(ACTIVITY_TYPE)));
        	activity.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	activity.setDescription(c.getString(c.getColumnIndex(ACTIVITY_DESCRIPTION)));
        	activity.setDue_date(sdf.parse(c.getString(c.getColumnIndex(DUE_DATE))));
        	activity.setGrade(c.getDouble(c.getColumnIndex(GRADE)));
        	activity.setIs_completed(c.getString(c.getColumnIndex(IS_COMPLETED)));
        	activity.setMax_grade(c.getDouble(c.getColumnIndex(MAX_GRADE)));
        }   
        return activity;
    }

}
