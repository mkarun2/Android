package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.helper.DatabaseHelper;

public class StudySessionsDAO {
	// Table Name
	public static final String TABLE_STUDY_SESSIONS = "studysession";

	// Database handle
	private SQLiteDatabase database;

	// Database handle creator class
	private DatabaseHelper dbHelper;

	// current context of the application
	public StudySessionsDAO(Context context) {
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
		buildQueryStatement.append(FKEY_CLASS_ID +" INTEGER REFERENCES "+ClassesDAO.TABLE_CLASSES +"( "+ClassesDAO.KEY_CLASS_ID+" ),");
		buildQueryStatement.append(NUM_COMPLETED_SESSIONS + " INTEGER );");
		return buildQueryStatement.toString();
	}
	
}
