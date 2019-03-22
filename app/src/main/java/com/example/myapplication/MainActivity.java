package com.example.myapplication;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import tyrantgit.explosionfield.ExplosionField;


public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    ExplosionField explosionField;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        explosionField=ExplosionField.attach2Window(this);
        imageButton=(ImageButton)findViewById(R.id.imageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
                    public void onClick(final View view) {
                explosionField.explode(imageButton);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startGame(view);
                    }
                }, 500);

            }


        });

    }

    public void startGame(View view){
        Intent intent = new Intent(this, StartGame.class);
        startActivity(intent);
        finish();


    }
}
