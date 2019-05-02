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
import com.test.calculus.Models.CourseModel;
import com.test.calculus.R;

import java.util.ArrayList;
import java.util.List;

public class ScoreFragment extends Fragment {

    private View v;
    private ListView scorelijst;
    private CourseListAdapter mAdapter;
    private List<CourseModel> courseModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_score, container, false);

        scorelijst = v.findViewById(R.id.scorelijst);

        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        courseModels.add(new CourseModel("Ford", "Thunderbird", "Baby Blue", "1967"));             // DUMMY DATA
        mAdapter = new CourseListAdapter(getContext(), 0, courseModels);
        scorelijst.setAdapter(mAdapter);

        return v;
    }
}
