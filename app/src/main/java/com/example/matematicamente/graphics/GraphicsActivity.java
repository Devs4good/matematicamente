package com.example.matematicamente.graphics;

import android.os.Bundle;
import android.widget.TextView;

import com.softmoore.android.graphlib.Function;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;


import com.example.matematicamente.R;

import androidx.appcompat.app.AppCompatActivity;

public class GraphicsActivity extends AppCompatActivity {

    GraphView graphView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        Graph graph = new Graph.Builder()
                .build();
        graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
        setTitle("Empty Graph");
        textView = findViewById(R.id.graph_view_label);
        textView.setText("Graph of Axes");
    }
}
