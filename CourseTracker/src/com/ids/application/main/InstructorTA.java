package com.ids.application.main;

import android.app.ExpandableListActivity;
import android.app.TabActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.os.AsyncTask;
import android.content.Intent;

public class InstructorTA extends TabActivity {
	private DatabaseConnector db;
    private Cursor  childrenCursor;
    private Cursor parentCursor;
    
    private SimpleCursorTreeAdapter instructorTAcursorTreeAdapter; // adapter for ListView
    private int instructorOrTA=1;  //instructor = 1, TA = 2
    private String[] parent_from;
    private int[] parent_to;
    private String[] children_from;
    private int[] children_to;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_ta_explistview);       
        
        TabHost tabs = getTabHost(); //(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();
        
        Intent instrIntent = new Intent().setClass(this, Instructors.class);
        TabSpec sp = tabs.newTabSpec("Instructors");
        sp.setIndicator("Instructors");
        sp.setContent(instrIntent);
        tabs.addTab(sp);
        
        Intent taIntent = new Intent().setClass(this, TAs.class);
        TabSpec tsp = tabs.newTabSpec("TAs");
        tsp.setIndicator("TA's");
        tsp.setContent(taIntent);
        tabs.addTab(tsp);
        
        Intent addIntent = new Intent().setClass(this, AddEditInstructorTA.class);
        TabSpec add = tabs.newTabSpec("AddNewInstructTA");
        add.setIndicator("+Add");
        add.setContent(addIntent);
        tabs.addTab(add);
        
//        TabHost.TabSpec instrTab = tabs.newTabSpec("Instructors");
//        instrTab.setContent(R.id.tabInstructors);
//        //instrTab.
//        tabs.addTab(instrTab);
//
//        // Home
//        TabHost.TabSpec taTab = tabs.newTabSpec("TA's");
//        taTab.setContent(R.id.tabTAs);
//        tabs.addTab(taTab);
//
//        // Home
//        TabHost.TabSpec addTab = tabs.newTabSpec("+Add");
//        addTab.setContent(R.id.tabAdd);
//        tabs.addTab(addTab);
        
    } //end of onCreate method

    @Override
    protected void onResume() 
    {
       super.onResume(); // call super's onResume method
       ShowInstructorOrTA();
     } // end method onResume


    private void ShowInstructorOrTA()
    {
       if (instructorOrTA == 1)
			Toast.makeText(InstructorTA.this, "Showing instructors...", Toast.LENGTH_SHORT).show();
       else
			Toast.makeText(InstructorTA.this, "Showing TAs...", Toast.LENGTH_SHORT).show();
    		
       parent_from = new String[]{"name", "office", "phone", "email", "_id"};
       parent_to = new int[]{R.id.nameTextView, R.id.officeTextView,
     	                      R.id.phoneTextView, R.id.emailTextView};
       children_from = new String[]{"officeHour", "_id"};
       children_to = new int[]{R.id.officeHourTextView};
       instructorTAcursorTreeAdapter = new SimpleCursorTreeAdapter(
               getApplicationContext(),
               parentCursor,
               R.layout.listrow_group,
               R.layout.listrow_group,
               parent_from,
               parent_to,
               R.layout.listrow_details,
               R.layout.listrow_details,
               children_from,
               children_to) {

           @Override
           protected Cursor getChildrenCursor(Cursor groupCursor) {
               db = new DatabaseConnector(InstructorTA.this);
               db.open();
               childrenCursor = db.getInstructorOfficeHours(groupCursor.getLong(0));
               db.close();
               return childrenCursor;
           }
       }; //end of instantiating instructorTAcursorTreeAdapter
       
       //setListAdapter(instructorTAcursorTreeAdapter);       
 	   new GetInstructorOrTA_Task().execute((Object[])null);
 	   
    } // end of ShowInstructorOrTA() 
    
    // performs database query outside GUI thread
    private class GetInstructorOrTA_Task extends AsyncTask<Object, Object, Cursor> 
    {
       DatabaseConnector databaseConnector = 
          new DatabaseConnector(InstructorTA.this);

       // perform the database access
       @Override
       protected Cursor doInBackground(Object... params)
       {
          databaseConnector.open();

          if (instructorOrTA == 1)
          {
         	 return databaseConnector.getAllInstructors();
          }
          
          else //(instructorOrTA == 2
          {
         	 return databaseConnector.getAllAssistants();
          }
       } // end method doInBackground

       // use the Cursor returned from the doInBackground method
       @Override
       protected void onPostExecute(Cursor result)
       {
    	  instructorTAcursorTreeAdapter.changeCursor(result); // set the adapter's Cursor
          databaseConnector.close();
       } // end method onPostExecute
    } // end class GetContactsTask
    
    
    // create the Activity's menu from a menu resource XML file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
       super.onCreateOptionsMenu(menu);
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.instructor_ta_menu, menu);
       return true;
    } // end method onCreateOptionsMenu

    // handle choice from options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
 	   switch (item.getItemId())
 	   {
 	       case R.id.backToFrame1:
 		       finish();
 		       return true;

 		   case R.id.addNewInstructor:
 			   instructorOrTA = 1;
 			   Intent addNewInstructor = new Intent(InstructorTA.this, AddEditInstructorTA.class);
 			   startActivity(addNewInstructor);
 			   return true;
 			   
 		   case R.id.addNewTA:
 			   instructorOrTA = 2;
 			   Intent addNewTA = new Intent(InstructorTA.this, AddEditInstructorTA.class);
 			   startActivity(addNewTA);
 			   return true;
 			   
 		   case R.id.showInstructors:
 		   	   instructorOrTA = 1;
 		       ShowInstructorOrTA();
 		       return true;
 		       
 		   case R.id.showTAs:
 		       instructorOrTA = 2;
 		       ShowInstructorOrTA();
 		       return true;  
 		   
 	     default: return true;
 	   }
    } // end method onOptionsItemSelected    
    
} //end of class ExpandableLV
