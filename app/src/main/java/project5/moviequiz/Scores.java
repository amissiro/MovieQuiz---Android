package project5.moviequiz;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Scores extends ActionBarActivity {

    int id;
    int games;
    int correct;
    int wrong;
    int avg;

    TextView correctView, wrongView, avgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);


        correctView = (TextView) findViewById(R.id.correctView);
        wrongView = (TextView) findViewById(R.id.wrongView);
        avgView = (TextView) findViewById(R.id.avgView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String Correct =  extras.getString("correct");
        String Wrong = extras.getString("wrong");
        String Average = extras.getString("average");

        correctView.setText(Correct);
        wrongView.setText(Wrong);
        avgView.setText(Average);


        Cursor cursor = TakeQuiz.db.fetchStats();
        List<String> stats = new ArrayList<String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getInt(cursor.getColumnIndex("stats_id"));
                    games = cursor.getInt(cursor.getColumnIndex("games"));
                    correct = cursor.getInt(cursor.getColumnIndex("correct"));
                    wrong = cursor.getInt(cursor.getColumnIndex("wrong"));
                    avg = cursor.getInt(cursor.getColumnIndex("avg"));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();

        int totCorrect = Integer.parseInt(Correct) + correct;
        int totWrong = Integer.parseInt(Wrong) + wrong;
        int totAvg = (Integer.parseInt(Average) + avg)/(games+1);
        TakeQuiz.db.insertRecords((games+1),totCorrect,totWrong,totAvg);
        Button clickButton = (Button) findViewById(R.id.tryButton);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scores.this, TakeQuiz.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
