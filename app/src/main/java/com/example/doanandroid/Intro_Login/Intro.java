package com.example.doanandroid.Intro_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.doanandroid.R;

public class Intro extends AppCompatActivity {
    //set thoi gian chuyen
    private static int SLASH_TIMER = 3000;
    ImageView logo;


    Animation sideanim,bottomanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        logo = findViewById(R.id.logo);


        //khai bao animation
        sideanim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomanim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set anim
        logo.setAnimation(bottomanim);


        //chuyen man hinh
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this, OnBoardingActivity.class);
                startActivity(intent);
            }
        },SLASH_TIMER);
    }
}