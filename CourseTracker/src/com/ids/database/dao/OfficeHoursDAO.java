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
import com.ids.database.model.Attendance;
import com.ids.database.model.OfficeHours;

public class OfficeHoursDAO {
		// Table Name
		public static final String TABLE_OFFICEHOURS = "officehours";
		private static final String LOG = "OfficeHoursDAO";
		
		// Database handle
		private SQLiteDatabase database;

		// Database handle creator class
		private DatabaseHandler dbHelper;

		// current context of the application
		public OfficeHoursDAO(Context context) {
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
		public static final String KEY_OFFICE_ID = "office_id";
		public static final String OFFICE_DAY = "office_day";
		public static final String START_TIME = "start_time";
		public static final String END_TIME = "end_time";
		public static final String FKEY_INSTRUCTOR_ID = "instructor_id";
 		
		public static final String CREATE_TABLE_OFFICEHOURS = "CREATE TABLE "+ TABLE_OFFICEHOURS + " (" +
				KEY_OFFICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				OFFICE_DAY + " TEXT NOT NULL," +
				START_TIME + " DATETIME NOT NULL," +
				END_TIME + " DATETIME NOT NULL, "+
				FKEY_INSTRUCTOR_ID + " INTEGER ," +
				"CONSTRAINT FK_OfficeHours_1 FOREIGN KEY ( "+ FKEY_INSTRUCTOR_ID + " ) " +
				"REFERENCES "+ InstructorsDAO.TABLE_INSTRUCTORS +" ( "+InstructorsDAO.KEY_INSTRUCTIOR_ID + " ) "+
				"ON DELETE CASCADE ON UPDATE CASCADE, " +
				"CONSTRAINT date_check CHECK ( " + START_TIME +" <= "+ END_TIME +"), "+
				"CONSTRAINT day_check CHECK ( lower("+ OFFICE_DAY +")  in "+
				"('monday','tuesday','wednesday','thursday','friday','saturday','sunday')));";
		
		
		public long insertOfficeHours(OfficeHours officeHoursObj) {
			if (officeHoursObj == null)
				return (long) -1;

			ContentValues values = new ContentValues();
			values.put(OFFICE_DAY, officeHoursObj.getOffice_day());
			values.put(START_TIME, officeHoursObj.getStart_time().toString());
			values.put(END_TIME, officeHoursObj.getEnd_time().toString());
			values.put(FKEY_INSTRUCTOR_ID, officeHoursObj.getInstructor_id());
			
			// insert row
			return database.insert(TABLE_OFFICEHOURS, null, values);
		}
		
		public int updateOfficeHoursRawQuery(String updateQuery) {

			Cursor c = database.rawQuery(updateQuery, null);
			if (c != null && c.getCount() == 0)
				return -1;
			else
				return c.getCount();
		}
		
		public int deleteAllOfficeHours() {				
			return database.delete(TABLE_OFFICEHOURS,"1",null);
	    }
		
		public int deleteOfficeHours(Integer office_id) {		
			if(office_id == null) return -1;
			
			return database.delete(TABLE_OFFICEHOURS, KEY_OFFICE_ID + " = ?",
	                new String[] { String.valueOf(office_id) });
	    }
		
		public List<OfficeHours> getAllOfficeHours() throws ParseException{
	        List<OfficeHours> officeHoursList = null;
	        String selectQuery = "SELECT  * FROM " + TABLE_OFFICEHOURS; 
	        Log.e(LOG, selectQuery); 
	        Cursor c = database.rawQuery(selectQuery, null);
	        
	        if(c == null) return null;
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
	        
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	        	officeHoursList = new ArrayList<OfficeHours>();
	            do {
	            	OfficeHours officeHoursObj = new OfficeHours();
	            	officeHoursObj.setOffice_id(c.getInt(c.getColumnIndex(KEY_OFFICE_ID)));
	            	officeHoursObj.setOffice_day(c.getString(c.getColumnIndex(OFFICE_DAY)));
	            	officeHoursObj.setStart_time(sdf.parse(c.getString(c.getColumnIndex(START_TIME))));
	            	officeHoursObj.setEnd_time(sdf.parse(c.getString(c.getColumnIndex(END_TIME))));
	            	officeHoursObj.setInstructor_id(c.getInt(c.getColumnIndex(FKEY_INSTRUCTOR_ID)));
	 
	                // adding to student list
	                officeHoursList.add(officeHoursObj);
	            } while (c.moveToNext());
	        } 
	        return officeHoursList;
	    }

		
		public OfficeHours getOfficeHours(Integer office_id) throws ParseException{
			if(office_id == null) return null;		
	        String selectQuery = "SELECT  * FROM " + TABLE_OFFICEHOURS + " WHERE " + KEY_OFFICE_ID + " = " + office_id; 
	        Log.e(LOG, "getOfficeHours(Integer attendance_id) query : " + selectQuery); 
	        Cursor c = database.rawQuery(selectQuery, null); 
	        OfficeHours officeHoursObj = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
	        if (c != null){
	            c.moveToFirst();  
	            officeHoursObj = new OfficeHours();
            	officeHoursObj.setOffice_id(c.getInt(c.getColumnIndex(KEY_OFFICE_ID)));
            	officeHoursObj.setOffice_day(c.getString(c.getColumnIndex(OFFICE_DAY)));
            	officeHoursObj.setStart_time(sdf.parse(c.getString(c.getColumnIndex(START_TIME))));
            	officeHoursObj.setEnd_time(sdf.parse(c.getString(c.getColumnIndex(END_TIME))));
            	officeHoursObj.setInstructor_id(c.getInt(c.getColumnIndex(FKEY_INSTRUCTOR_ID)));
	        }   
	        return officeHoursObj;
	    }
		
		public OfficeHours getOfficeHoursForInstructor(Integer instructor_id) throws ParseException{
			if(instructor_id == null) return null;		
	        String selectQuery = "SELECT  * FROM " + TABLE_OFFICEHOURS + " WHERE " + FKEY_INSTRUCTOR_ID + " = " + instructor_id; 
	        Log.e(LOG, "getOfficeHoursForInstructor(Integer FKEY_INSTRUCTOR_ID) query : " + selectQuery); 
	        Cursor c = database.rawQuery(selectQuery, null); 
	        OfficeHours officeHoursObj = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
	        if (c != null){
	            c.moveToFirst();  
	            officeHoursObj = new OfficeHours();
            	officeHoursObj.setOffice_id(c.getInt(c.getColumnIndex(KEY_OFFICE_ID)));
            	officeHoursObj.setOffice_day(c.getString(c.getColumnIndex(OFFICE_DAY)));
            	officeHoursObj.setStart_time(sdf.parse(c.getString(c.getColumnIndex(START_TIME))));
            	officeHoursObj.setEnd_time(sdf.parse(c.getString(c.getColumnIndex(END_TIME))));
            	officeHoursObj.setInstructor_id(c.getInt(c.getColumnIndex(FKEY_INSTRUCTOR_ID)));
	        }   
	        return officeHoursObj;
	    }
}
