package com.test.calculus.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_score, container, false);

        scorelijst = v.findViewById(R.id.scorelijst);

        scoreModels.add(new scoreModel("Lucy", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Tom", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Harry", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Kees", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Tamara", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Demi", "random oefening", "Baby Blue"));             // DUMMY DATA
        scoreModels.add(new scoreModel("Ford", "Thunderbird", "Baby Blue"));             // DUMMY DATA

        mAdapter = new CourseListAdapter(getContext(), 0, scoreModels);
        scorelijst.setAdapter(mAdapter);

        return v;
    }
}
