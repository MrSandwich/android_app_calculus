package com.test.calculus;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

public class MediaControl extends AppCompatActivity {


    private boolean isLooped;
    private MediaPlayer music;
    private int length;

    public MediaControl (Context context, int bestand, boolean isLooped){
        this.isLooped = isLooped;

        music = MediaPlayer.create(context, bestand);
    }

    public void start(){

        if (isLooped == true){
            music.start();
            music.setLooping(true);
        }
        else{
            music.start();
        }
    }
    public void stop(){
        music.stop();
    }
    public void reset(){
        music.pause();
        music.seekTo(0);
    }
    public void pauzeer(){
        music.pause();
        length = music.getCurrentPosition();
    }
    public void herstart(){
        music.seekTo(length);
        music.start();
    }
    public int getLength(){
     return music.getCurrentPosition();
    }
}
