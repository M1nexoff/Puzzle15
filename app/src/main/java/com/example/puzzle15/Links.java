package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        findViewById(R.id.telegram).setOnClickListener(v->{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/M1nexofficial")));
        });
        findViewById(R.id.gita).setOnClickListener(v->{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/gitauz")));
        });
        findViewById(R.id.github).setOnClickListener(v->{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/M1nexoff/Puzzle15")));
        });
        findViewById(R.id.menu).setOnClickListener(v->{
            finish();
        });
        findViewById(R.id.game).setOnClickListener(v->{
            Intent intent = new Intent(Links.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}