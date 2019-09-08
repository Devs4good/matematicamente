package com.example.matematicamente.graphics;

import android.util.Log;
import android.util.Pair;

import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.HashMap;
import java.util.Map;

public class MyGraphSeries<E extends DataPointInterface> extends PointsGraphSeries {

    final float TOLERANCE = 50f;
    private Map<Pair<Double, Double>, Pair<Float, Float>> logicPointsToRealPoints = new HashMap<>();
    private Map<Pair<Float, Float>, Pair<Double, Double>> realPointsToLogicPoints = new HashMap<>();

    public MyGraphSeries(E[] data) {
        super(data);
        init();
    }

    @Override
    protected void registerDataPoint(float x, float y, DataPointInterface dp) {
        super.registerDataPoint(x, y, dp);
        Pair<Double, Double> logicPoint = new Pair<>(dp.getX(), dp.getY());
        Pair<Float, Float> realPoint = new Pair<>(x, y);
        logicPointsToRealPoints.put(logicPoint, realPoint);
        realPointsToLogicPoints.put(realPoint, logicPoint);

    }

    public Pair<Float, Float> getLocationForPoint(E point) {
        return logicPointsToRealPoints.get(new Pair<>(point.getX(), point.getY()));
    }

    public Pair<Double, Double> getPointForLocation(float x, float y) {
        Log.d("GRAPH", String.format("Looking for (%f;%f)", x, y));
        for (Map.Entry<Pair<Float, Float>, Pair<Double, Double>> entry : realPointsToLogicPoints.entrySet()) {
            Pair<Float, Float> key = entry.getKey();
            Pair<Double, Double> value = entry.getValue();
            float realX = key.first;
            float realY = key.second;
            Log.d("GRAPH", String.format("Having (%f;%f)", realX, realY));
            if (x < realX + TOLERANCE && x > realX - TOLERANCE
                    && y < realY + TOLERANCE && y > realY - TOLERANCE) {
                Log.d("GRAPH", String.format("Coincided !"));
                return value;
            }
        }
        return null;
    }

}
