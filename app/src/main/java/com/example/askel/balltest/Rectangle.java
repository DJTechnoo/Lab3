package com.example.askel.balltest;
import android.graphics.Paint;
/**
 * Created by askel on 06/03/2018.
 */

public class Rectangle {
    public int x;
    public int y;
    public int width;
    public int height;
    public Paint colour;

    public Rectangle(int x, int y, int wd, int ht, Paint col){
        this.x = x;
        this.y = y;
        this.width = wd;
        this.height = ht;
        this.colour = col;
    }


}
