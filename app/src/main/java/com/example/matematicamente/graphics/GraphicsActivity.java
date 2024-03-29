package com.example.matematicamente.graphics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matematicamente.R;
import com.example.matematicamente.WonActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class GraphicsActivity extends AppCompatActivity {

    private View cursorView;
    private ListView pointListView;
    private GraphView graph;

    MyGraphSeries<DataPoint> invisibleSeries;
    MyGraphSeries<DataPoint> originSeries;
    LineGraphSeries<DataPoint> lineSeries;
    PointsGraphSeries<DataPoint> pointSeries;
    int lastCorrectMove = -1;
    DataPoint[] points;
    TextView pointsView;
    int score = 0;
    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        cursorView = findViewById(R.id.cursor);
        graph = findViewById(R.id.graph);
        pointListView = findViewById(R.id.pointList);
        pointsView = findViewById(R.id.pointsText);
        setUpPoints();
        setUpGraph();
        setUpSeries();
    }

    private void setUpPoints() {
        points = new DataPoint[]{
                new DataPoint(-4, 2),
                new DataPoint(-3, 4),
                new DataPoint(-1, 0),
                new DataPoint(1, -1),
                new DataPoint(2, 3)
        };

        List<String> pointsArr = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            DataPoint p = points[i];
            int x = (int) p.getX();
            int y = (int) p.getY();
            pointsArr.add(String.format("Puerto %d: X=%d Y=%d", (i + 1), x, y));
        }
        itemsAdapter = new ArrayAdapter<>(this, R.layout.simple_list_item, pointsArr);
        pointListView.setAdapter(itemsAdapter);
    }

    private boolean hasFinished() {
        return lastCorrectMove == points.length - 1;
    }

    private void setUpGraph() {
        graph.setTitleTextSize(32);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinX(-5);
        graph.getViewport().setMaxX(5);
        graph.getViewport().setMinY(-5);
        graph.getViewport().setMaxY(5);
    }

    private void setUpSeries() {
        invisibleSeries = new MyGraphSeries<>(points);
        invisibleSeries.setColor(Color.TRANSPARENT);
        graph.addSeries(invisibleSeries);
        originSeries = new MyGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
        originSeries.setColor(Color.TRANSPARENT);
        graph.addSeries(originSeries);
        pointSeries = new PointsGraphSeries<>();
        graph.addSeries(pointSeries);
        lineSeries = new LineGraphSeries<>();
        graph.addSeries(lineSeries);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int[] contentLocation = new int[2];
        graph.getLocationOnScreen(contentLocation);
        float originalXPosition = event.getX();
        float originalYPosition = event.getY();
        Log.d("GRAPH", String.format("Touch will be dispatched in (%f;%f)", originalXPosition, originalYPosition));
        float xPosition = event.getX() - contentLocation[0];
        float yPosition = event.getY() - contentLocation[1];
        Log.d("GRAPH", String.format("Coordinates after transformed (%f;%f)", xPosition, yPosition));

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Pair<Double, Double> touchCoordinates = invisibleSeries.getPointForLocation(xPosition, yPosition);
                if (touchCoordinates == null) {
                    Toast.makeText(this, "Intentá de nuevo", Toast.LENGTH_SHORT).show();
                    break;
                }
                DataPoint dataPoint = new DataPoint(touchCoordinates.first, touchCoordinates.second);
                for (int i = 0; i < points.length; i++) {
                    DataPoint currentPoint = points[i];
                    if (currentPoint.getX() == dataPoint.getX() && currentPoint.getY() == dataPoint.getY()) {
                        // currentPoint is the Point tapped on
                        if (i == lastCorrectMove + 1) {
                            // currentPoint is the correct point to tap

                            updateScore(score + 1);
                            lastCorrectMove = i;
                            updatePointListRemoving(lastCorrectMove);
                            showCorrectLineTo(dataPoint);
                            moveCursorCoordinate(xPosition, yPosition);
                            if (hasFinished()) {
                                succeededGame();
                            } else {
                                Toast.makeText(this, "Correcto!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Intentá de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private void updatePointListRemoving(int index) {
        int x = (int) points[index].getX();
        int y = (int) points[index].getY();
        String listItem = String.format("Puerto %d: X=%d Y=%d", (index + 1), x, y);
        itemsAdapter.remove(listItem);
    }


    private void updateScore(int newScore) {
        score = newScore;
        pointsView.setText(Integer.toString(score));
    }

    private void moveCursorCoordinate(float X, float Y) {
        int mDuration = 1000;
        float previousX = getCursorX();
        float previousY = getCursorY();
        ValueAnimator vaX = ValueAnimator.ofFloat(previousX, X);
        vaX.setDuration(mDuration);
        vaX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                setCursorX((float) animation.getAnimatedValue());
            }
        });
        vaX.start();
        ValueAnimator vaY = ValueAnimator.ofFloat(previousY, Y);
        vaY.setDuration(mDuration);
        vaY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                setCursorY((float) animation.getAnimatedValue());
            }
        });
        vaY.start();
    }

    private void setCursorX(float x) {
        cursorView.setX(x - cursorView.getWidth() / 2f);
    }

    private void setCursorY(float y) {
        cursorView.setY(y - cursorView.getHeight() / 2f);
    }

    private float getCursorX() {
        return cursorView.getX() + cursorView.getWidth() / 2f;
    }

    private float getCursorY() {
        return cursorView.getY() + cursorView.getHeight() / 2f;
    }

    private void showCorrectLineTo(DataPoint point) {
        lineSeries.appendData(point, false, points.length);
        pointSeries.appendData(point, false, points.length);
    }

    private void succeededGame() {
        final Context context = this;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context, WonActivity.class);
                startActivity(intent);
            }
        }, 750);
    }

}
