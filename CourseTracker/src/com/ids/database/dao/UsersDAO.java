package com.ids.database.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ids.database.connector.DatabaseHandler;
import com.ids.database.model.Users;

public  class UsersDAO {
	//logger
	private static final String LOG = "UsersDAO";
	
	// Table Name
	public static final String TABLE_USERS = "users";
	
	// Database handle
	private SQLiteDatabase database;
	
	// Database handle creator class
	private DatabaseHandler dbHelper;
	
	// current context of the application
	public UsersDAO(Context context) {
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
	public  long insertUser(Users userObj){
		if(userObj == null) return (long) -1;
				
		ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, userObj.getFirst_name());
        values.put(KEY_LAST_NAME, userObj.getLast_name());
        values.put(KEY_SCHOOL_NAME, userObj.getSchool_name());
        values.put(KEY_DESCRIPTION, userObj.getDescription());
        values.put(KEY_OTHER, userObj.getOther());
		       
        // insert row
        return database.insert(TABLE_USERS, null, values);
	}
	
	/*
	 * This method is used to update the student entity
	 * given a student object. 
	 * Returns either 0 or num of rows updated indicating
	 * success or failure.
	 * So, when using this method, success or
	 * failure check have to be mentioned.
	 */
	public int updateStudent(Users userObj) {

		ContentValues values = new ContentValues();
		values.put(KEY_FIRST_NAME, userObj.getFirst_name());
        values.put(KEY_LAST_NAME, userObj.getLast_name());
        values.put(KEY_SCHOOL_NAME, userObj.getSchool_name());
        values.put(KEY_DESCRIPTION, userObj.getDescription());
        values.put(KEY_OTHER, userObj.getOther());

		// updating row
		return database.update(TABLE_USERS, values, KEY_STUDENT_ID + " = ?",new String[] { String.valueOf(userObj.getStudent_id()) });
	}
	
	/*
	 * Delete all students
	 * returns the number of rows deleted 
	 */
	public int deleteAllStudents() {				
		return database.delete(TABLE_USERS,"1",null);
    }
	
	/*
	 * Delete a student.
	 * return -1 for error,
	 * 0 - no rows deleted
	 * > 0 - num of rows deleted
	 */
	public int deleteStudent(Integer student_id) {		
		if(student_id == null) return -1;
		
		return database.delete(TABLE_USERS, KEY_STUDENT_ID + " = ?",
                new String[] { String.valueOf(student_id) });
    }
	
	/*
	 * Method to retrieve all the students.
	 * This method returns a list of student objects
	 * or else null. So check for null condition when 
	 * using this method
	 */
	public List<Users> getAllStudents() {
        List<Users> studentList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_USERS; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	studentList = new ArrayList<Users>();
            do {
            	Users student = new Users();
    	        student.setStudent_id(c.getInt(c.getColumnIndex(KEY_STUDENT_ID)));
    	        student.setFirst_name(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
    	        student.setLast_name(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
    	        student.setSchool_name(c.getString(c.getColumnIndex(KEY_SCHOOL_NAME)));
    	        student.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
    	        student.setOther(c.getString(c.getColumnIndex(KEY_OTHER)));
 
                // adding to student list
                studentList.add(student);
            } while (c.moveToNext());
        } 
        return studentList;
    }
	
	/*
	 * Get a student object given a student ID
	 * If a student id is not present, null is returned, so when using this method,
	 * check for null condition before using the object.
	 * This method uses the raw query feature
	 */
	public Users getStudent(Integer student_id) {
		if(student_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_STUDENT_ID + " = " + student_id; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Users student = null;
        if (c != null){
            c.moveToFirst();  
            student = new Users();
	        student.setStudent_id(c.getInt(c.getColumnIndex(KEY_STUDENT_ID)));
	        student.setFirst_name(c.getString(c.getColumnIndex(KEY_FIRST_NAME)));
	        student.setLast_name(c.getString(c.getColumnIndex(KEY_LAST_NAME)));
	        student.setSchool_name(c.getString(c.getColumnIndex(KEY_SCHOOL_NAME)));
	        student.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
	        student.setOther(c.getString(c.getColumnIndex(KEY_OTHER)));
        }   
        return student;
    }
	
}