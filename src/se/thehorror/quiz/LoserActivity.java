package se.thehorror.quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class LoserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loser);
		
		MediaPlayer scarySound = MediaPlayer.create(getBaseContext(),
		R.raw.chuckielaugh);
		scarySound.start();
	}

	public void restartGameButton(View view) {
		Intent restart = new Intent(this, StartActivity.class);
		startActivity(restart);
	}
}
