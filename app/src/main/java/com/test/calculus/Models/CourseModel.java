package com.test.calculus.Models;

/**
 * Created by mjboere on 28-11-2017.
 */
public class CourseModel {

    private String merk;
    private String model;
    private String kleur;
    private String bouwjaar;

    public CourseModel(String merk, String model, String kleur, String bouwjaar) {
        this.merk = merk;
        this.model = model;
        this.kleur = kleur;
        this.bouwjaar = bouwjaar;
    }

    public String getMerk(){
        return merk;
    }

    public String getModel(){
        return model;
    }
    public String getKleur(){
        return kleur;
    }
    public String getBouwjaar(){
        return bouwjaar;
    }

    // ADD GETTERS AND SETTERS - ONLY IF NEEDED !!}
}
