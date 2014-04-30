package com.ids.application.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

public class AboutUs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		Button buttonOK = (Button) findViewById(R.id.buttonOK);
		buttonOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}
}
