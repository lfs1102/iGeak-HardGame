package com.HardGame.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by 逢双 on 14-2-19.
 */
public class DrawToLocation {
    public static void draw(Bitmap bitmap, ImageSizeAndLocation isl){
        Bitmap result = ImageUtil.zoomBitmap(bitmap, (float)isl.height, (float)isl.width);
        isl.canvas.drawBitmap(result, isl.x, isl.y, null);
     //   System.out.println(isl.yPercent + " " + isl.y + " " + isl.canvas.getHeight());
    }
}
