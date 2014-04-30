package com.ids.application.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

public class StudentProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_profile);
		Button buttonOK = (Button) findViewById(R.id.buttonOK);
		buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
}
