package com.ids.application.Main;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.ids.coursetracker.R;
import com.ids.database.dao.ClassesDAO;
import com.ids.database.dao.UsersDAO;
import com.ids.database.model.Classes;

public class MainActivity extends Activity {

	private UsersDAO users_datasource;
	private ClassesDAO classes_datasource;
	
	private static final String LOG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		classes_datasource = new ClassesDAO(this);
		classes_datasource.open();
		
		Classes c = new Classes("CS442","DOP","Distributed Object Oriented Programming Using Middleware","LH 208",
				new Date(),new Date(),2);
		
		if(classes_datasource.insertUser(c) <= 0)
			Log.e(LOG, "Insert Failed");
		else
			Log.e(LOG, "Insert Success");
		
		classes_datasource.close();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
