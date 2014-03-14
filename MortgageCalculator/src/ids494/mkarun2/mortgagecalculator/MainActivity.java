package ids494.mkarun2.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	private static final String LOAN_AMOUNT = "LOAN_AMOUNT";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";
	private static final String MORTGAGE_LENGTH = "MORTGAGE_LENGTH";
	private double loanAmount;
	private double currentAnnualInterestRate; // tip % set with the SeekBar
	private int currentMortgageLength;
	private TextView currentAnnualRateTextView;
	private EditText loanAmountHandler;
	private SeekBar annualRateSeekBarHandler;
	private EditText totalPaymentHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			loanAmount = 0.0;
			currentAnnualInterestRate = 4;
			currentMortgageLength = 10;
		} else {
			loanAmount = savedInstanceState.getDouble(LOAN_AMOUNT);
			currentAnnualInterestRate = savedInstanceState.getInt(CUSTOM_PERCENT);
			currentMortgageLength = savedInstanceState.getInt(MORTGAGE_LENGTH);
		}

		// Handlers
		currentAnnualRateTextView = (TextView) findViewById(R.id.annualRateTextView);
		loanAmountHandler = (EditText) findViewById(R.id.lamt);
		annualRateSeekBarHandler = (SeekBar) findViewById(R.id.annualInterestRate);
		totalPaymentHandler = (EditText) findViewById(R.id.totalPayment);

		// Listeners
		loanAmountHandler.addTextChangedListener(loanAmountHandlerListener);
		annualRateSeekBarHandler.setOnSeekBarChangeListener(annualInterestRateSeekBarListener);
	}

	private OnSeekBarChangeListener annualInterestRateSeekBarListener = new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

			currentAnnualInterestRate = 0.25 * seekBar.getProgress();
			currentAnnualRateTextView.setText(currentAnnualInterestRate + "%");
			
			double totalPayment = calculateMortgage();
			totalPaymentHandler.setText(Double.toString(totalPayment));
		} 
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		} 

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		} 
	}; 

	private TextWatcher loanAmountHandlerListener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert loan amount text to a double
			try {
				loanAmount = Double.parseDouble(s.toString());
			} catch (NumberFormatException e) {
				loanAmount = 0.0;
			}

			double totalPayment = calculateMortgage();
			totalPaymentHandler.setText(Double.toString(totalPayment));
		}

		@Override
		public void afterTextChanged(Editable s) {
		} // end method afterTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} // end method beforeTextChanged
	};

	public void onRadioButtonClicked(View view) {

		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.ten:
			if (checked){
				this.currentMortgageLength = 10;
				double totalPayment = calculateMortgage();
				totalPaymentHandler.setText(Double.toString(totalPayment));
			}	
			break;
		case R.id.fifteen:
			if (checked){
				this.currentMortgageLength = 15;
				double totalPayment = calculateMortgage();
				totalPaymentHandler.setText(Double.toString(totalPayment));
			}
			break;
		case R.id.twenty:
			if (checked){
				this.currentMortgageLength = 20;
				double totalPayment = calculateMortgage();
				totalPaymentHandler.setText(Double.toString(totalPayment));
			}
			break;
		default:
			this.currentMortgageLength = 10;
			totalPaymentHandler.setText(Double.toString(0.0));
			break;
		}
	}
	
	private double calculateMortgage(){
		double monthlyInterestRate = 0.0;
		int months = 0;
		double monthlyPayment = 0.0;
		double totalPayment = 0.0;
		
		monthlyInterestRate = ((double) currentAnnualInterestRate )/(12 * 100);
		months = (this.currentMortgageLength * 12);
		monthlyPayment = (this.loanAmount * monthlyInterestRate)/(1 - (Math.pow((1 + monthlyInterestRate), -months)));
		totalPayment = monthlyPayment * months;
		
		return totalPayment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putDouble(LOAN_AMOUNT, loanAmount);
		outState.putDouble(CUSTOM_PERCENT, currentAnnualInterestRate);
		outState.putInt(MORTGAGE_LENGTH, currentMortgageLength);
	}
}
