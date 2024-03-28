package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

public class Links extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();
        
        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 3) {
                    return true;
                }
                break;
        }

            return super.onTouchEvent(event);
    }
}