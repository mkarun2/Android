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
import com.ids.database.model.OfficeHours;
import com.ids.database.model.StudySession;

public class StudySessionsDAO {
	// Table Name
	public static final String TABLE_STUDY_SESSIONS = "studysession";
	private static final String LOG = "StudySessionsDAO";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHandler dbHelper;

	// current context of the application
	public StudySessionsDAO(Context context) {
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
	
	public static final String KEY_STUDY_SESSION_ID = "study_session_id";
	public static final String FKEY_CLASS_ID = "course_id";
	public static final String STUDY_DATE = "date";
	public static final String NUM_COMPLETED_SESSIONS = "num_sessions_completed";
	
	public static final String CREATE_STUDY_SESSIONS_TABLE = StudySessionsDAO.createTableStudySessionsScript();
	
	
	private  static String createTableStudySessionsScript(){
		StringBuilder buildQueryStatement = new StringBuilder();
		buildQueryStatement.append("CREATE TABLE "+TABLE_STUDY_SESSIONS +" (");
		buildQueryStatement.append(KEY_STUDY_SESSION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,");
		buildQueryStatement.append(STUDY_DATE +" DATE ,");
		buildQueryStatement.append(FKEY_CLASS_ID +" INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES +"( "+ClassesDAO.KEY_CLASS_ID+" ) " +
				"ON DELETE CASCADE ON UPDATE CASCADE,");
		buildQueryStatement.append(NUM_COMPLETED_SESSIONS + " INTEGER );");
		return buildQueryStatement.toString();
	}
	
	public long insertStudySession(StudySession studySessionObj) {
		if (studySessionObj == null)
			return (long) -1;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
		ContentValues values = new ContentValues();
		values.put(FKEY_CLASS_ID, studySessionObj.getCourse_id());
		values.put(STUDY_DATE, sdf.format(studySessionObj.getStudy_date()));
		values.put(NUM_COMPLETED_SESSIONS,studySessionObj.getNum_sessions_completed());

		
		// insert row
		return database.insert(TABLE_STUDY_SESSIONS, null, values);
	}
	
	public int updateStudySessionRawQuery(String updateQuery) {

		Cursor c = database.rawQuery(updateQuery, null);
		if (c != null && c.getCount() == 0)
			return -1;
		else
			return c.getCount();
	}
	
	public int deleteAllStudySessions() {				
		return database.delete(TABLE_STUDY_SESSIONS,"1",null);
    }
	
	public int deleteStudySession(Integer studySession_id) {		
		if(studySession_id == null) return -1;
		
		return database.delete(TABLE_STUDY_SESSIONS, KEY_STUDY_SESSION_ID + " = ?",
                new String[] { String.valueOf(studySession_id) });
    }
	
	public List<StudySession> getAllStudySessions() throws ParseException{
        List<StudySession> studySessionList = null;
        String selectQuery = "SELECT  * FROM " + TABLE_STUDY_SESSIONS; 
        Log.e(LOG, selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null);
        
        if(c == null) return null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss",Locale.US);
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
        	studySessionList = new ArrayList<StudySession>();
            do {
            	StudySession studySessionObj = new StudySession();
            	studySessionObj.setStudy_session_id(c.getInt(c.getColumnIndex(KEY_STUDY_SESSION_ID)));
            	studySessionObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
            	studySessionObj.setStudy_date(sdf.parse(c.getString(c.getColumnIndex(STUDY_DATE))));
            	studySessionObj.setNum_sessions_completed(c.getInt(c.getColumnIndex(NUM_COMPLETED_SESSIONS)));

 
                // adding to student list
                studySessionList.add(studySessionObj);
            } while (c.moveToNext());
        } 
        return studySessionList;
    }

	
	public StudySession getStudySession(Integer studySessionID) throws ParseException{
		if(studySessionID == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_STUDY_SESSIONS + " WHERE " + KEY_STUDY_SESSION_ID + " = " + studySessionID; 
        Log.e(LOG, "getStudySession(Integer studySessionID) query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        StudySession studySessionObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
        if (c != null){
            c.moveToFirst();  
            studySessionObj = new StudySession();
        	studySessionObj.setStudy_session_id(c.getInt(c.getColumnIndex(KEY_STUDY_SESSION_ID)));
        	studySessionObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	studySessionObj.setStudy_date(sdf.parse(c.getString(c.getColumnIndex(STUDY_DATE))));
        	studySessionObj.setNum_sessions_completed(c.getInt(c.getColumnIndex(NUM_COMPLETED_SESSIONS)));
        }   
        return studySessionObj;
    }
	
	public StudySession getStudySessionWithCourseID(Integer course_id) throws ParseException{
		if(course_id == null) return null;		
        String selectQuery = "SELECT  * FROM " + TABLE_STUDY_SESSIONS + " WHERE " + FKEY_CLASS_ID + " = " + course_id; 
        Log.e(LOG, "getStudySessionWithCourseID(Integer course_id)query : " + selectQuery); 
        Cursor c = database.rawQuery(selectQuery, null); 
        StudySession studySessionObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",Locale.US);
        if (c != null){
            c.moveToFirst();  
            studySessionObj = new StudySession();
        	studySessionObj.setStudy_session_id(c.getInt(c.getColumnIndex(KEY_STUDY_SESSION_ID)));
        	studySessionObj.setCourse_id(c.getInt(c.getColumnIndex(FKEY_CLASS_ID)));
        	studySessionObj.setStudy_date(sdf.parse(c.getString(c.getColumnIndex(STUDY_DATE))));
        	studySessionObj.setNum_sessions_completed(c.getInt(c.getColumnIndex(NUM_COMPLETED_SESSIONS)));
        }   
        return studySessionObj;
    }
	
}
