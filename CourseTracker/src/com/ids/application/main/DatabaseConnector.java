package com.ids.application.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseConnector 
{
   // database name
   private static final String DATABASE_NAME = "ClassTracker";
   private SQLiteDatabase database; // database object
   private DatabaseOpenHelper databaseOpenHelper; // database helper

   // public constructor for DatabaseConnector
   public DatabaseConnector(Context context) 
   {
      // create a new DatabaseOpenHelper
	   int dbVersion = 1;
      databaseOpenHelper = 
         new DatabaseOpenHelper(context, DATABASE_NAME, null, dbVersion);
   } // end DatabaseConnector constructor

   // open the database connection
   public void open() throws SQLException 
   {
       //create or open a database for reading/writing
      database = databaseOpenHelper.getWritableDatabase();
   } // end method open

   // close the database connection
   public void close() 
   {
      if (database != null)
         database.close(); // close the database connection
   } // end method close

   // inserts a new contact in the database
   public void insertInstructor(String name, String office, String phone, String email)
   {
	   ContentValues newInstructor = new ContentValues();
	   newInstructor.put("name", name);
	   newInstructor.put("office", office);
	   newInstructor.put("phone", phone);
	   newInstructor.put("email", email);
	   
	   open(); // open the database
	   database.insert("instructors", null, newInstructor);
	   close(); // close the database
	} // end method insertContact

   public Cursor getAllInstructors()
   {
	   return database.rawQuery("select * from instructors order by name", null);
   }

   public Cursor getOneInstructor(long instructorID)
   {
	   return database.rawQuery("select * from instructors where _id=" + instructorID, null);
   }
   
   public void updateInstructor(long id, String name, String office, String phone, String email)
   {
	   ContentValues editInstructor = new ContentValues();
	   editInstructor.put("name", name);
	   editInstructor.put("office", office);
	   editInstructor.put("phone", phone);
	   editInstructor.put("email", email);
	      
	   open(); // open the database
	   database.update("instructors", editInstructor, "_id=" + id, null);
	   close(); // close the database
   } // end method updateInstructor

   public void deleteInstructor(long id)
   {
	      open(); // open the database
	      database.delete("instructors", "_id=" + id, null);
	      close(); // close the database
   }
   

   public void insertAssistant(String name, String office, String phone, String email) 
   {
      ContentValues newAssistant = new ContentValues();
      newAssistant.put("name", name);
      newAssistant.put("office", office);
      newAssistant.put("phone", phone);
      newAssistant.put("email", email);
 
      open(); // open the database
      database.insert("assistants", null, newAssistant);
      close(); // close the database
   } // end method insertContact

   public Cursor getAllAssistants()
   {
	   return database.rawQuery("select * from assistants order by name", null);
   }

   public Cursor getOneAssistant(long assistantID)
   {
	   return database.rawQuery("select * from assistants where _id=" + assistantID, null);
   }
   
   public void updateAssistant(long id, String name, String office, String phone, String email)
   {
	   ContentValues editAssistant = new ContentValues();
	   editAssistant.put("name", name);
	   editAssistant.put("office", office);
	   editAssistant.put("phone", phone);
	   editAssistant.put("email", email);
	      
	   open(); // open the database
	   database.update("assistants", editAssistant, "_id=" + id, null);
	   close(); // close the database
   } // end method updateInstructor
   
   public void deleteAssistant(long id)
   {
	      open(); // open the database
	      database.delete("assistants", "_id=" + id, null);
	      close(); // close the database
   }


   public void insertOfficeHour(String officeDay, String startTime, String endTime,
		   long instructorID, long assistantID) 
   {
      ContentValues newOfficeHour = new ContentValues();
      newOfficeHour.put("officeDay", officeDay);
      newOfficeHour.put("startTime", startTime);
      newOfficeHour.put("endTime", endTime);
      newOfficeHour.put("instructor_id", instructorID);
      newOfficeHour.put("assistant_id", assistantID);

      open(); // open the database
      database.insert("officeHours", null, newOfficeHour);
      close(); // close the database
   } // end method insertBookAuthor
   
   // return a Cursor for instructor office hours
   public Cursor getInstructorOfficeHours(long instructorID)
   {
	  return database.rawQuery("select _id, officeDay || ': ' || startTime || ' - ' || endTime as officeHour from officeHours " +
                               "where instructor_id  = " + instructorID , null);
   } // end method getInstructorOfficeHours

   // return a Cursor for instructor office hours
   public Cursor getAssistantOfficeHours(long assistantID)
   {
		  return database.rawQuery("select _id, officeDay || ': ' || startTime || ' - ' || endTime as officeHour from officeHours " +
                  "where assistant_id  = " + assistantID , null);
   } // end method getAssistantOfficeHours
   
   public void deleteInstructorOfficeHour(long instructorID, String officeDay, String startTime, String endTime)
   {
	      open(); // open the database
	      database.delete("officeHours", "instructor_id=" + instructorID +
	    		          " and officeDay='" + officeDay +
	    		          "' and startTime='" + startTime +
	    		          "' and endTime='" + endTime + "'" , null);
	      close(); // close the database
   }
   
   public void deleteAssistantOfficeHour(long assistantID, String officeDay, String startTime, String endTime)
   {
	      open(); // open the database
	      database.delete("officeHours", "assistant_id=" + assistantID +
	    		          " and officeDay='" + officeDay +
	    		          "' and startTime='" + startTime +
	    		          "' and endTime='" + endTime + "'" , null);
	      close(); // close the database
   }
   
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper 
   {
      // public constructor
      public DatabaseOpenHelper(Context context, String name,
         CursorFactory factory, int version) 
      {
         super(context, name, factory, version);
      } // end DatabaseOpenHelper constructor

      // creates the contacts table when the database is created
      @Override
      public void onCreate(SQLiteDatabase db) 
      {
         // query to create a new table named contacts
         String createQuery = "CREATE TABLE instructors" +
            "(_id integer primary key autoincrement," +
            "name TEXT, " +
            "office TEXT, " +
            "phone TEXT," +
            "email TEXT);";
                  
         db.execSQL(createQuery); // execute the query

         createQuery = "CREATE TABLE assistants" +
        		 "(_id integer primary key autoincrement," +
                 "name TEXT, " +
                 "office TEXT, " +
                 "phone TEXT," +
                 "email TEXT);";
                       
         db.execSQL(createQuery); // execute the query

         createQuery = "CREATE TABLE officehours" +
        		 "(_id integer primary key autoincrement," +
                 "officeDay TEXT, " +
                 "startTime TEXT, " +
                 "endTime TEXT, " +
        		 "instructor_id integer references instructors(_id) on delete cascade," +
		         "assistant_id integer references assistants(_id) on delete cascade);"; 
         db.execSQL(createQuery); // execute the query

         createQuery = "CREATE TABLE classes" +
        		 "(_id integer primary key autoincrement," +
                 "courseNumber TEXT, " +
                 "name TEXT, " +
                 "description TEXT, " +
                 "location TEXT, " +
                 "startDate TEXT, " +
                 "endDate TEXT, " +
                 "numTargetedWeeklySessions integer, " +
        		 "instructor_id integer references instructors(_id)," +
		         "assistant_id integer references assistants(_id));"; 
         db.execSQL(createQuery); // execute the query
       
         //TODO:
         //continue creating other tables (see the entity relationship diagram)
         
         
      } // end method onCreate

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
      {
    	  //TODO
      } // end method onUpgrade
   } // end class DatabaseOpenHelper
} // end class DatabaseConnector
