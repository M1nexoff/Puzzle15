package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_activity);
        pref = getSharedPreferences("STATE", MODE_PRIVATE);

        TextView movesView = findViewById(R.id.moves);
        Chronometer timer = findViewById(R.id.timer);

        int moves = getIntent().getIntExtra("MOVES", 0);
        long time = getIntent().getLongExtra("TIME", 0);
        int elapsedMillis = (int) (SystemClock.elapsedRealtime() - time);
        int minutes = (elapsedMillis / 1000) / 60;
        int seconds = (elapsedMillis / 1000) % 60;
        movesView.setText("Moves: " + moves);
        timer.setBase(SystemClock.elapsedRealtime() - elapsedMillis);
        timer.setFormat("Time taken - %s");

        int top1 = pref.getInt("TOP1", 0);
        long top2 = pref.getLong("TOP2", 0);
        if (moves < top1 || top1 == 0) {
            top1 = moves;
        }
        if (elapsedMillis < top2 || top2 == 0) {
            top2 = elapsedMillis;
        }
        TextView top_1 = findViewById(R.id.top1);
        Chronometer top_2 = findViewById(R.id.top2);
        pref.edit().putInt("TOP1", top1).apply();
        pref.edit().putLong("TOP2", top2).apply();
        top_1.setText("Best moves: " + top1);
        top_2.setBase(SystemClock.elapsedRealtime() - top2);
        top_2.setFormat("Best time - %s");
        findViewById(R.id.refresh).setOnClickListener(v-> {
            Intent intent = new Intent(WinActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}