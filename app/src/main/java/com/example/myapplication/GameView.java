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


class   GameView extends View {
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
    Bitmap ball, block1,block2, block3, block4, block5, finish;

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
        block1 = Bitmap.createScaledBitmap(block1, dWidth/20,dHeight/(11/2), false);

        block2 = BitmapFactory.decodeResource(getResources(), R.drawable.block2);
        block2 = Bitmap.createScaledBitmap(block2, dWidth/20, dHeight/(11/2), false);

        block3 = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        block3 = Bitmap.createScaledBitmap(block3, dWidth/20,dHeight/(11/2), false);

        block4 = BitmapFactory.decodeResource(getResources(), R.drawable.block2);
        block4 = Bitmap.createScaledBitmap(block4, dWidth/20, dHeight/(11/2), false);

        block5 = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        block5 = Bitmap.createScaledBitmap(block5, dWidth/20,dHeight/(11/2), false);

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

        canvas.drawBitmap(block1, dWidth/2 - block1.getWidth()/2 , 0, null);
        canvas.drawBitmap(finish, dWidth /10 *9 , dHeight/2 - finish.getHeight()/2  , null);
        canvas.drawBitmap(block2, dWidth/2 - block2.getWidth()/2, block2.getHeight()+ block2.getHeight()/22, null);
        canvas.drawBitmap(block3, dWidth/2 - block2.getWidth()/2, block3.getHeight() * 2 + block2.getHeight()/12 , null);
        canvas.drawBitmap(block4, dWidth/2 - block2.getWidth()/2, block4.getHeight()* 3 +  block4.getHeight()/8, null);
        canvas.drawBitmap(block5, dWidth/2 - block2.getWidth()/2, block5.getHeight()* 4 + block5.getHeight()/1, null);
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
