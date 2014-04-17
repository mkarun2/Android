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
	
	// inserting an instructor goes in addition to adding a record in
	// the officehours table
	// The type field in the instructor, defines whether
	// it is an instructor or a TA
	
}
