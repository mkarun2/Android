package com.ids.application.main;

import java.util.Date;

import com.ids.database.dao.ClassesDAO;
import com.ids.database.dao.UsersDAO;
import com.ids.database.model.Classes;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;

public class MainActivity extends Activity {
	
	private UsersDAO users_datasource;
	private ClassesDAO classes_datasource;
	
	private static final String LOG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		classes_datasource = new ClassesDAO(this);
		classes_datasource.open();
		
		Classes c = new Classes("CS442","DOP","Distributed Object Oriented Programming Using Middleware","LH 208",
				new Date(),new Date(),2);
		
		if(classes_datasource.insertUser(c) <= 0)
			Log.e(LOG, "Insert Failed");
		else
			Log.e(LOG, "Insert Success");
		
		classes_datasource.close();	
		
		//Button buttonClassStudy = (Button) findViewById(R.id.butEnterViewClass);
	    //buttonClassStudy.setOnClickListener(butEnterViewClassEvent);
	    
	    Button buttonClassStudy = (Button) findViewById(R.id.butAddEditCLassesStudySessions);
	    buttonClassStudy.setOnClickListener(butEnterViewClassEvent);

		Button buttonViewInstrTAs = (Button) findViewById(R.id.butEnterViewInstrTAs);
		buttonViewInstrTAs.setOnClickListener(butEnterViewInstrTAsEvent);

		ImageButton imageButton1 = (ImageButton) findViewById(R.id.imgButStudentProfile);
		imageButton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Showing student profile...", Toast.LENGTH_SHORT).show();
				Intent spIntent = new Intent(MainActivity.this, StudentProfile.class);
				startActivity(spIntent);
			}
		});

		ImageButton imageButton2 = (ImageButton) findViewById(R.id.imgButAboutUs);
		imageButton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "Showing calendar view...", Toast.LENGTH_SHORT).show();
//				Intent cvIntent = new Intent(MainActivity.this, SampleCalendarView.class);
//				startActivity(cvIntent);
				
				Toast.makeText(MainActivity.this, "Showing about us...", Toast.LENGTH_SHORT).show();
				Intent cvIntent = new Intent(MainActivity.this, AboutUs.class);
				startActivity(cvIntent);
			}
		});	
	}
	
	// responds to event generated when user clicks butEnterViewClass
	OnClickListener butEnterViewClassEvent = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			Toast.makeText(MainActivity.this, "Activating Enter/View Classes...", Toast.LENGTH_SHORT).show();
			Intent addEditClassIntent = new Intent(MainActivity.this, AddEditClassStudySessions.class);
			startActivity(addEditClassIntent);	    	  
		} // end method onClick
	}; // end OnClickListener button2ClickEvent

	// responds to event generated when user clicks button2
	OnClickListener butEnterViewInstrTAsEvent = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			Toast.makeText(MainActivity.this, "Activating InstructorTA...", Toast.LENGTH_SHORT).show();
			Intent instructorTAintent = new Intent(MainActivity.this, InstructorTA.class);
			startActivity(instructorTAintent);	    	  
		} // end method onClick
	}; // end OnClickListener button2ClickEvent

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
}
