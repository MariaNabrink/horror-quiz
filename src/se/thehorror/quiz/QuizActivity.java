package se.thehorror.quiz;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	TextView nameText;
	ImageView timeImage;
	TextView viewQuestion;
	RadioGroup radioGroup;
	RadioButton radioButton1;
	RadioButton radioButton2;
	RadioButton radioButton3;
	RadioButton radioButton4;
	TextView timeText;
	String id;
	JSONObject answers;
	String question;
	JSONArray questions;
	int currentQuestion = 0;
	Random random;
	int score = 0;
	CountDownTimer timer;
	int nrOfQuestions = 1;
	int nrOfWrongAnswer = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		nameText = (TextView) findViewById(R.id.nameText);
		timeText = (TextView) findViewById(R.id.timeText);
		viewQuestion = (TextView) findViewById(R.id.questionTextView);
		radioGroup = (RadioGroup) findViewById(R.id.radioAnswersGroup);
		radioButton1 = (RadioButton) findViewById(R.id.radioAnswer1);
		radioButton2 = (RadioButton) findViewById(R.id.radioAnswer2);
		radioButton3 = (RadioButton) findViewById(R.id.radioAnswer3);
		radioButton4 = (RadioButton) findViewById(R.id.radioAnswer4);

		getJson();
		setQuestions(currentQuestion);

		Intent intent = getIntent();
		String name = intent.getStringExtra("Name");
		if (intent.hasExtra("Name")) {
			Toast.makeText(QuizActivity.this,
					"Welcome, " + name + ", to your doom!", 5000).show();
		}
		nameText.setText("User: " + name);
	}

	public void countDownTimer() {
		timer = new CountDownTimer(11000, 1000) {
			public void onTick(long millisUntilFinished) {
				timeText.setText("" + millisUntilFinished / 1000);
			}

			public void onFinish() {
				timeText.setText("Time's up!");
				nrOfWrongAnswer++;
				if (nrOfQuestions == 10 || nrOfWrongAnswer == 5) {
					Intent intent = new Intent(QuizActivity.this,
							LoserActivity.class);
					startActivity(intent);
				}
				nrOfQuestions++;
				setQuestions(currentQuestion);
			}
		}.start();
	}

	public void getJson() {
		try {
			String jsonFile = "questions_and_answers.json";
			String json = null;
			try {
				InputStream is = getAssets().open(jsonFile);
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();

				json = new String(buffer, "UTF-8");

			} catch (IOException ex) {
				ex.printStackTrace();
			}
			questions = new JSONObject(json).getJSONArray("Questions");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void setQuestions(int questionIndex) {

		random = new Random();

		questionIndex = random.nextInt(20 + 1);

		List<Integer> rdmNumber = new ArrayList<Integer>();

		for (int i = 0; i <= 3; i++)

			rdmNumber.add(i);

		Collections.shuffle(rdmNumber);

		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			try {

				question = questions.getJSONObject(questionIndex).getString(
						"question");
				id = questions.getJSONObject(questionIndex).getString("id");
				answers = questions.getJSONObject(questionIndex).getJSONObject(
						"answers");
				viewQuestion.setText(question);

				switch (i) {

				case 0:
					((RadioButton) radioGroup.getChildAt(i)).setText(answers
							.get(answers.names().get(rdmNumber.get(0))
									.toString()).toString());
					break;
				case 1:
					((RadioButton) radioGroup.getChildAt(i)).setText(answers
							.get(answers.names().get(rdmNumber.get(1))
									.toString()).toString());
					break;
				case 2:
					((RadioButton) radioGroup.getChildAt(i)).setText(answers
							.get(answers.names().get(rdmNumber.get(2))
									.toString()).toString());
					break;
				case 3:
					((RadioButton) radioGroup.getChildAt(i)).setText(answers
							.get(answers.names().get(rdmNumber.get(3))
									.toString()).toString());
					break;
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		countDownTimer();
	}

	public void nextQuestion(View view) {

		nrOfQuestions++;
		currentQuestion++;
		Intent rightIntent = new Intent(this, EndActivity.class);

		if (nrOfQuestions == 10) {
			Intent intent = new Intent(QuizActivity.this, LoserActivity.class);
			startActivity(intent);
		}

		timer.cancel();

		try {
			if (radioButton1.isChecked()
					&& answers.get("right answer").toString()
							.matches(radioButton1.getText().toString())) {
				score++;
				Toast.makeText(this, score, 3000).show();
			} else if (radioButton2.isChecked()
					&& answers.get("right answer").toString()
							.matches(radioButton2.getText().toString())) {
				score++;
				Toast.makeText(this, score, 3000).show();
			} else if (radioButton3.isChecked()
					&& answers.get("right answer").toString()
							.matches(radioButton3.getText().toString())) {
				score++;
				Toast.makeText(this, score, 3000).show();
			} else if (radioButton4.isChecked()
					&& answers.get("right answer").toString()
							.matches(radioButton4.getText().toString())) {
				score++;
				Toast.makeText(this, score, 3000).show();
			} else {
				nrOfWrongAnswer++;
				Toast.makeText(this, "WRONG!!!!", 2000).show();
				if (nrOfWrongAnswer == 5) {
					Intent wrongIntent = new Intent(this, LoserActivity.class);
					startActivity(wrongIntent);
				}
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		if (nrOfQuestions == 10) {

			rightIntent.putExtra("score", score);
			startActivity(rightIntent);
		}

		setQuestions(currentQuestion);

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

}
