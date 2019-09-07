package com.example.matematicamente.graphics;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.matematicamente.R;

import androidx.appcompat.app.AppCompatActivity;

public class GraphicsActivity extends AppCompatActivity implements View.OnTouchListener {

    private View map;
    private View cursorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        map = findViewById(R.id.map);
        map.setOnTouchListener(this);
        cursorView = findViewById(R.id.cursor);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float dx = motionEvent.getX();
        float dy = motionEvent.getY();
        moveCursorCoordinate(dx, dy);
        return true;
    }

    protected void moveCursorCoordinate(float X, float Y) {
        int mDuration = 1000;
        float previousX = cursorView.getX();
        float previousY = cursorView.getY();
        ValueAnimator vaX = ValueAnimator.ofFloat(previousX, X);
        vaX.setDuration(mDuration);
        vaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                cursorView.setX((float) animation.getAnimatedValue());
            }
        });
        vaX.start();
        ValueAnimator vaY = ValueAnimator.ofFloat(previousY, Y);
        vaY.setDuration(mDuration);
        vaY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                cursorView.setY((float) animation.getAnimatedValue());
            }
        });
        vaY.start();
    }
}
