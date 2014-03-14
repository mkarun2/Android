package ids494.mkarun2.simplearithmeticgameapp;

import ids494.mkarun2.utility.Operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class MainActivity extends Activity implements TextToSpeech.OnInitListener{

	private Random random; // random number generator
	private Handler handler; // used to delay lading the next question
	private TextView answerTextView; // displays Correct! or Incorrect!
	private TextView questionNumberTextView; // shows current question #
	private TextView arithmeticQuestionTextView; // displays a flag
	private TableLayout buttonTableLayout; // table of answer Buttons
	private Integer guessRows;
	private Integer correctAnswer;
	private Animation shakeAnimation; 
	private Integer totalGuesses;	// number of guessess made 
	private Integer totalCorrectAnswers; // number of correct guessess made
	private TextToSpeech tts;
	
	// create constants for each menu id
	private final int CHOICES_MENU_ID = Menu.FIRST;
	private final int REGIONS_MENU_ID = Menu.FIRST + 1;
	
	private Map<Operator,Boolean> arithmeticOperationsMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// call the super class method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		guessRows = 1; // default to one row of choices
		correctAnswer = 0;
		totalGuesses = 0;
		totalCorrectAnswers = 0;
		
	    random = new Random(); // initialize the random number generator
	    handler = new Handler(); // used to perform delayed operations
	    tts = new TextToSpeech(this, this);
	    
	    //Data structures
	    arithmeticOperationsMap = new HashMap<Operator,Boolean>();
	    
	    //enable all operations by default
	    for(Operator operation : Operator.values()){
	    	arithmeticOperationsMap.put(operation, true);
	    }
	    
	    // get references to GUI components
	    questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
	    arithmeticQuestionTextView = (TextView) findViewById(R.id.arithmeticQuestionTextView);
	    answerTextView = (TextView) findViewById(R.id.answerTextView);
	    buttonTableLayout = (TableLayout) findViewById(R.id.buttonTableLayout);
	    
	    // load the shake animation that's used for incorrect answers
	    shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.incorrect_shake); 
	    shakeAnimation.setRepeatCount(3); // animation repeats 3 times 
  
	    // set questionNumberTextView's text
	    questionNumberTextView.setText(getResources().getString(R.string.Question) + " 1 " +  
	    getResources().getString(R.string.of) + " 10");
	    
	    startGame();	    
	}
	
	/**
	 * Start the game
	 */
	private void startGame(){
		   
		answerTextView.setText(""); // clear answerTextView 

	      // display the number of the current question in the quiz
	    questionNumberTextView.setText(
	         getResources().getString(R.string.Question) + " " + 
	         (totalCorrectAnswers + 1) + " " + 
	         getResources().getString(R.string.of) + " 10");
	    
		arithmeticQuestionTextView.setText("");
	    
		// clear prior answer Buttons from TableRows
	    for (int row = 0; row < buttonTableLayout.getChildCount(); ++row)
	         ((TableRow) buttonTableLayout.getChildAt(row)).removeAllViews();
	    
	    Integer operand2 = 0;
	    Integer operand1 = 0;
	    
		Operator questionOperation = getRandomOperator();		    
	    if(Operator.getOperatorValue(questionOperation).equals("/")){
	    	int numerator = 1;
	    	do{
	    		operand1 = getRandomInteger(questionOperation,0,100);
		    	operand2 = getRandomInteger(questionOperation,0,100);
		    	numerator = operand1 * operand2;
		    	operand2 = (operand1 < operand2) ? operand1 : operand2;
		    	operand1 = numerator;
	    	}while(operand1 > 625);
	    }else{
	    	operand1 = getRandomInteger(questionOperation,0,100);
	    	operand2 = getRandomInteger(questionOperation,0,100);
	    }
	    
	    // set the question text view for testing
	    arithmeticQuestionTextView.setText(Integer.toString(operand1) + 
	    		Operator.getOperatorValue(questionOperation) + 
	    		Integer.toString(operand2) + "= ?");
	    
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	     // add 3, 6, or 9 answer Buttons based on the value of guessRows
		for (int row = 0; row < guessRows; row++) 
		{
	         TableRow currentTableRow = getTableRow(row);

	         // place Buttons in currentTableRow
	         for (int column = 0; column < 3; column++) 
	         {
	            // inflate guess_button.xml to create new Button
	            Button newGuessButton = (Button) inflater.inflate(R.layout.guess_button, null);	      
	            newGuessButton.setText(getRandomInteger(null,100,200).toString());
	            
	            // register answerButtonListener to respond to button clicks
	            newGuessButton.setOnClickListener(guessButtonListener);
	            currentTableRow.addView(newGuessButton);
	         } 
	      } 
	      
	      correctAnswer = this.getCorrectAnswer(questionOperation, operand1, operand2);
	      
	      // randomly replace one Button with the correct answer
	      int row = random.nextInt(guessRows); // pick random row
	      int column = random.nextInt(3); // pick random column	
	      TableRow randomTableRow = getTableRow(row); // get the TableRow
	      ((Button)randomTableRow.getChildAt(column)).setText(correctAnswer.toString());
	}
	
	private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
	
	private Integer getCorrectAnswer(Operator operator, Integer operand1, Integer operand2){
		switch(operator){
		case ADDITION:
			return operand1 + operand2;
		case SUBRACTION:
			return operand1 - operand2;
		case MULTIPLICATION:
			return operand1 * operand2;
		case DIVISION:
			return operand1 / operand2;
		}
		return null;
	}
	
	// returns the specified TableRow
   private TableRow getTableRow(int row)
   {
      return (TableRow) buttonTableLayout.getChildAt(row);
   } // end method getTableRow
	   
	/**
	 * Return random operators
	 * @return
	 */
	protected Operator getRandomOperator(){
		List<Operator> keys = new ArrayList<Operator>(arithmeticOperationsMap.size());
		for (Map.Entry<Operator, Boolean> entry : arithmeticOperationsMap.entrySet())
		{
			if(entry.getValue()){
				keys.add(entry.getKey());
			}		
		}		
		
		if(keys.size() == 0) {
			showError("Please select an operation");
		    return Operator.ADDITION;
		}
		
		Operator randomOperator = keys.get(random.nextInt(keys.size()));
		return randomOperator;
	}
	
	/**
	 * Return random number for the operator passed
	 * @param operation
	 * @return
	 */
	protected Integer getRandomInteger(Operator operation,Integer low,Integer high){
		
		if(operation == null) return random.nextInt(high-low) + low;
		
		switch(operation){
		case ADDITION: case SUBRACTION:
			low = 0;
			high = 100;
			return random.nextInt(high-low) + low;
		case MULTIPLICATION:
			low = 1;
			high = 25;
			return random.nextInt(high-low) + low;
		case DIVISION:
			low = 1;
			high = 625;
			return random.nextInt(high-low) + low;
		}		
		return 1;
	}

	private void submitGuess(Button guessButton){
		int guess = Integer.parseInt(guessButton.getText().toString());
		int answer = correctAnswer;
		totalGuesses++;
		
		if(guess == answer){
			totalCorrectAnswers++;
			
			// display "Correct!" in green text
	         answerTextView.setText(answer + "!");
	         answerTextView.setTextColor(getResources().getColor(R.color.correct_answer));
	         
	         speakOut("Correct");
	         
	         disableButtons(); // disable all answer Buttons
	         
	         // if the user has correctly identified 10 answers
	         if (totalCorrectAnswers == 10) 
	         {
	        	 // create a new AlertDialog Builder
	             AlertDialog.Builder builder = new AlertDialog.Builder(this);

	             builder.setTitle(R.string.reset_quiz); // title bar string
	             
	             // set the AlertDialog's message to display game results
	             builder.setMessage(String.format("%d %s, %.02f%% %s", 
	                totalGuesses, getResources().getString(R.string.guesses), 
	                (1000 / (double) totalGuesses), 
	                getResources().getString(R.string.correct)));

	             builder.setCancelable(false); 
	             
	             // add "Reset Quiz" Button                              
	             builder.setPositiveButton(R.string.reset_quiz,
	                new DialogInterface.OnClickListener()                
	                {                                                       
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                	   resetGame();                                   
	                   } // end method onClick                              
	                } // end anonymous inner class
	             ); // end call to setPositiveButton
	             
	             // create AlertDialog from the Builder
	             AlertDialog resetDialog = builder.create();
	             resetDialog.show(); // display the Dialog
	         }
	         else // answer is correct but quiz is not over 
	         {
	            // load the next flag after a 1-second delay
	            handler.postDelayed(
	               new Runnable()
	               { 
	                  @Override
	                  public void run()
	                  {
	                	  startGame();
	                  }
	               }, 500); // 1000 milliseconds for 1-second delay
	         } // end else
		}
		else // guess was incorrect  
	    {
	         // play the animation
			arithmeticQuestionTextView.startAnimation(shakeAnimation);

	         // display "Incorrect!" in red 
	         answerTextView.setText(R.string.incorrect_answer);
	         answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer));
	         guessButton.setEnabled(false); // disable the incorrect answer
	         speakOut("Incorrect");
	      } // end else
	}
	
	private void resetGame(){
		//guessRows = 1; // default to one row of choices
		correctAnswer = 0;
		totalGuesses = 0;
		totalCorrectAnswers = 0;
		
		answerTextView.setText(""); // clear answerTextView
		
		arithmeticQuestionTextView.setText("");
			    
		 // clear prior answer Buttons from TableRows
	    for (int row = 0; row < buttonTableLayout.getChildCount(); ++row)
	         ((TableRow) buttonTableLayout.getChildAt(row)).removeAllViews();
	    
	    startGame();
	}
	
	private void disableButtons()
   {
      for (int row = 0; row < buttonTableLayout.getChildCount(); ++row)
      {
         TableRow tableRow = (TableRow) buttonTableLayout.getChildAt(row);
         for (int i = 0; i < tableRow.getChildCount(); ++i)
            tableRow.getChildAt(i).setEnabled(false);
      } // end outer for
   } // end method disableButtons
	
	// called when a guess Button is touched
    private OnClickListener guessButtonListener = new OnClickListener() 
    {
       @Override
       public void onClick(View v) 
       {
          submitGuess((Button) v); // pass selected Button to submitGuess
       } // end method onClick
    }; // end answerButtonListener

    private void showError(String errorMessage){
    	// create a new AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.error); // title bar string
        
        // set the AlertDialog's message to display game results
        builder.setMessage(errorMessage);

        builder.setPositiveButton("OK",null);        
                
        // create AlertDialog from the Builder
        AlertDialog resetDialog = builder.create();
        resetDialog.show(); // display the Dialog
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		 super.onCreateOptionsMenu(menu);                        
         
	      // add two options to the menu - "Choices" and "Regions"
	      menu.add(Menu.NONE, CHOICES_MENU_ID, Menu.NONE, R.string.choices);             
	      menu.add(Menu.NONE, REGIONS_MENU_ID, Menu.NONE, R.string.regions);             

		return true;
	}
	
	 // called when the user selects an option from the menu
	   @Override
	   public boolean onOptionsItemSelected(MenuItem item) 
	   {
	      // switch the menu id of the user-selected option
	      switch (item.getItemId()) 
	      {
	         case CHOICES_MENU_ID:
	            // create a list of the possible numbers of answer choices
	            final String[] possibleChoices = getResources().getStringArray(R.array.guessesList);

	            // create a new AlertDialog Builder and set its title
	            AlertDialog.Builder choicesBuilder = new AlertDialog.Builder(this);         
	            choicesBuilder.setTitle(R.string.choices);
	         
	            // add possibleChoices's items to the Dialog and set the 
	            // behavior when one of the items is clicked
	            choicesBuilder.setItems(R.array.guessesList, new DialogInterface.OnClickListener()                    
	            {                                                        
	                  public void onClick(DialogInterface dialog, int item) 
	                  {                                                     
	                     // update guessRows to match the user's choice     
	                     guessRows = Integer.parseInt(possibleChoices[item].toString()) / 3;          
	                     resetGame(); // reset the quiz                     
	                  } // end method onClick                               
	               } // end anonymous inner class
	            );  // end call to setItems                             
	         
	            // create an AlertDialog from the Builder
	            AlertDialog choicesDialog = choicesBuilder.create();
	            choicesDialog.show(); // show the Dialog            
	            return true; 
	            
	         case REGIONS_MENU_ID:
	        	 
	        	 Iterator<Operator> it = arithmeticOperationsMap.keySet().iterator();
	        	 final List<Operator> arithmeticOperationNames = new ArrayList<Operator>();

	        	 while(it.hasNext())
	        		 arithmeticOperationNames.add(it.next());


	             // create an AlertDialog Builder and set the dialog's title
	             AlertDialog.Builder regionsBuilder = new AlertDialog.Builder(this);
	             regionsBuilder.setTitle(R.string.regions);
	             
	             // replace _ with space in region names for display purposes
	             String[] displayNames = new String[arithmeticOperationNames.size()+1];
	             int i = 0;
	             for (; i < arithmeticOperationNames.size(); ++i)
	                 displayNames[i] = Operator.getOperatorValue(arithmeticOperationNames.get(i));
	             
	             displayNames[i] = "All Combinations";
	             
	             regionsBuilder.setItems(displayNames, new DialogInterface.OnClickListener()                    
	             {                                                        
	                  public void onClick(DialogInterface dialog, int item) 
	                  {                                                     
	                     // update operators to match the user's choice     
	                	  switch(item){
	                	  case 0: 
	                		  setAllFalseOperations(Operator.MULTIPLICATION);
	                		  break;
	                	  case 1: 
	                		  setAllFalseOperations(Operator.DIVISION);
	                		  break;
	                	  case 2: 
	                		  setAllFalseOperations(Operator.ADDITION);
	                		  break;
	                	  case 3: 
	                		  setAllFalseOperations(Operator.SUBRACTION);
	                		  break;
	                	  case 4:
	                		  // all combinations
	                		  setAllFalseOperations(null);
	                		  break;
	                	  }
	                     resetGame();                     
	                  }                              
	                } 
	             );  
	         	                         
	             // create a dialog from the Builder 
	             AlertDialog regionsDialog = regionsBuilder.create();
	             regionsDialog.show(); // display the Dialog
	             return true;
	      }
	      return super.onOptionsItemSelected(item);
	   }
	   
	 private void setAllFalseOperations(Operator operation){
		 // set all combinations
		 if(operation == null){
			 for(Map.Entry<Operator,Boolean> map : arithmeticOperationsMap.entrySet()){
				 map.setValue(true);
			 }
		 }else{ // selected operator alone			 
			 for(Map.Entry<Operator,Boolean> map : arithmeticOperationsMap.entrySet()){
				 map.setValue(false);
			 }
			 arithmeticOperationsMap.put(operation,true);
		 }
	 }
	
	@Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }  
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
	}

}
