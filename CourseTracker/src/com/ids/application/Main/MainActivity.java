package com.ids.application.Main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.ids.coursetracker.R;
import com.ids.database.DAO.UsersDAO;
import com.ids.database.model.Users;

public class MainActivity extends Activity {

	private UsersDAO users_datasource;
	private static final String LOG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		users_datasource = new UsersDAO(this);
		users_datasource.open();
		
		Users userObj = new Users("Mohanakrishnakumar","Karunakaran","AVM","High School","Enjoyed");
		
		if(users_datasource.insertUser(userObj))
			Log.e(LOG, "Inserted Successfully");
		else
			Log.e(LOG, "Insert Failed");
		
		users_datasource.close();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
