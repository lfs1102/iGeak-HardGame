package com.HardGame.view;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import com.HardGame.Graphics.DrawToLocation;
import com.HardGame.Graphics.ImageSizeAndLocation;
import com.HardGame.Graphics.ImageUtil;
import com.HardGame.Map;
import com.HardGame.R;

/**
 * Created by 逢双 on 14-3-1.
 */
public class WinView extends View{

    Bitmap bGround;
    Paint paint;

    public WinView(Context context) {
        super(context);
        bGround = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.game_background), (float) 240, (float) 240);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //DrawToLocation.draw(bGround, new ImageSizeAndLocation(canvas));
        canvas.drawText("LEVEL " + Map.level, 70, 130, paint);
    }
}
