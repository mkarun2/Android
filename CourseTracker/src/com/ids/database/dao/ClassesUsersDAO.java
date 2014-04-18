package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.helper.DatabaseHelper;

public class ClassesUsersDAO {
	// Table Name
	public static final String TABLE_CLASSESUSERS = "classesusers";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHelper dbHelper;

	// current context of the application
	public ClassesUsersDAO(Context context) {
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

	// table columns
	public static final String FKEY_CLASS_ID = "course_id";
	public static final String FKEY_STUDENT_ID = "student_id";
	
	public static final String CREATE_CLASSES_USERS = "CREATE TABLE "+TABLE_CLASSESUSERS+ " (" +
			FKEY_CLASS_ID + " INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES+ " ( "+ClassesDAO.KEY_CLASS_ID+" )," +
			FKEY_STUDENT_ID +" INTEGER REFERENCES "+UsersDAO.TABLE_USERS+ " ( "+UsersDAO.KEY_STUDENT_ID+" )," +
			"PRIMARY KEY ("+FKEY_CLASS_ID+" , "+FKEY_STUDENT_ID+" ));";


}
