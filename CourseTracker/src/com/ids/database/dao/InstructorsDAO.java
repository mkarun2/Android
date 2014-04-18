package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.helper.DatabaseHelper;

public class InstructorsDAO {
	// Table Name
	public static final String TABLE_INSTRUCTORS = "instructors";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHelper dbHelper;

	// current context of the application
	public InstructorsDAO(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	// open the database connection
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	// close the database connection
	public void close() {
		dbHelper.close();
	}
	
	
	public static final String KEY_INSTRUCTIOR_ID = "instructor_id";
	public static final String INSTRUCTOR_NAME = "name";
	public static final String INSTRUCTOR_PHONE = "phone";
	public static final String INSTRUCTOR_EMAIL = "email";
	public static final String INSTRUCTOR_TYPE = "type";
	
	public static final String CREATE_TABLE_INSTRUCTORS = "CREATE TABLE " + TABLE_INSTRUCTORS + "(" +
			KEY_INSTRUCTIOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "	+
			INSTRUCTOR_NAME + " TEXT NOT NULL," + 
			INSTRUCTOR_PHONE + " TEXT NOT NULL," +
			INSTRUCTOR_EMAIL + " TEXT NOT NULL," +
			INSTRUCTOR_TYPE + " TEXT DEFAULT 'PROFESSOR' CHECK ( " + INSTRUCTOR_TYPE +" IN ('PROFESSOR','TA')) );";
	
	
	
	
	// inserting an instructor goes in addition to adding a record in
	// the officehours table
	// The type field in the instructor, defines whether
	// it is an instructor or a TA
	
}
