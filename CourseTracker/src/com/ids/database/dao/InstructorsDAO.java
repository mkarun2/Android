package com.ids.database.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ids.database.connector.DatabaseHandler;
import com.ids.database.model.Instructors;

public class InstructorsDAO {
	// Table Name
	public static final String TABLE_INSTRUCTORS = "instructors";
	
	private static final String LOG = "InstructorsDAO";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public InstructorsDAO(Context context) {
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

	public long insertInstructor(Instructors instructorObj) {
		if (instructorObj == null)
			return (long) -1;

		ContentValues values = new ContentValues();
		values.put(KEY_INSTRUCTIOR_ID, instructorObj.getInstructor_id());
		values.put(INSTRUCTOR_NAME, instructorObj.getName());
		values.put(INSTRUCTOR_PHONE, instructorObj.getPhone());
		values.put(INSTRUCTOR_TYPE, instructorObj.getType());
		values.put(INSTRUCTOR_EMAIL, instructorObj.getEmail().toString());
		
		// insert row
		return database.insert(TABLE_INSTRUCTORS, null, values);
	}
	
	public int updateInstructor(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	public int deleteAllInstructors() {				
		return database.delete(TABLE_INSTRUCTORS,"1",null);
    }
	
	public int deleteInstructor(Integer instructor_id) {		
		if(instructor_id == null) return -1;
		
		return database.delete(TABLE_INSTRUCTORS, KEY_INSTRUCTIOR_ID + " = ?",
                new String[] { String.valueOf(instructor_id) });
    }
	
	
	public List<Instructors> getAllInstructors() throws ParseException{
        List<Instructors> attendanceList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTORS; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	attendanceList = new ArrayList<Instructors>();
            do {
            	Instructors instructor = new Instructors();
            	instructor.setInstructor_id(c.getInt(c.getColumnIndex(KEY_INSTRUCTIOR_ID)));
            	instructor.setName(c.getString(c.getColumnIndex(INSTRUCTOR_NAME)));
            	instructor.setPhone(c.getString(c.getColumnIndex(INSTRUCTOR_PHONE)));
            	instructor.setEmail(c.getString(c.getColumnIndex(INSTRUCTOR_EMAIL)));
            	instructor.setType(c.getString(c.getColumnIndex(INSTRUCTOR_TYPE)));
 
                // adding to student list
                attendanceList.add(instructor);
            } while (c.moveToNext());
        } 
        return attendanceList;
    }
	
	public Instructors getAttendance(Integer instructor_id) throws ParseException{
		if(instructor_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_INSTRUCTORS + " WHERE " + KEY_INSTRUCTIOR_ID + " = " + instructor_id; 
        Log.e(LOG, "getAttendance(Integer attendance_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        Instructors instructor = null;

        if (c != null){
            c.moveToFirst();  
            instructor = new Instructors();
        	instructor.setInstructor_id(c.getInt(c.getColumnIndex(KEY_INSTRUCTIOR_ID)));
        	instructor.setName(c.getString(c.getColumnIndex(INSTRUCTOR_NAME)));
        	instructor.setPhone(c.getString(c.getColumnIndex(INSTRUCTOR_PHONE)));
        	instructor.setEmail(c.getString(c.getColumnIndex(INSTRUCTOR_EMAIL)));
        	instructor.setType(c.getString(c.getColumnIndex(INSTRUCTOR_TYPE)));
        }   
        return instructor;
    }
	
}
