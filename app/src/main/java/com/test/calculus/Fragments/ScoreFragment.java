package com.test.calculus.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.calculus.CourseListAdapter;
import com.test.calculus.Models.scoreModel;
import com.test.calculus.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreFragment extends Fragment {

    final Handler handler = new Handler();
    private View v;
    private ListView scorelijst;
    private CourseListAdapter mAdapter;
    private List<scoreModel> scoreModels = new ArrayList<>();
    private long maxid;

    DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_score, container, false);

        scorelijst = v.findViewById(R.id.scorelijst);

        // Haal het aantal entries in de database op
        count();

        //Wacht tot het aantal entries bekend zijn en haal alle data op
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateScoreList(maxid);
            }
        }, 2500);

        return v;
    }
    public void count (){

        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                "Data ophalen...", Snackbar.LENGTH_LONG);
        snackBar.show();

        myRef = FirebaseDatabase.getInstance().getReference().child("scores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                maxid = (dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void updateScoreList(long aantEntries){

        // controle of de database leeg is
        if (aantEntries != 0){

            for (long i = 1; i < aantEntries+1; i++){

                myRef = FirebaseDatabase.getInstance().getReference().child("scores").child(Long.toString(i));

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String naam = dataSnapshot.child("naam").getValue().toString();
                        String score = dataSnapshot.child("score").getValue().toString();
                        String oefening = dataSnapshot.child("soortOef").getValue().toString();


                        scoreModels.add(new scoreModel(naam, oefening, score));

                        mAdapter = new CourseListAdapter(getContext(), 0, scoreModels);
                        scorelijst.setAdapter(mAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        }

    }
}
