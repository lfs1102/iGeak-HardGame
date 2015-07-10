package com.HardGame.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by 逢双 on 14-2-19.
 */
public class ImageSizeAndLocation {
    Canvas canvas;
    int x;
    int y;
    double xPercent;
    double yPercent;
    int width;
    int height;
    double widthDivideHeight;
    double widthPercent;
    double heightPercent;
    
    public ImageSizeAndLocation(Canvas canvas){
        this.canvas = canvas;
        this.x = 0;
        this.y = 0;
        this.width = canvas.getWidth();
        this.height = canvas.getHeight();
        this.xPercent = 1;
        this.yPercent = 1;
        this.widthDivideHeight = width / height;
        this.widthPercent = 1;
        this.heightPercent = 1;
    }

    public ImageSizeAndLocation(int x, int y, int width,int height, Canvas canvas){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.canvas = canvas;
        this.xPercent = x / canvas.getWidth();
        this.yPercent = y / canvas.getHeight();
        this.widthDivideHeight = width / height;
        this.widthPercent = width / canvas.getWidth();
        this.heightPercent = height / canvas.getHeight();
    }

    public ImageSizeAndLocation(double xPercent, double yPercent,double widthPercent, double heightPercent, Canvas canvas){
        this.canvas = canvas;
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        this.x = (int)(xPercent * canvas.getWidth());
        this.y = (int)(yPercent * canvas.getHeight());
        this.width = (int)(widthPercent * canvas.getWidth());
        this.height = (int)(heightPercent * canvas.getHeight());
        this.widthPercent = width / height;
    }

    public ImageSizeAndLocation(double xPercent, double yPercent, Bitmap bitmap, double widthPercent, Canvas canvas){
        this.canvas = canvas;
        this.xPercent = xPercent;
        this.yPercent = yPercent;
        this.widthPercent = widthPercent;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        this.widthDivideHeight = bitmap.getWidth() / bitmap.getHeight();
        this.heightPercent = height / canvas.getHeight();
        this.x = (int)(xPercent * canvas.getWidth() - bitmap.getWidth() / 2);
        this.y = (int)(yPercent * canvas.getHeight() - bitmap.getHeight() / 2);
    }

    public boolean isTouched(int x, int y){
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }


}
