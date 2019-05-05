package com.test.calculus.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    private View v;
    private ListView scorelijst;
    private CourseListAdapter mAdapter;
    private List<scoreModel> scoreModels = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("scores");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_score, container, false);

        scorelijst = v.findViewById(R.id.scorelijst);

        /*scoreModels.add(new scoreModel("Lucy", "random oefening", "1"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Tom", "random oefening", "8"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Harry", "random oefening", "9"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Kees", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Tamara", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Demi", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Ford", "Thunderbird", "Baby Blue"));             // DUMMY DATA */

        mAdapter = new CourseListAdapter(getContext(), 0, scoreModels);
        scorelijst.setAdapter(mAdapter);

        // Read from the database
        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("Dit lees ik uit", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/



        return v;
    }
}
