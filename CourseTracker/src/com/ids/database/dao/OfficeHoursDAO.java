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
}
