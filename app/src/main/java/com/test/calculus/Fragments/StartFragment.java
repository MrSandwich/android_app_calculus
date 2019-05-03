package com.test.calculus.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import com.test.calculus.MediaControl;
import com.test.calculus.R;

public class StartFragment extends Fragment implements View.OnClickListener {

    MediaControl lobbymusic;

    public static final String PREFS_NAME = "GedeeldeGegevens";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_startscherm, container, false);

        //Start muziek
        lobbymusic = new MediaControl(getContext(), R.raw.lobby_music, true);
        lobbymusic.start();

        // Listeners opzetten voor de knoppen

        Button randomOefening = v.findViewById(R.id.randomOefening);
        Button tafelsknop = v.findViewById(R.id.tafelOefening);

        randomOefening.setOnClickListener(this);
        tafelsknop.setOnClickListener(this);

        return v;
    }
    @Override
    public void onPause() {
        super.onPause();

        //pauzeer de muziek en sla positie op
        lobbymusic.pauzeer();
    }

    @Override
    public void onResume() {
        super.onResume();

        //hervat de muziek
        lobbymusic.herstart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tafelOefening:

                // invoervelden uit de view ophalen
                TextView invoer = getView().findViewById(R.id.invoerveld);

                Switch randomToggle = getView().findViewById(R.id.randomToggle);

                //controleren of gebruiker een tafelnummer heeft opgegeven
                int tafelnummer = 0;
                try {
                    tafelnummer = Integer.parseInt(invoer.getText().toString());
                }
                catch (NumberFormatException e){
                    Snackbar.make(v, "Voer een geldig tafelnummer in", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (tafelnummer > 0 && tafelnummer < 11){

                    //Tafelnummer en de status van de switch opslaan, zodat andere activities & fragments dit kunnen uitlezen
                    Context context = getActivity();
                    SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("tafelnummer", tafelnummer);
                    editor.putBoolean("switchState", randomToggle.isChecked());
                    editor.commit();

                    //Transitie starten naar het tafel oefenen
                    lobbymusic.stop();
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new TafelOefFragment()).commit();

                }
                else if (tafelnummer > 10){
                    Snackbar.make(v, "Selecteer een tafelnummer tussen de 1 en 10", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;

            case R.id.randomOefening:

                getFragmentManager().beginTransaction().replace(R.id.frame_container, new RandomOefFragment()).commit();
                break;

            default:
                break;
        }

    }
}
