package se.thehorror.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EndActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);
	}
	
	
	public void restartGameButton(View view) {
		Intent restart = new Intent(this, StartActivity.class);
		startActivity(restart);
	}
	
	
	public void smsButton(){
		
	}
	
	
	public void emailButton(){
		
	}
}
