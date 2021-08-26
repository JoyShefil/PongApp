package com.example.pongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
//import android.media.MediaPlayer;
import android.os.Bundle;
//import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private CustomView gCustomView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gCustomView = findViewById(R.id.CustomView);
    }
    @Override
    public void onBackPressed(){
        if (gCustomView.bTimer == null && !gCustomView.toStart){
            startActivity(new Intent(MainActivity2.this, MainActivity.class));
        } else{
            gCustomView.bTimer.cancel();
            gCustomView.bTimer = null;
            gCustomView.lose.start();
        }
    }
}