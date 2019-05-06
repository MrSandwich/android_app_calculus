package com.test.calculus.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.test.calculus.MediaControl;
import com.test.calculus.R;
import java.util.Random;


public class TafelOefFragment extends Fragment implements View.OnClickListener {

    public static final String PREFS_NAME = "GedeeldeGegevens";
    final Handler handler = new Handler();
    MediaControl countdownMusic;
    MediaControl bongMusic;
    private View v;
    private int tafelnummer;
    private int productCijfer = 0;
    private int antwoord;
    private int beantwoordeVragen;
    private boolean isRandom;
    private int vragenCorrect;

    private Button antwoordknop1;
    private Button antwoordknop2;
    private Button antwoordknop3;
    private Button antwoordknop4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_oefeningen, container, false);
        countdownMusic = new MediaControl(getContext(), R.raw.countdown_5, false);
        bongMusic = new MediaControl(getContext(), R.raw.bong, false);

        // knop objecten ophalen
        antwoordknop1 = v.findViewById(R.id.antKnop1);
        antwoordknop1.setOnClickListener(this);
        antwoordknop2 = v.findViewById(R.id.antKnop2);
        antwoordknop2.setOnClickListener(this);
        antwoordknop3 = v.findViewById(R.id.antKnop3);
        antwoordknop3.setOnClickListener(this);
        antwoordknop4 = v.findViewById(R.id.antKnop4);
        antwoordknop4.setOnClickListener(this);

        // tafelnummer ophalen uit sharedpreferences
        haalTafelnummerOp();

        //vanaf onCreate eenmalig de nieuwe som opstarten
        startVolgendeVraag(0);

        return v;
    }
    // muziek correct pauzeren & starten
    @Override
    public void onPause() {
        super.onPause();

        //muziek stoppen
        countdownMusic.stop();
        bongMusic.stop();

        //Einde tonen
        handler.removeCallbacksAndMessages(null);
        onderbreekSpel();
    }

    public void startVolgendeVraag (int beantwoordeVragen){

        //Controleer het aantal beantwoorde vragen
        if (beantwoordeVragen < 9){

            // start de sommenmaker
            sommenMaker();

            // Genereer antwoord knoppen
            stelAntwoordKnoppenIn(tafelnummer, productCijfer);

            countdownMusic.start();

            // toon antwoorden wanneer men geen antwoord geeft binnen 5 seconden
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    toonFeedback();
                }
            }, 5000);
        }
        // Het spel stopt, feliciteer de gebruiker en start het fragment met de score
        else{
            // toon feedback aan de gebruiker
            countdownMusic.stop();
            TextView getoondeSom = v.findViewById(R.id.getoondeSom);
            getoondeSom.setTextSize(40);
            getoondeSom.setText("Super, alle vragen beantwoord!");

            // value passing van de score en de soort oefening
            Context context = getActivity();
            SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("vragenCorrect", vragenCorrect);
            editor.putString("soortOefening", "De tafel van: "+ tafelnummer);
            editor.commit();


            // Wacht 2,5 seconden en toon vervolgens het fragment waar het een visualisatie van de score getoont wordt
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new OverzichtFragment()).commit();
                }
            }, 2500);
        }

    }
    public void haalTafelnummerOp(){

        Context context = getActivity();
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        tafelnummer = settings.getInt("tafelnummer", 0);
        isRandom = settings.getBoolean("switchState", false);
    }

    public void sommenMaker(){

        TextView getoondeSom = v.findViewById(R.id.getoondeSom);

        if (isRandom == true){

            while (true){
                // genereer een getal tussen de 1 en 10
                Random randomgenerator = new Random();
                int randomGetal = randomgenerator.nextInt(10) + 1;

                // controleer of nieuwe som niet hetzelfde is als de vorige
                if (randomGetal != productCijfer){
                    productCijfer = randomGetal;
                    break;
                }
            }
            getoondeSom.setText(""+tafelnummer +" x "+productCijfer);
        }
        else {
            // tel 1 op bij het productCijfer, zodat de som de tafel op volgorde af gaat

            productCijfer++;
            getoondeSom.setText(""+tafelnummer +" x "+productCijfer);

        }
        // bereken het antwoord en maak het globaal
        antwoord = tafelnummer * productCijfer;
    }
    public void stelAntwoordKnoppenIn (int tafelnummer, int productCijfer){

        int foutAnt1;
        int foutAnt2;
        int foutAnt3;

        // genereer drie redelijk logische (foute) antwoorden, die niet hetzelfde zijn als het correcte antwoord of de overige foute antwoorden

        Random randomGenerator = new Random();

        while (true){
            foutAnt1 = (randomGenerator.nextInt(tafelnummer+5) + 1) * (tafelnummer);

            if (foutAnt1 != antwoord){
                break;
            }
        }
        while (true){
            foutAnt2 = (randomGenerator.nextInt(tafelnummer+5) + 1) * (tafelnummer);

            if (foutAnt2 != antwoord && foutAnt2 != foutAnt1){
                break;
            }
        }
        while (true){
            foutAnt3 = (randomGenerator.nextInt(tafelnummer+5) + 1) * (tafelnummer);

            if (foutAnt3 != antwoord && foutAnt3 !=foutAnt1 && foutAnt3 != foutAnt2){
                break;
            }
        }

        // De correcte en foute antwoorden toewijzen aan willekeurige knoppen

        int volgorde = randomGenerator.nextInt(4)+1;

        if (volgorde == 1){

            antwoordknop1.setText(""+antwoord);
            antwoordknop2.setText(""+foutAnt1);
            antwoordknop3.setText(""+foutAnt2);
            antwoordknop4.setText(""+foutAnt3);

        } else if (volgorde == 2) {

            antwoordknop1.setText(""+foutAnt1);
            antwoordknop2.setText(""+antwoord);
            antwoordknop3.setText(""+foutAnt2);
            antwoordknop4.setText(""+foutAnt3);

        }
        else if (volgorde == 3){

            antwoordknop1.setText(""+foutAnt1);
            antwoordknop2.setText(""+foutAnt2);
            antwoordknop3.setText(""+antwoord);
            antwoordknop4.setText(""+foutAnt3);

        }
        else {

            antwoordknop1.setText(""+foutAnt1);
            antwoordknop2.setText(""+foutAnt2);
            antwoordknop3.setText(""+foutAnt3);
            antwoordknop4.setText(""+antwoord);

        }

    }
    public void onClick(View v){
        switch (v.getId()){

            // controleer of de gebruiker de correcte knop gekozen heeft en zo ja, tel een punt op bij de score
            case R.id.antKnop1:
                if (Integer.parseInt(antwoordknop1.getText().toString()) == (antwoord)){
                    vragenCorrect++;
                }
                toonFeedback();
                break;

            case R.id.antKnop2:
                if (Integer.parseInt(antwoordknop2.getText().toString()) == (antwoord)){
                    vragenCorrect++;
                }
                toonFeedback();
                break;

            case R.id.antKnop3:
                if (Integer.parseInt(antwoordknop3.getText().toString()) == (antwoord)){
                    vragenCorrect++;
                }
                toonFeedback();
                break;

            case R.id.antKnop4:
                if (Integer.parseInt(antwoordknop4.getText().toString()) == (antwoord)){
                    vragenCorrect++;
                }
                toonFeedback();
                break;
        }
    }
    public void toonFeedback (){

        //Stop de timer
        handler.removeCallbacksAndMessages(null);

        countdownMusic.reset();
        bongMusic.start();


        // kleur de foute antwoorden rood en de correcte groen

        if (Integer.parseInt(antwoordknop1.getText().toString()) == (antwoord)){

            antwoordknop1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#00b20b")));
            antwoordknop1.setTextColor(Color.parseColor("#000000"));
            antwoordknop2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
        }
        else if (Integer.parseInt(antwoordknop2.getText().toString()) == (antwoord)){

            antwoordknop1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#00b20b")));
            antwoordknop3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
        }
        else if (Integer.parseInt(antwoordknop3.getText().toString()) == (antwoord)){

            antwoordknop1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#00b20b")));
            antwoordknop4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
        }
        else{
            antwoordknop1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#d11f00")));
            antwoordknop4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#00b20b")));
        }


        // reset de knopkleuren en start de volgende vraag
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                antwoordknop1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#D5D6D6")));
                antwoordknop2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#D5D6D6")));
                antwoordknop3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#D5D6D6")));
                antwoordknop4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#D5D6D6")));

                bongMusic.reset();
                startVolgendeVraag(beantwoordeVragen++);
            }
        }, 4000);
    }
    public void onderbreekSpel(){

        TextView getoondeSom = v.findViewById(R.id.getoondeSom);
        getoondeSom.setTextSize(40);
        getoondeSom.setText("Spel onderbroken, keer terug naar het startscherm");

    }
}
