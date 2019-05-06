package com.test.calculus.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.test.calculus.MediaControl;
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
    private MediaControl mediaplayer;
    private MediaControl mediaplayer2;
    private MediaControl mediaplayer3;

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

        mediaplayer = new MediaControl(getContext(), R.raw.countdown_5, false);
        mediaplayer2 = new MediaControl(getContext(), R.raw.countdown_5_2, false);
        mediaplayer3 = new MediaControl(getContext(), R.raw.countdown_5_3, false);


        return v;
    }

    @Override
    public void onClick(View v) {

        Context context = getActivity();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        switch (v.getId()) {

            case R.id.audio1:

                // check of er geen andere audio speelt, zo ja, stop het
                if (mediaplayer2.getLength() != 0){
                    mediaplayer2.reset();
                }
                else if (mediaplayer3.getLength() != 0){
                    mediaplayer3.reset();
                }

                // check of het gevraagde nummer al speelt, zo ja set stop, anders start
                if (mediaplayer.getLength() != 0){
                    mediaplayer.reset();
                }
                else {
                    mediaplayer.start();

                    // stel de gekozen audio in en stuur het door naar de oefeningfragmenten
                    editor.putInt("audioNummer", R.raw.countdown_5);
                    editor.commit();

                    // toon feedback aan de gebruiker
                    Snackbar.make(v, "Timer 1 ingesteld", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.audio2:

                // check of er geen andere audio speelt, zo ja, stop het
                if (mediaplayer.getLength() != 0){
                    mediaplayer.reset();
                }
                else if (mediaplayer3.getLength() != 0){
                    mediaplayer3.reset();
                }

                // check of het gevraagde nummer al speelt, zo ja set stop, anders start
                if (mediaplayer2.getLength() != 0){
                    mediaplayer2.reset();
                }
                else {
                    mediaplayer2.start();

                    // stel de gekozen audio in en stuur het door naar de oefeningfragmenten

                    editor.putInt("audioNummer", R.raw.countdown_5_2);
                    editor.commit();

                    // toon feedback aan de gebruiker
                    Snackbar.make(v, "Timer 2 ingesteld", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                break;
            case R.id.audio3:

                // check of er geen andere audio speelt, zo ja, stop het
                if (mediaplayer.getLength() != 0){
                    mediaplayer.reset();
                }
                else if (mediaplayer2.getLength() != 0){
                    mediaplayer2.reset();
                }

                // check of het gevraagde nummer al speelt, zo ja set stop, anders start
                if (mediaplayer3.getLength() != 0){
                    mediaplayer3.reset();
                }
                else {
                    mediaplayer3.start();

                    // stel de gekozen audio in en stuur het door naar de oefeningfragmenten

                    editor.putInt("audioNummer", R.raw.countdown_5_3);
                    editor.commit();

                    // toon feedback aan de gebruiker
                    Snackbar.make(v, "Timer 3 ingesteld", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
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

                    // geef de wachttijd door aan de oefeningfragmenten

                    editor.putInt("wachttijd", input*1000);
                    editor.commit();

                    // toon feedback aan de gebruiker
                    Snackbar.make(v, "De wachttijd is: " + input, Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }

                break;
        }
    }
}
