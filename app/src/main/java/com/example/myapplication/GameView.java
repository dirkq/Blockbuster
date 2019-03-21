package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;


class GameView extends View {
//  voor het runnen van de applicatie:
    Handler handler;
    Runnable runnable;

//  voor het updaten van het scherm in miliseconde:
    final int UPDATE_MILLIS = 1;

//  de waarde van het display
    Display display;
    Point point;

//  heigth en width van het display
    int dWidth, dHeight;

//  bal
    Bitmap ball, block1, finish;

//  als de bal naar links gaat is going forward false anders true, als de bal naar boven gaat i goingup true,anders false
    Boolean goingForward = true, goingUp = false;

//  waarde die per x aantal miliseconde toegevoegt wil worden
    int snelheidX = 20;
    int snelheidY = 20;

//  de coordienaten van de bal
    int balX, balY;

//  of het spel gestart is en de bal weggeschoten is
    boolean touched;


    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = Bitmap.createScaledBitmap(ball, 100,100, false);
        balX = 50;
        balY = dHeight/2 - ball.getHeight()/2;

        block1 = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        block1 = Bitmap.createScaledBitmap(block1, 300,300, false);

        finish = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        finish = Bitmap.createScaledBitmap(finish, 200,200, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(touched) {
//          checken of de bal rechts uit het scherm is:
            int minX = 0;
            int minY = 0;
            int maxX = dWidth - ball.getWidth();
            int maxY = dHeight - ball.getHeight()*2;

//          bal gaat naar link
            if ((balX - snelheidX) < minX) {
                if ((balX - snelheidX) < minX) {
                    balX = minX;
                }
                goingForward = true;
            }
//          bal gaat naar rechts
            if ((balX + snelheidX) >= maxX) {
                if ((balX + snelheidX) > maxX) {
                    balX = maxX;
                }
                goingForward = false;
            }

//          bal gaat naar boven
            if ((balY - snelheidY) < minY) {
                if ((balY - snelheidY) < minY) {
                    balY = minY;
                }
                goingUp = false;
            }
//          bal gaat naar onder
            if ((balY + snelheidY) >= maxY) {
                if ((balY + snelheidY) > maxY) {
                    balY = maxY;
                }
                goingUp = true;
            }
            if (goingForward == true) {
                balX += snelheidX;
            }
            else if (goingForward == false) {
                balX -= snelheidX;
            }
            if (goingUp == false) {
                balY += snelheidY;
            }
            else if (goingUp == true) {
                balY -= snelheidY;
            }
        }

        canvas.drawBitmap(block1, dWidth/2 - block1.getWidth()/2, dHeight/2 - block1.getHeight()/2, null);
        canvas.drawBitmap(finish, dWidth /10 *9 , dHeight/2 - finish.getHeight()/2  , null);
        canvas.drawBitmap(ball, balX, balY, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            touched = true;
        }
        return true;
    }
}
