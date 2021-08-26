package com.example.pongapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btn_beginner, btn_pro, btn_practice_easy, btn_practice_hard, btn_cm_easy, btn_cm_hard;
    TextView highScoreEP, highScoreHP, highScoreEC, highScoreHC;
    public static String order,mode;
    LinearLayout l1,l2_1 ,l2_2;
    //int example;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_beginner = findViewById(R.id.btn_practice);
        btn_pro = findViewById(R.id.btn_cm);
        l1 = findViewById(R.id.l1);
        l2_1 = findViewById(R.id.l2_1);
        l2_2 = findViewById(R.id.l2_2);
        btn_practice_easy = findViewById(R.id.btn_practice_easy);
        btn_practice_hard = findViewById(R.id.btn_practice_hard);
        btn_cm_easy = findViewById((R.id.btn_cm_easy));
        btn_cm_hard = findViewById(R.id.btn_cm_hard);
        highScoreEC = findViewById(R.id.highScoreEC);
        highScoreHC = findViewById(R.id.highScoreHC);
        highScoreEP = findViewById(R.id.highScoreEP);
        highScoreHP = findViewById(R.id.highScoreHP);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int highEP = sharedPreferences.getInt("highScoreEP",0);
        int highHP = sharedPreferences.getInt("highScoreHP",0);
        int highHC = sharedPreferences.getInt("highScoreHC",0);
        int highEC = sharedPreferences.getInt("highScoreEC",0);
        highScoreHP.setText("HIGHSCORE: " + highHP);
        highScoreHC.setText("HIGHSCORE: " + highHC);
        highScoreEP.setText("HIGHSCORE: " + highEP);
        highScoreEC.setText("HIGHSCORE: " + highEC);

        btn_beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.INVISIBLE);
                l2_1.setVisibility(View.VISIBLE);
            }
        });

        btn_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setVisibility(View.INVISIBLE);
                l2_2.setVisibility(View.VISIBLE);
            }
        });
        btn_practice_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
                order = "Easy";
                mode = "Practice";
            }
        });
        btn_practice_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
                order = "Hard";
                mode = "Practice";
            }
        });
        btn_cm_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
                order = "Easy";
                mode = "CM";
            }
        });
        btn_cm_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
                order = "Hard";
                mode = "CM";
            }
        });
    }

    public void openGame(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    /*private static int randBetween2(int start, int end) {
        return (-1) + 2*((int) Math.round(Math.random() * (end - start)));

    }*/
    @Override
    public void onBackPressed(){
        if (l2_1.getVisibility() == View.VISIBLE || l2_2.getVisibility() == View.VISIBLE){
            l2_2.setVisibility(View.INVISIBLE);
            l2_1.setVisibility(View.INVISIBLE);
            l1.setVisibility(View.VISIBLE);
        } else {
            finishAffinity();
        }
    }

}