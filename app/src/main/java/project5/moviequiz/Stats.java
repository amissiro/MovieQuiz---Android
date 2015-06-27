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


public class Stats extends ActionBarActivity {

    TextView gView, cView, wView, aView;
    int id;
    int games;
    int correct;
    int wrong;
    int avg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

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

        gView = (TextView) findViewById(R.id.gView);
        gView.setText(Integer.toString(games));

        cView = (TextView) findViewById(R.id.cView);
        cView.setText(Integer.toString(correct));

        wView = (TextView) findViewById(R.id.wView);
        wView.setText(Integer.toString(wrong));

        aView = (TextView) findViewById(R.id.aView);
        aView.setText(Integer.toString(avg));



        Button clickButton1 = (Button) findViewById(R.id.button);
        clickButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Stats.this, TakeQuiz.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
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
