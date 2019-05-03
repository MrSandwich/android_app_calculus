package com.test.calculus.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.test.calculus.R;

import java.util.ArrayList;
import java.util.List;

public class OverzichtFragment extends Fragment {

    private View v;
    private PieChart scoreChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_overzicht, container, false);

        scoreChart = v.findViewById(R.id.chart);
        Description d = new Description();
        d.setText("Aantal behaalde punten");
        d.setTextColor(Color.rgb(255,255,255));
        d.setTextSize(16f);
        scoreChart.setDescription(d);
        scoreChart.setBackgroundColor(000);
        scoreChart.setHoleColor(000);
        scoreChart.setTouchEnabled(true);
        scoreChart.getLegend().setEnabled(false);
        scoreChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        scoreChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setData(6);

        return v;
    }

    private void setData(int aantal) {
        List<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(aantal, "Juist"));
        yValues.add(new PieEntry(10 - aantal, "Fout"));


        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(49,160,18));
        colors.add(Color.rgb(202,13,13));

        PieDataSet dataSet = new PieDataSet(yValues, "Vragen");

        dataSet.setColors(colors);//colors);
        dataSet.setValueTextSize(20f);

        PieData data = new PieData(dataSet);
        scoreChart.setData(data); // bind dataset aan chart.
        scoreChart.invalidate();  // Aanroepen van een redraw
    }
}
