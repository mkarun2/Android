package com.ids.database.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ids.database.helper.DatabaseHelper;

public class OfficeHoursDAO {
		// Table Name
		public static final String TABLE_OFFICEHOURS = "officehours";

		// Database handle
		private SQLiteDatabase database;

		// Database handle creator class
		private DatabaseHelper dbHelper;

		// current context of the application
		public OfficeHoursDAO(Context context) {
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
}
