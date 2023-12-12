package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Start extends AppCompatActivity {
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViewById(R.id.play).setOnClickListener(v-> {
            Intent intent = new Intent(Start.this, MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.info).setOnClickListener(v-> {
            Intent intent = new Intent(Start.this, MainActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.links).setOnClickListener(v-> {

        });
        findViewById(R.id.exit).setOnClickListener(v-> {
            finish();
        });
    }
}