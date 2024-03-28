package com.example.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.WindowManager;

public class Start extends AppCompatActivity {
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViewById(R.id.play).setOnClickListener(v -> {
            Intent intent = new Intent(Start.this, MainActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.info).setOnClickListener(v -> {
            Intent intent = new Intent(Start.this, Info.class);
            startActivity(intent);
        });

        findViewById(R.id.links).setOnClickListener(v -> {
            Intent intent = new Intent(Start.this, Links.class);
            startActivity(intent);
        });

        findViewById(R.id.exit).setOnClickListener(v -> {
            ResetDialog dialog = new ResetDialog(1);
            dialog.setCancelAction(dialog::dismiss);
            dialog.setRestartAction(() -> {
                finish();
                dialog.dismiss();
            });
            dialog.show(getSupportFragmentManager(), "reset_dialog");

        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
                // Check if it's a three-finger touch
                if (event.getPointerCount() == 3) {
                    // Consume the event to prevent further processing
                    return true;
                }
                break;
        }

        // Let the system handle the event for other cases
        return super.onTouchEvent(event);
    }
}