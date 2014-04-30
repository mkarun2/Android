package com.ids.application.main;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
 
public class SampleCalendarView extends Activity {
	
	CalendarView cal;
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        
        cal = (CalendarView) findViewById(R.id.calendarView1);
        
        cal.setOnDateChangeListener(new OnDateChangeListener() {
			
		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(),"Selected Date is\n\n"
				+dayOfMonth + " : " + (month+1) + " : " + year , 
				Toast.LENGTH_LONG).show();
		}
	});
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_view_menu, menu);
        return true;
    }
    
   // handle choice from options menu
   @Override
   public boolean onOptionsItemSelected(MenuItem item) 
   {
		   switch (item.getItemId())
		   {
		   case R.id.backToFrame1:
			   finish();
			   return true;
			   
		   default: return true;
		   }
   } // end method onOptionsItemSelected
    
}
