package com.example.myapplication.blokjes;

import android.app.Activity;
import android.graphics.Rect;

public class Blokje extends Activity {

    int width, height;
    int lbX, lbY, rbX, rbY, loX, loY, roX, roY;

    public Blokje(int lbX, int lbY, int width, int height) {
        this.width = width;
        this.height = height;
        this.lbX = lbX - width/2;
        this.lbY = lbY - height/2;

        this.rbX = lbX + width;
        this.rbY = lbY;

        this.loX = lbX;
        this.loY = lbY + height;

        this.roX = loX + width;
        this.roY = loY;
    }
    public int getlbX() {
        return lbX;
    }

    public int getlbY() {
        return lbY;
    }


    public void setLbX(int lbX) {
        this.lbX = lbX;
    }

    public Boolean hit(Rect rectball){
        Rect blokje = new Rect(lbX , lbY, roX, roY);
        return blokje.intersect(rectball);
    }



}
