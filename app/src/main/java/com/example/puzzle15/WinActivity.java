package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private Chronometer timer;
    private long elapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_activity);
        pref = getInstance("STATE",MODE_PRIVATE);
        TextView movesView = findViewById(R.id.moves);
        timer = findViewById(R.id.timer);

        int moves = getIntent().getIntExtra("MOVES", 0);
        long time = getIntent().getLongExtra("TIME", 0);
        movesView.setText("Moves: " + moves);
        time = pref.getLong("TIME",0);
        timer.setFormat("Time - %s");
        timer.setBase(SystemClock.elapsedRealtime() + time);
        int top1 = pref.getInt("TOP1", 0);
        long top2 = pref.getLong("TOP2", 0);
        if (moves < top1 || top1 == 0) {
            top1 = moves;
        }
        if (time > top2 || top2 == 0) {
            top2 = time;
        }
        TextView top_1 = findViewById(R.id.top1);
        Chronometer top_2 = findViewById(R.id.top2);
        pref.edit().putInt("TOP1", top1).apply();
        pref.edit().putLong("TOP2", top2).apply();
        top_1.setText("Best moves: " + top1);
        top_2.setFormat("Best time - %s");
        top_2.setBase(SystemClock.elapsedRealtime() + top2);
        findViewById(R.id.refresh).setOnClickListener(v-> {
            Intent intent = new Intent(WinActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.back).setOnClickListener(v-> {
            finish();
        });
    }
    private SharedPreferences getInstance(String name,int i) {
        if(pref==null){
            pref = this.getSharedPreferences(name,MODE_PRIVATE);
        }
        return pref;
    }

}