package com.ids.database.connector;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ids.database.dao.ActivitiesDAO;
import com.ids.database.dao.ClassesDAO;
import com.ids.database.dao.ClassesUsersDAO;
import com.ids.database.dao.InstructorsDAO;
import com.ids.database.dao.OfficeHoursDAO;
import com.ids.database.dao.SchedulesDAO;
import com.ids.database.dao.StudySessionsDAO;
import com.ids.database.dao.UsersDAO;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	
	// Logger string
	private static final String LOG = "DatabaseHandler";

	// Database Version
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
	private static final String DATABASE_NAME = "courseTracker";
	

	//Basic constructor that accepts the current view context
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(UsersDAO.CREATE_TABLE_USERS);		
		db.execSQL(InstructorsDAO.CREATE_TABLE_INSTRUCTORS);
		db.execSQL(OfficeHoursDAO.CREATE_TABLE_OFFICEHOURS);
		db.execSQL(ClassesDAO.CREATE_TABLE_CLASSES);
		db.execSQL(ClassesUsersDAO.CREATE_CLASSES_USERS);
		db.execSQL(StudySessionsDAO.CREATE_STUDY_SESSIONS_TABLE);
		db.execSQL(SchedulesDAO.CREATE_TABLE_SCHEDULE);
		db.execSQL(ActivitiesDAO.CREATE_TABLE_ACTIVITIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + UsersDAO.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + InstructorsDAO.TABLE_INSTRUCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + OfficeHoursDAO.TABLE_OFFICEHOURS);
        db.execSQL("DROP TABLE IF EXISTS " + ClassesDAO.TABLE_CLASSES);
        db.execSQL("DROP TABLE IF EXISTS " + ClassesUsersDAO.TABLE_CLASSESUSERS);
        db.execSQL("DROP TABLE IF EXISTS " + StudySessionsDAO.TABLE_STUDY_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + SchedulesDAO.CREATE_TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + ActivitiesDAO.CREATE_TABLE_ACTIVITIES);
        
        // create new tables after dropping
        onCreate(db);
	}

}
