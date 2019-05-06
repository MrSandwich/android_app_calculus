package com.test.calculus.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.test.calculus.R;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public static final String PREFS_NAME = "GedeeldeGegevens";

    private View v;
    private Button audio1;
    private Button audio2;
    private Button audio3;
    private Button setKnop;
    private EditText invoerTijd;
    private int input;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);

        // grafische elementen ophalen uit de view
        audio1 = v.findViewById(R.id.audio1);
        audio2 = v.findViewById(R.id.audio2);
        audio3 = v.findViewById(R.id.audio3);
        setKnop = v.findViewById(R.id.setKnop);
        invoerTijd = v.findViewById(R.id.inputTijd);

        // listeners instellen
        audio1.setOnClickListener(this);
        audio2.setOnClickListener(this);
        audio3.setOnClickListener(this);
        setKnop.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audio1:

                break;
            case R.id.audio2:

                break;
            case R.id.audio3:

                break;
            case R.id.setKnop:

                // error handling
                try{
                    input = Integer.parseInt(invoerTijd.getText().toString());
                }
                catch (NumberFormatException e){

                    Snackbar.make(v, "Voer eerst een nummer in", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                if (input >= 0 && input <= 10){

                    Context context = getActivity();
                    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("wachttijd", input);
                    editor.commit();

                }

                break;
        }
    }
}
