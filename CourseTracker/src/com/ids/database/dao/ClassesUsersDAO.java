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
import com.ids.database.model.ClassUsers;

public class ClassesUsersDAO {
	// Table Name
	public static final String TABLE_CLASSESUSERS = "classesusers";
	
	private static final String LOG = "ClassUsersDAO";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public ClassesUsersDAO(Context context) {
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
	public static final String FKEY_CLASS_ID = "course_id";
	public static final String FKEY_STUDENT_ID = "student_id";
	
	public static final String CREATE_CLASSES_USERS = "CREATE TABLE "+TABLE_CLASSESUSERS+ " (" +
			FKEY_CLASS_ID + " INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES+ " ( "+ClassesDAO.KEY_CLASS_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE," +
			FKEY_STUDENT_ID +" INTEGER REFERENCES "+UsersDAO.TABLE_USERS+ " ( "+UsersDAO.KEY_STUDENT_ID+" ) ON DELETE CASCADE ON UPDATE CASCADE," +
			"PRIMARY KEY ("+FKEY_CLASS_ID+" , "+FKEY_STUDENT_ID+" ));";

	
	// insert a given a activity object
	public long insertClassUser(ClassUsers classUserObj) {
		if (classUserObj == null)
			return (long) -1;

		ContentValues values = new ContentValues();
		values.put(FKEY_CLASS_ID, classUserObj.getCourse_id());
		values.put(FKEY_STUDENT_ID, classUserObj.getStudent_id());

		// insert row
		return database.insert(TABLE_CLASSESUSERS, null, values);
	}
	
	public int updateClassUsers(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	public int deleteAllClassUsers() {				
		return database.delete(TABLE_CLASSESUSERS,"1",null);
    }
	
	public int deleteClassUser(Integer class_id,Integer student_id) {		
		if(class_id == null || student_id == null) return -1;
		
		return database.delete(TABLE_CLASSESUSERS, FKEY_CLASS_ID + " = ? AND "+ FKEY_STUDENT_ID + " = ?",
                new String[] { String.valueOf(class_id),String.valueOf(student_id)  });
    }
	
	public List<ClassUsers> getAllClassUsers() throws ParseException{
        List<ClassUsers> activityList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CLASSESUSERS; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	activityList = new ArrayList<ClassUsers>();
            do {
            	ClassUsers classUserObj = new ClassUsers();
            	classUserObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
            	classUserObj.setStudent_id(c.getInt(c.getColumnIndex(FKEY_STUDENT_ID)));
 
                // adding to student list
                activityList.add(classUserObj);
            } while (c.moveToNext());
        } 
        return activityList;
    }
	
	public ClassUsers getClassUser(Integer class_id,Integer student_id) throws ParseException{
		if(class_id == null || student_id == null) return null;		
		
        String selectQuery = "SELECT  * FROM " + TABLE_CLASSESUSERS + 
        		" WHERE " + FKEY_CLASS_ID + " = " + class_id + " AND " + FKEY_STUDENT_ID +" = " + student_id; 
        Log.e(LOG, "getClassUser(Integer class_id,Integer student_id) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        ClassUsers classUserObj = null;

        if (c != null){
            c.moveToFirst();  
            classUserObj = new ClassUsers();
        	classUserObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	classUserObj.setStudent_id(c.getInt(c.getColumnIndex(FKEY_STUDENT_ID)));

        }   
        return classUserObj;
    }

}
