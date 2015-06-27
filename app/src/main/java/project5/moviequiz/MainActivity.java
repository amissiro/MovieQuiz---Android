package project5.moviequiz;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    public TextView questionView, responseView, timerView, scoreView;
    int i = 0;
    boolean checkResponse = true;
    String answer;
    int correctScore = 0;
    int wrongScore = 0;
    int avgTime = 0;
    CountDownTimer timer;

    boolean paused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clearButtons();
        questionPicker();
        timerView = (TextView) findViewById(R.id.timerClock);

        //take quiz
        Button clickButton = (Button) findViewById(R.id.submitButton);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                radio();
                questionPicker();
                clearButtons();
            }
        });

        //see results

    }

    public void questionPicker() {

        try {
            Random randnum = new Random();
            int i = randnum.nextInt(10);
            String question = "";
            switch (i) {

                case 0:
                    question = firstQueryOutput();
                    break;
                case 1:
                    question = secondQueryOutput();
                    break;
                case 2:
                    question = thirdQueryOutput();
                    break;
                case 3:
                    question = fourthQueryOutput();
                    break;
                case 4:
                    question = fifthQueryOutput();
                    break;
                case 5:
                    question = sixthQueryOutput();
                    break;
                case 6:
                    question = seventhQueryOutput();
                    break;
                case 7:
                    question = eighthQueryOutput();
                    break;
                case 8:
                    question = ninthQueryOutput();
                    break;
                case 9:
                    question = tenthQueryOutput();
                    break;
            }

            questionView = (TextView) findViewById(R.id.questionView);
            questionView.setText(question);
        }
        catch (Exception e){

        }
    }

    public void radio() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        int count = radioGroup.getChildCount();

        for (int i = 0; i < count; i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof RadioButton) {

                RadioButton radioBtn = (RadioButton) o;
                boolean isChecked = radioBtn.isChecked();

                if (isChecked) {

                    if (radioBtn.getText() != null && !radioBtn.getText().toString().isEmpty()) {
                        if (answer.equals(radioBtn.getText())) {
                            correctScore++;
                            responseView = (TextView) findViewById(R.id.responseMessage);
                            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                            AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                            responseView.startAnimation(fadeIn);
                            responseView.startAnimation(fadeOut);
                            fadeIn.setDuration(500);
                            fadeIn.setFillAfter(true);
                            fadeOut.setDuration(500);
                            fadeOut.setFillAfter(true);
                            fadeOut.setStartOffset(500 + fadeIn.getStartOffset());
                            responseView.setText("Correct!");
                            responseView.setTextColor(Color.WHITE);
                            responseView.setBackgroundColor(Color.GREEN);
                            scoreView = (TextView) findViewById(R.id.scoreView);
                            scoreView.setText(Integer.toString(correctScore));
                        } else {
                            wrongScore++;
                            responseView = (TextView) findViewById(R.id.responseMessage);
                            AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
                            AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                            responseView.startAnimation(fadeIn);
                            responseView.startAnimation(fadeOut);
                            fadeIn.setDuration(500);
                            fadeIn.setFillAfter(true);
                            fadeOut.setDuration(500);
                            fadeOut.setFillAfter(true);
                            fadeOut.setStartOffset(500 + fadeIn.getStartOffset());
                            responseView.setText("Wrong!");
                            responseView.setTextColor(Color.WHITE);
                            responseView.setBackgroundColor(Color.RED);
                        }
                    }
                }
            }
        }
    }

    public void clearButtons() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
    }

    private String question1(String X) {

        return "Who directed the movie " + X + "?";
    }

    private String question2(String X) {

        return "When was the movie " + X + " released?";
    }

    private String question3(String X) {

        return "Which star was in the movie " + X + "?";
    }

    private String question4(String X) {

        return "Which star wasn't in the movie " + X + "?";
    }

    private String question5(String X, String Y) {

        return "In which movie the stars " + X + " and " + Y + " appear together?";
    }

    private String question6(String X) {

        return "Who directed the star " + X + "?";
    }

    private String question7(String X) {

        return "Who didn't direct the star " + X + "?";
    }

    private String question8(String X, String Y) {

        return "Which star appears in both movies " + X + " and " + Y + "?";
    }

    private String question9(String X) {

        return "Which star did not appear in the same movie with the star " + X + "?";
    }

    private String question10(String X, String Y) {

        return "Who directed the star " + X + " in year " + Y + "?";
    }


    public String firstQueryOutput() {

        Cursor cursor = TakeQuiz.db.firstQuery();
        List<String> directors = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String director = cursor.getString(cursor.getColumnIndex("director"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    directors.add(director);
                    titles.add(title);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = directors.get(rand);
        System.out.print(answer);
        //Log.i("answer",answer);

        String question = question1(titles.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(directors.get(i));
        }

        return question;
    }

    public String secondQueryOutput() {

        Cursor cursor = TakeQuiz.db.secondQuery();
        List<String> years = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String year = cursor.getString(cursor.getColumnIndex("year"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    years.add(year);
                    titles.add(title);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = years.get(rand);
        //      System.out.print(answer);

        String question = question2(titles.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(years.get(i));
        }

        return question;
    }


    public String thirdQueryOutput() {

        Cursor cursor = TakeQuiz.db.thirdQuery();
        List<String> stars = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String star = cursor.getString(cursor.getColumnIndex("last_name"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    stars.add(star);
                    titles.add(title);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = stars.get(rand);

        String question = question3(titles.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(stars.get(i));
        }

        return question;
    }

    public String fourthQueryOutput() {

        Cursor cursor = TakeQuiz.db.fourthQuery();
        List<String> stars = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String star = cursor.getString(cursor.getColumnIndex("group_concat(last_name)"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    stars.add(star);
                    titles.add(title);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = stars.get(rand).split(",")[0];

        int k = 0;
        if ((rand+1) <=3){
            k = rand+1;
        }
        else{
            k = rand-1;
        }

        String question = question4(titles.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        ((RadioButton) rbtnGrp.getChildAt(0)).setText(stars.get(k).split(",")[0]);
        ((RadioButton) rbtnGrp.getChildAt(1)).setText(answer);
        ((RadioButton) rbtnGrp.getChildAt(2)).setText(stars.get(k).split(",")[1]);
        ((RadioButton) rbtnGrp.getChildAt(3)).setText(stars.get(k).split(",")[2]);


        return question;
    }

    public String fifthQueryOutput() {

        Cursor cursor = TakeQuiz.db.fifthQuery();
        List<String> stars = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String star = cursor.getString(cursor.getColumnIndex("group_concat(last_name)"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    stars.add(star);
                    titles.add(title);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = titles.get(rand);

        String[] parts = stars.get(rand).split(",");

        String question = question5(parts[0], parts[1]);
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(titles.get(i));
        }

        return question;
    }

    public String sixthQueryOutput() {

        Cursor cursor = TakeQuiz.db.sixthQuery();
        List<String> directors = new ArrayList<String>();
        List<String> stars = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String director = cursor.getString(cursor.getColumnIndex("director"));
                    String star = cursor.getString(cursor.getColumnIndex("last_name"));
                    directors.add(director);
                    stars.add(star);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = directors.get(rand);

        String question = question6(stars.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(directors.get(i));
        }

        return question;
    }

    public String seventhQueryOutput() {
        Cursor cursor = TakeQuiz.db.seventhQuery();
        List<String> directors = new ArrayList<String>();
        List<String> stars = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String director = cursor.getString(cursor.getColumnIndex("group_concat(director)"));
                    String star = cursor.getString(cursor.getColumnIndex("last_name"));
                    directors.add(director);
                    stars.add(star);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = directors.get(rand).split(",")[0];

        int k = 0;
        if ((rand+1) <=3){
            k = rand+1;
        }
        else{
            k = rand-1;
        }

        String question =  question7(stars.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        ((RadioButton) rbtnGrp.getChildAt(0)).setText(answer);
        ((RadioButton) rbtnGrp.getChildAt(1)).setText(directors.get(k).split(",")[0]);
        ((RadioButton) rbtnGrp.getChildAt(2)).setText(directors.get(k).split(",")[1]);
        ((RadioButton) rbtnGrp.getChildAt(3)).setText(directors.get(k).split(",")[2]);



        return question;
    }


    public String eighthQueryOutput() {

        Cursor cursor = TakeQuiz.db.eighthQuery();
        List<String> titles = new ArrayList<String>();
        List<String> stars = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String star = cursor.getString(cursor.getColumnIndex("last_name"));
                    String title = cursor.getString(cursor.getColumnIndex("group_concat(title)"));
                    stars.add(star);
                    titles.add(title);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = stars.get(rand);

        String[] parts = titles.get(rand).split(",");
        String question = question8(parts[0], parts[1]);
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(stars.get(i));
        }

        return question;
    }

    public String ninthQueryOutput() {

        Cursor cursor = TakeQuiz.db.ninthQuery();
        List<String> stars = new ArrayList<String>();
        List<String> titles = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String star = cursor.getString(cursor.getColumnIndex("group_concat(last_name)"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    stars.add(star);
                    titles.add(title);
                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = stars.get(rand).split(",")[0];

        int k = 0;
        if ((rand+1) <=3){
            k = rand+1;
        }
        else{
            k = rand-1;
        }


        String question = question9(stars.get(k).split(",")[3]);

        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        ((RadioButton) rbtnGrp.getChildAt(0)).setText(answer);
        ((RadioButton) rbtnGrp.getChildAt(1)).setText(stars.get(k).split(",")[0]);
        ((RadioButton) rbtnGrp.getChildAt(2)).setText(stars.get(k).split(",")[1]);
        ((RadioButton) rbtnGrp.getChildAt(3)).setText(stars.get(k).split(",")[2]);

        return question;
    }

    public String tenthQueryOutput() {

        Cursor cursor = TakeQuiz.db.tenthQuery();
        List<String> directors = new ArrayList<String>();
        List<String> stars = new ArrayList<String>();
        List<String> years = new ArrayList<String>();


        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String director = cursor.getString(cursor.getColumnIndex("director"));
                    String star = cursor.getString(cursor.getColumnIndex("last_name"));
                    String year = cursor.getString(cursor.getColumnIndex("year"));

                    directors.add(director);
                    stars.add(star);
                    years.add(year);

                }while (cursor.moveToNext());
            }
        }

        cursor.close();

        Random randnum = new Random();
        int rand = randnum.nextInt(4);
        answer = directors.get(rand);

        String question =  question10(stars.get(rand), years.get(rand));
        RadioGroup rbtnGrp = (RadioGroup) findViewById(R.id.radioGroup);
        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(directors.get(i));
        }

        return question;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("avg", avgTime);
        savedInstanceState.putInt("cscore", correctScore);
        savedInstanceState.putInt("wscore", wrongScore);
        savedInstanceState.putString("question", questionView.getText().toString());
        savedInstanceState.putString("timer", timerView.getText().toString());

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton radioBtn = (RadioButton) o;
                savedInstanceState.putString("rbutton"+Integer.toString(i), radioBtn.getText().toString());
            }
        }
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        correctScore = savedInstanceState.getInt("cscore");
        wrongScore = savedInstanceState.getInt("wscore");
        avgTime = savedInstanceState.getInt("avg");
        questionView.setText(savedInstanceState.getString("question"));
        timerView.setText(savedInstanceState.getString("timer"));

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton radioBtn = (RadioButton) o;
                radioBtn.setText(savedInstanceState.getString("rbutton" + Integer.toString(i)));
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        timer.cancel();
        Log.i("onPause", "pause called");
        getIntent().putExtra("azret", correctScore);

        paused = true;
    }

    @Override
    public void onResume()
    {

        super.onResume();
        Log.i("onResume", "resumeCalled");


        int sc  = getIntent().getIntExtra("azret", 0);
        scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText(Integer.toString(sc));

        Log.i("scpre", Integer.toString(sc));

        long startTime  = getIntent().getLongExtra("CurrMiliSec", 0);



        if(startTime == 0)
            startTime = 30000;
        else
            paused = false;

        timer = new CountDownTimer(startTime, 1000) {
            public void onTick(long millisUntilFinished) {
                timerView.setText("" + millisUntilFinished / 1000);
                if(!paused)
                    getIntent().putExtra("CurrMiliSec", millisUntilFinished);
            }

            public void onFinish() {
                timerView.setText("done!");
                Intent intent = new Intent(MainActivity.this, Scores.class);

                String cor = Integer.toString(correctScore);
                String wrong = Integer.toString(wrongScore);
                if ((correctScore+wrongScore)!=0) {
                    avgTime = 180 / (correctScore + wrongScore);
                }
                else{

                    avgTime = 180;
                }
                String avg = Integer.toString(avgTime);
                Bundle bundle = new Bundle();
                bundle.putString("correct", cor);
                bundle.putString("wrong", wrong);
                bundle.putString("average", avg);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }.start();
    }
}