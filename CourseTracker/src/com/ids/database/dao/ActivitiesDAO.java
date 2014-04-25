package com.ids.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.connector.DatabaseHandler;
import com.ids.database.model.Activities;

public class ActivitiesDAO {
	// Table Name
	public static final String TABLE_ACTIVITIES = "activities";

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

}
