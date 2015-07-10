package com.HardGame.view;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import com.HardGame.Graphics.ImageUtil;
import com.HardGame.HardGame;
import com.HardGame.R;

/**
 * Created by 逢双 on 14-3-1.
 */
public class LoseView extends View{

    Bitmap bPlayer;
    Paint paint;

    public LoseView(Context context) {
        super(context);
        bPlayer = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player), (float)20, (float)20);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(22);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bPlayer, 100, 100, null);
        canvas.drawText("× " + HardGame.life, 130, 119, paint);
    }
}
