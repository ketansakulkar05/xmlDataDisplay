package com.k10.xmlpiechartassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Templates;

import static android.view.View.TEXT_ALIGNMENT_TEXT_END;
import static android.view.View.TEXT_DIRECTION_INHERIT;

public class DisplayPieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_piechart);

        if (getIntent().hasExtra("pieData")) ;
        {
            Map<String, ArrayList<String>> caseData = (Map<String, ArrayList<String>>) getIntent().getExtras().get("pieData");
            diaplayPieChart(caseData);
        }

    }

    private void diaplayPieChart(Map<String, ArrayList<String>> caseData) {
        PieChart piechart = findViewById(R.id.piechart);
        piechart.setUsePercentValues(false);
        piechart.setExtraOffsets(5,10,5,5);
        piechart.setTextDirection(TEXT_ALIGNMENT_TEXT_END);
        piechart.setDragDecelerationFrictionCoef(0.99f);
        piechart.setDrawHoleEnabled(false);
        piechart.setHoleColor(Color.WHITE);
        piechart.setTransparentCircleRadius(61f);


        ArrayList testCaseList = new ArrayList();
        ArrayList status = new ArrayList();

        int Count = 0;
        for (Map.Entry<String, ArrayList<String>> entry : caseData.entrySet()) {
            testCaseList.add(new Entry((float)entry.getValue().size(), Count));
            status.add(entry.getKey());
            Count++;
        }
        PieDataSet dataSet = new PieDataSet(testCaseList, "Number of Tests");
        dataSet.setSliceSpace(0.5f);
        dataSet.setSelectionShift(5f);
        dataSet.setColor(Color.WHITE);
        PieData data = new PieData(status, dataSet);
        data.setValueTextSize(10f);
        piechart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        piechart.animateXY(5000, 5000);



    }

}
