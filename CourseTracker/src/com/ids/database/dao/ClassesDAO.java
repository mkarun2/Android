package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.connector.DatabaseHandler;

public class ClassesDAO {
	// Table Name
	public static final String TABLE_CLASSES = "classes";

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
	
	//table columns
	public static final String KEY_CLASS_ID = "course_id";
	public static final String COURSE_NUMBER = "course_number";
	public static final String COURSE_NAME = "course_name";
	public static final String COURSE_DESCRIPTION = "description";
	public static final String COURSE_LOCATION = "location";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";
	public static final String NUMBER_TARGETED_WEEKLY_SESSIONS = "num_target_week_sessions";
	
	public static final String CREATE_TABLE_CLASSES = "CREATE TABLE "+TABLE_CLASSES+ "( " +
			KEY_CLASS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COURSE_NUMBER + " TEXT NOT NULL," +
			COURSE_NAME + " TEXT NOT NULL," +
			COURSE_DESCRIPTION + " TEXT ," +
			COURSE_LOCATION + " TEXT ," +
			START_DATE +" DATETIME NOT NULL, "+
			END_DATE + " DATETIME NOT NULL, "+
			NUMBER_TARGETED_WEEKLY_SESSIONS + " INTEGER NOT NULL, "+
			"CONSTRAINT date_check_classes CHECK ("+START_DATE +" <= "+ END_DATE +" ));";
	
}
