package com.usil.proyectomoviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.usil.proyectomoviles.R;

public class SplashScreen extends AppCompatActivity {
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.lyt_splash_screen);
        asignarReferencia();
        animaciones();
    }

    private void asignarReferencia() {
        imgLogo = findViewById(R.id.imgLogo);
    }

    private void animaciones() {
        Animation animacion1 = AnimationUtils.loadAnimation(this, R.anim.animacion_logo);
        imgLogo.setAnimation(animacion1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                imgLogo, PropertyValuesHolder.ofFloat("scaleX",1.7f), PropertyValuesHolder.ofFloat("scaleY",1.7f)
        );
        objectAnimator.setDuration(3000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}