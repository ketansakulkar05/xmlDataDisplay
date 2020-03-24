package com.k10.xmlpiechartassignment;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class LineGraphActivity extends AppCompatActivity {

    private LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = findViewById(R.id.LineChart_view);

        if (getIntent().hasExtra("LineData")) ;
        {
            Map<String, Long> componentData = (Map<String, Long>) getIntent().getExtras().get("LineData");
            displayLineChart(componentData);
        }

    }

    private void displayLineChart(Map<String, Long> componentData) {

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xbvalues = new ArrayList<>();

        Long minVal = Collections.min(componentData.values());
        int Count = 0;
        for (Map.Entry<String, Long> entry : componentData.entrySet()) {


            yValues.add(new Entry((long) ((entry.getValue()) / 1000) / 60 /*entry.getValue()*/, Count));

            String value = String.valueOf(Count);
            xbvalues.add(value);
            Count++;
        }

        LineDataSet set1 = new LineDataSet(yValues, "Component Execution Timing");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);


        LineData data = new LineData(xbvalues, dataSets);
        lineChart.setData(data);
        lineChart.setDescription("Line chart for component");
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                long seconds = (long) (e.getVal() * 60);
                Toast.makeText(LineGraphActivity.this, "Total Time " + String.valueOf(convertSecondsToHMmSs(seconds)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }



}
