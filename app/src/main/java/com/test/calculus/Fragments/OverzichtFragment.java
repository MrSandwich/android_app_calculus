package com.test.calculus.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.calculus.Models.scoreModel;
import com.test.calculus.R;
import java.util.ArrayList;
import java.util.List;

public class OverzichtFragment extends Fragment implements View.OnClickListener {

    // globale variabelen

    public static final String PREFS_NAME = "GedeeldeGegevens";
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("scores");

    private View v;
    private PieChart scoreChart;
    private Button uploadKnop;
    private EditText naamInput;
    private int vragenCorrect;
    private String soortOefening;
    private long maxid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_overzicht, container, false);

        // Initialiseer input componenten
        uploadKnop = v.findViewById(R.id.uploadknop);
        naamInput = v.findViewById(R.id.naamInput);

        // Listener opzetten voor de upload knop
        uploadKnop.setOnClickListener(this);

        // Design van de piechart vastzetten
        pieChartSettings();

        // de score en de soort oefening ophalen uit sharedpreferences
        Context context = getActivity();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        vragenCorrect = settings.getInt("vragenCorrect", 0);
        soortOefening = settings.getString("soortOefening", "None");

        // start de score visualisatie
        setChartData(vragenCorrect);

        // haal het aantal entries in de Firebase database op
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // auto increment opstellen zodat scores niet overschreven worden
                if (dataSnapshot.exists()){
                    maxid = (dataSnapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;
    }
    private void pieChartSettings(){

        scoreChart = v.findViewById(R.id.chart);
        Description d = new Description();
        d.setText("Aantal beantwoordde vragen");
        d.setTextColor(Color.rgb(255,255,255));
        d.setTextSize(18f);
        d.setYOffset(5f);
        scoreChart.setDescription(d);
        scoreChart.setBackgroundColor(000);
        scoreChart.setHoleColor(000);
        scoreChart.setTouchEnabled(true);
        scoreChart.getLegend().setEnabled(false);
        scoreChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        scoreChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);


    }
    private void setChartData(int vragenCorrect) {
        List<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(vragenCorrect, "Juist"));
        yValues.add(new PieEntry(10 - vragenCorrect, "Fout"));


        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(49,160,18));
        colors.add(Color.rgb(202,13,13));

        PieDataSet dataSet = new PieDataSet(yValues, "Vragen");

        dataSet.setColors(colors);
        dataSet.setValueTextSize(20f);

        PieData data = new PieData(dataSet);
        scoreChart.setData(data); // bind dataset aan chart.
        scoreChart.invalidate();  // Aanroepen van een redraw
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadknop:

                String ingevoerdeNaam = naamInput.getText().toString();
                if (TextUtils.isEmpty(ingevoerdeNaam) == true){
                    Snackbar.make(v, "Voer eerst een naam in.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{

                    // De data
                    scoreModel data = new scoreModel(naamInput.getText().toString(), soortOefening, Integer.toString(vragenCorrect));

                    // upload score naar Firebase met auto increment
                    myRef.child(String.valueOf(maxid+1)).setValue(data);

                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new StartFragment()).commit();

                }
                break;
        }
    }
}
