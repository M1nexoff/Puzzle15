package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.ImageView;
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
        time.setBase(getIntent().getLongExtra("TOP2", SystemClock.elapsedRealtime()));
        time.setFormat("Best time - %s");
        LinearLayout menu = findViewById(R.id.menu);
        menu.setOnClickListener(v-> finish());
        LinearLayout game = findViewById(R.id.game);
        game.setOnClickListener(v->{
            Intent intent = new Intent(Info.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}