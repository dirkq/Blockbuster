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

import java.util.Random;


class GameView extends View {
    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS = 1;
    Display display;
    Point point;
    int dWidth, dHeight, gap = 20;
//    int blockHeight = 100, blockWidth = 100;
    Bitmap ball;
//    Bitmap block, block1,block2;
    Boolean goingForward = true, goingUp = false;
    int snelheidX = 40;
    int snelheidY = 10;
    int balX, balY;
    boolean shot;


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

//        block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
//        block = Bitmap.createScaledBitmap(block, blockWidth,blockHeight, false);
//        block1 = BitmapFactory.decodeResource(getResources(), R.drawable.block);
//        block1 = Bitmap.createScaledBitmap(block1, blockWidth,blockHeight, false);
//        block2 = BitmapFactory.decodeResource(getResources(), R.drawable.block);
//        block2 = Bitmap.createScaledBitmap(block2, blockWidth,blockHeight, false);

        balX = 50;
        balY = dHeight/2 - ball.getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(balX > dWidth - ball.getWidth()){
            goingForward = false;
        }
        if(balX < 0){
            goingForward = true;
        }

        if(balY > dHeight - ball.getHeight()*2){
            goingUp = true;
        }
        if(balY < 0){
            goingUp = false;
        }


        if(shot == true && goingUp == true) {
            if((balY - snelheidY) < 0){
                balY -= balY - snelheidY;
                goingUp = false;
            }else{
                balY -= snelheidY;
            }
        }
        else if(shot == true && goingUp == false){
            if((balY - snelheidY) > dHeight){
                balY += balY - snelheidY;
                goingUp = true;
            }else{
                balY += snelheidY;
            }
        }

        if(shot == true && goingForward == true) {
            balX += snelheidX;
        }
        else if(shot == true && goingForward == false){
            balX -= snelheidX;
        }


//        canvas.drawBitmap(block2, dWidth/2 - blockWidth/2, dHeight/2 - blockHeight/2 - blockHeight - gap, null);
//        canvas.drawBitmap(block, dWidth/2 - blockWidth/2, dHeight/2 - blockHeight/2, null);
//        canvas.drawBitmap(block1, dWidth/2 - blockWidth/2, dHeight/2 - blockHeight/2 + blockHeight + gap, null);

        canvas.drawBitmap(ball, balX, balY, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            shot = true;
        }
        return true;
    }
}
