package com.example.puzzle15;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int N = 4;
    private boolean isWin;
    private Button[][] buttons = new Button[4][4];
    private List<String> values = new ArrayList<>();
    private int x = 3;
    private int y = 3;
    private int count = 0;
    private SharedPreferences pref;
    private Chronometer chronometer;
    private long time;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView ref = findViewById(R.id.refresh);
        ref.setOnClickListener(v -> refresh());
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        pref = this.getSharedPreferences("STATE", Context.MODE_PRIVATE);
        String date = pref.getString("STATE", "!");
        count = pref.getInt("COUNT", 0);
        time = pref.getLong("TIME", 0);
        if (date.equals("!")) {
            refresh();
            return;
        }
        String[] s = date.split("#");
        for (int i = 0; i < s.length; i++) {
            values.add(s[i]);
        }
        RelativeLayout relativeLayout = findViewById(R.id.relative);

        TextView v = findViewById(R.id.countt);
        v.setText("Count: " + count);
        for (int i = 0; i < 16; i++) {
            int currentX = i / 4;
            int currentY = i % 4;

            Button currentBtn = (Button) relativeLayout.getChildAt(i);
            buttons[currentX][currentY] = currentBtn;
            currentBtn.setVisibility(View.VISIBLE);
            currentBtn.setOnClickListener(this::onClick);
            currentBtn.setTag(new Point(currentX, currentY));
        }
        loadData();
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime() + time);
        chronometer.start();
    }

    private void refresh() {
        ImageView image = findViewById(R.id.refresh);
        image.setClickable(false);
        count = 0;
        values.clear();
        initViews();
        initData();
        x = 3;
        y = 3;
        shuffle();
        loadData();
        ImageView image2 = findViewById(R.id.refresh);
        image2.setOnClickListener(v -> refresh());
        image2.setClickable(true);
        while (!isSolvable()) {
            refresh();
        }
        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pref.edit().putLong("TIME", 0).apply();
        time = 0;
        chronometer.start();
    }

    private void shuffle() {
//        Collections.shuffle(values);
        values.add("0");
    }

    private void initViews() {
        RelativeLayout relativeLayout = findViewById(R.id.relative);

        TextView v = findViewById(R.id.countt);
        v.setText("Count: " + count);
        for (int i = 0; i < 16; i++) {
            int currentX = i / 4;
            int currentY = i % 4;

            Button currentBtn = (Button) relativeLayout.getChildAt(i);
            buttons[currentX][currentY] = currentBtn;
            currentBtn.setVisibility(View.VISIBLE);
            currentBtn.setOnClickListener(this::onClick);
            currentBtn.setTag(new Point(currentX, currentY));
            if (currentX == 3 && currentY == 3) {
                currentBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        pref.edit().putBoolean("PAUSE", true).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chronometer.stop();
        time = chronometer.getBase() - SystemClock.elapsedRealtime();
        pref.edit().putLong("TIME", time).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        time = pref.getLong("TIME", 0);
        chronometer.setBase(SystemClock.elapsedRealtime() + time);
        chronometer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("TTT", "onStop");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                sb.append(buttons[i][j].getText()).append("#");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        pref.edit().putString("STATE", sb.toString()).apply();
        pref.edit().putInt("COUNT", count).apply();
        pref.edit().putLong("TIME", chronometer.getBase() - SystemClock.elapsedRealtime()).apply();
        if (isWin) {
            pref.edit().putLong("TIME", 0).apply();
        }
    }

    private void loadData() {
        for (int i = 0; i < 16; i++) {
            if (values.get(i).equals("0")) {
                buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
                x = i / 4;
                y = i % 4;
            }
            buttons[i / 4][i % 4].setText(values.get(i));
        }
    }

    private void initData() {
        for (int i = 1; i < 16; i++) {
            values.add(String.valueOf(i));
        }
    }

    private void onClick(View view) {
        Button clicked = (Button) view;
        Point cur = (Point) clicked.getTag();
        boolean canMove = Math.abs(cur.getX() - x) + Math.abs(cur.getY() - y) == 1;
        if (canMove) {
            buttons[x][y].setText(clicked.getText());
            buttons[x][y].setVisibility(View.VISIBLE);
            clicked.setText("0");
            clicked.setVisibility(View.INVISIBLE);
            x = cur.getX();
            y = cur.getY();
            count++;
            TextView v = findViewById(R.id.countt);
            v.setText("Count: " + count);
            if (x == 3 && y == 3) {
                checkWin();
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void checkWin() {
        RelativeLayout container = findViewById(R.id.relative);
        for (int i = 1; i < 16; i++) {
            Button cur = (Button) container.getChildAt(i - 1);
            if (!cur.getText().equals(String.valueOf(i))) {
                return;
            }
        }
        chronometer.stop();
        time = chronometer.getBase() - SystemClock.elapsedRealtime();
        Intent intent = new Intent(MainActivity.this, WinActivity.class);
        intent.putExtra("MOVES", count);
        intent.putExtra("TIME", time);
        time = 0;
        count = 0;
        pref.edit().putLong("TIME", time).apply();
        pref.edit().putInt("COUNT", count).apply();
        startActivity(intent);
        isWin = true;
        finish();
    }

    private int getInvCount(int[] arr) {
        int inv_count = 0;
        for (int i = 0; i < N * N - 1; i++) {
            for (int j = i + 1; j < N * N; j++) {
                if (arr[j] != 0 && arr[i] != 0 && arr[i] > arr[j]) inv_count++;
            }
        }
        return inv_count;
    }

    private boolean isSolvable() {
        int[] arr;
        arr = convertTo1DArray();
        int invCount = getInvCount(arr);

        if (N % 2 == 1) return invCount % 2 == 0;
        else {
            int pos = x;
            if (pos % 2 == 1) return invCount % 2 == 0;
            else return invCount % 2 == 1;
        }

    }

    private int[] convertTo1DArray() {
        int[] arr = new int[N * N];
        int k = 0;
        for (int i = 0; i < N * N; i++) {
            if (values.get(i).equals("")) {
                arr[k++] = 0;
                continue;
            }
            arr[k++] = Integer.parseInt(values.get(i));
        }
        return arr;
    }

    public class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
