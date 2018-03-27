package com.example.askel.balltest;
import android.graphics.Paint;

/**
 * Created by askel on 06/03/2018.
 */

public class Ball {
    public int x;
    public int y;
    public int xv;
    public int yv;
    public int radius;
    public Paint colour;


    //  Instantiate the ball properties
    public Ball(int x, int y, int rad, Paint col){
        this.x = x;
        this.y = y;
        this.xv = 0;
        this.yv = 0;
        this.radius = rad;
        this.colour = col;
    }

}
