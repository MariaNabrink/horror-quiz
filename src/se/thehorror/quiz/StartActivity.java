package se.thehorror.quiz;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class StartActivity extends Activity {

	String name;
	EditText nameEdit;
	ImageView logoImage;
	CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		nameEdit = (EditText) findViewById(R.id.setName);
		logoImage = (ImageView) findViewById(R.id.logoImage);

		timer = new CountDownTimer(400, 100) {

			@Override
			public void onTick(long millisUntilFinished) {
				if (millisUntilFinished <= 400 && millisUntilFinished >= 300) {
					logoImage.setImageResource(R.drawable.horrorquizlogo3);
				} else if (millisUntilFinished <= 200
						&& millisUntilFinished > 100) {
					logoImage.setImageResource(R.drawable.horrorquizlogo2);
				} else {
					logoImage.setImageResource(R.drawable.horrorquizlogo1);
				}

			}

			@Override
			public void onFinish() {
				timer.start();

			}
		}.start();
	}

	@Override
	protected void onPause() {
		timer.cancel();
		super.onPause();
	}

	@Override
	protected void onResume() {
		timer.start();
		super.onResume();
	}

	public void startTheGameButton(View view) {
		name = nameEdit.getText().toString();

		MediaPlayer scarySound = MediaPlayer.create(getBaseContext(),
				R.raw.monster);
		scarySound.start();

		if (name.isEmpty()) {
			Toast.makeText(StartActivity.this,
					"Enter your name or suffer the consequences!", 3000).show();
			return;
		}

		Intent toTheQuiz = new Intent(this, QuizActivity.class);
		toTheQuiz.putExtra("Name", name);
		startActivity(toTheQuiz);
	}

}
