package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.blokjes.Blokje;

import java.util.ArrayList;
import java.util.List;


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
    Bitmap ball, block1, finish, cursor, cursor1, cursor2, cursor3;

    List<Blokje> blokjes = new ArrayList<>();
    int hoogte, breedte;
    Rect rectball;
    boolean b;
//  als de bal naar links gaat is going forward false anders true, als de bal naar boven gaat i goingup true,anders false
    Boolean goingForward = true, goingUp = false;

//  waarde die per x aantal miliseconde toegevoegt wil worden
    int snelheidX = 0;
    int snelheidY = 0;
    double standaard = Math.sqrt(800);

//  de coordinaten van de bal
    int balX, balY;

//  de coordinaten van de cursor
    int cursorX, cursorY, cursor1X, cursor1Y, cursor2X, cursor2Y, cursor3X, cursor3Y;

//  of het spel gestart is en de bal weggeschoten is
    boolean touched, fired;


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

        hoogte = dHeight/100*18;
        breedte = dWidth/20;

        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = Bitmap.createScaledBitmap(ball, 100,100, false);
        balX = 50;
        balY = dHeight/2 - ball.getHeight()/2;

        cursor = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        cursor = Bitmap.createScaledBitmap(cursor, 50,50, false);
        cursorX = -20;
        cursorY = -20;

        cursor1 = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        cursor1 = Bitmap.createScaledBitmap(cursor1, 40,40, false);
        cursor1X = -20;
        cursor1Y = -20;

        cursor2 = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        cursor2 = Bitmap.createScaledBitmap(cursor2, 30,30, false);
        cursor2X = -20;
        cursor2Y = -20;

        cursor3 = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        cursor3 = Bitmap.createScaledBitmap(cursor3, 20,20, false);
        cursor3X = -20;
        cursor3Y = -20;

        block1 = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        block1 = Bitmap.createScaledBitmap(block1,breedte, hoogte, false);

        blokjes.add(new Blokje(dWidth/2, dHeight/100*25, breedte ,hoogte));
        blokjes.add(new Blokje(dWidth/2, dHeight/100*50, breedte ,hoogte));
        blokjes.add(new Blokje(dWidth/2, dHeight/100*75, breedte ,hoogte));

//        finish = BitmapFactory.decodeResource(getResources(), R.drawable.block);
//        finish = Bitmap.createScaledBitmap(finish, 200,200, false);
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

        for(int i = 0; i<blokjes.size(); i++){


            b = blokjes.get(i).hit(balX, balY,ball.getWidth(), ball.getHeight());
            if(b){
                blokjes.get(i).remove();
            }
            canvas.drawBitmap(block1, blokjes.get(i).getMinX(), blokjes.get(i).getMaxY(),null);
        }
//        canvas.drawBitmap(finish, dWidth /10 *9 , dHeight/2 - finish.getHeight()/2  , null);
        canvas.drawBitmap(ball, balX, balY, null);
        canvas.drawBitmap(cursor, cursorX, cursorY, null);
        canvas.drawBitmap(cursor1, cursor1X, cursor1Y, null);
        canvas.drawBitmap(cursor2, cursor2X, cursor2Y, null);
        canvas.drawBitmap(cursor3, cursor3X, cursor3Y, null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();
        int intervalx = (50 + ball.getWidth() / 2 - x);
        int intervaly = (dHeight / 2 - y);
        double slope = Math.sqrt(intervalx * intervalx + intervaly * intervaly);
        double vergroting = standaard / slope;
        double vergrootx = vergroting * intervalx;
        double vergrooty = vergroting * intervaly;
        if (fired == false) {
            cursor1X = -(int)vergrootx *65/10 + (ball.getWidth()/2+50 - cursor1.getWidth()/2);
            cursor1Y = -(int)vergrooty *65/10 + (dHeight/2 - cursor1.getHeight()/2);
            cursor2X = -(int)vergrootx *4 + (ball.getWidth()/2+50 - cursor2.getWidth()/2);
            cursor2Y = -(int)vergrooty *4 + (dHeight/2 - cursor2.getHeight()/2);
            cursor3X = -(int)vergrootx *2 + (ball.getWidth()/2+50 - cursor3.getWidth()/2);
            cursor3Y = -(int)vergrooty *2 + (dHeight/2 - cursor3.getHeight()/2);
            cursorX = -(int)vergrootx *95/10 + (ball.getWidth()/2+50 - cursor.getWidth()/2);
            cursorY = -(int)vergrooty *95/10 + (dHeight/2 - cursor.getHeight()/2);
        }
        if(action == MotionEvent.ACTION_DOWN){
        }
        if (action == MotionEvent.ACTION_UP){
            if (fired == false) {
                snelheidX = -(int)vergrootx;
                snelheidY = -(int)vergrooty;
                if (snelheidY < 0){
                    snelheidY *= -1;
                    goingUp = true;
                }
                if (snelheidX < 0) {
                    snelheidX *= -1;
                    goingForward = false;
                }
                cursorX = -80;
                cursor1X = -80;
                cursor2X = -80;
                cursor3X = -80;
            }
            fired = true;
            touched = true;
        }
        return true;
    }
}
