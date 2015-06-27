package project5.moviequiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class TakeQuiz extends ActionBarActivity {
    static DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        db = new DataBase(this);

        Button clickButton1 = (Button) findViewById(R.id.resultsButton);
        clickButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TakeQuiz.this, Stats.class);
                startActivity(intent);
            }
        });


        Button clickButton = (Button) findViewById(R.id.startButton);
        clickButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TakeQuiz.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button clickButton2 = (Button) findViewById(R.id.quitButton);
        clickButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.insertRecords(0,0,0,0);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onDestroy(){
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}
