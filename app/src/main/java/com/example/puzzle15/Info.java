package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Info extends AppCompatActivity {
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences("STATE", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView moves = findViewById(R.id.moves);
        moves.setText("Moves: " + pref.getInt("TOP1", 0));

        Chronometer time = findViewById(R.id.timer);
        long baseTime = SystemClock.elapsedRealtime() - pref.getLong("TOP2", 0);
        time.setBase(baseTime);
        time.setFormat("Best time - %s");

        LinearLayout menu = findViewById(R.id.menu);
        menu.setOnClickListener(v -> finish());

        LinearLayout game = findViewById(R.id.game);
        game.setOnClickListener(v -> {
            startActivity(new Intent(Info.this, MainActivity.class));
            finish();
        });
    }
}