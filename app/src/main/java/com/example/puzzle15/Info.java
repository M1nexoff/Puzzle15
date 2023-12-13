package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView moves = findViewById(R.id.moves);
        moves.setText("Moves: " + getIntent().getIntExtra("TOP1", 0));

        Chronometer time = findViewById(R.id.timer);
        long baseTime = SystemClock.elapsedRealtime() - getIntent().getLongExtra("TOP2", 0);
        time.setBase(baseTime);
        time.setFormat("Best time - %s");

        LinearLayout menu = findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout game = findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Info.this, MainActivity.class));
                finish();
            }
        });
    }
}