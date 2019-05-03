package com.test.calculus.Models;

/**
 * Created by mjboere on 28-11-2017.
 */
public class scoreModel {

    private String naam;
    private String soortOef;
    private String score;


    public scoreModel(String naam, String soortOef, String score) {
        this.naam = naam;
        this.soortOef = soortOef;
        this.score = score;
    }

    public String getNaam(){
        return naam;
    }
    public String getSoortOef(){ return soortOef; }
    public String getScore(){ return score; }


    // ADD GETTERS AND SETTERS - ONLY IF NEEDED !!}
}
