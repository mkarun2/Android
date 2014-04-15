package com.ids.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.helper.DatabaseHelper;
import com.ids.database.model.Users;

public  class UsersDAO {
	
	// Table Name
	public static final String TABLE_USERS = "users";
	
	// Database handle
	private SQLiteDatabase database;
	
	// Database handle creator class
	private DatabaseHelper dbHelper;
	
	// current context of the application
	public UsersDAO(Context context) {
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
	
	
	// Users Table Column Names
	public static final String KEY_FIRST_NAME = "first_name";
	public static final String KEY_LAST_NAME = "last_name";
	public static final String KEY_STUDENT_ID = "student_id";
	public static final String KEY_SCHOOL_NAME = "school_name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_OTHER = "other";
	
	// Table creation script
	public static final String CREATE_TABLE_USERS = "CREATE TABLE "+ TABLE_USERS + "(" + 
							KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
							KEY_FIRST_NAME + " TEXT NOT NULL," +
							KEY_LAST_NAME + " TEXT NOT NULL," +
							KEY_SCHOOL_NAME + " TEXT ," +
							KEY_DESCRIPTION + " TEXT ," +
							KEY_OTHER + " TEXT " +
								");";
	
	// insert a user given a users object
	public final boolean insertUser(Users userObj){
		if(userObj == null) return false;
				
		ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, userObj.getFirst_name());
        values.put(KEY_LAST_NAME, userObj.getLast_name());
        values.put(KEY_SCHOOL_NAME, userObj.getSchool_name());
        values.put(KEY_DESCRIPTION, userObj.getDescription());
        values.put(KEY_OTHER, userObj.getOther());
		       
        // insert row
        Long student_id = database.insert(TABLE_USERS, null, values);
        
        if(student_id == null) return false;
        else return true;
	}
}