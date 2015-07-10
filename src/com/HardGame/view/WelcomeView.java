package com.HardGame.view;

import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.HardGame.GameStatus;
import com.HardGame.Graphics.DrawToLocation;
import com.HardGame.Graphics.ImageSizeAndLocation;
import com.HardGame.Graphics.ImageUtil;
import com.HardGame.HardGame;
import com.HardGame.R;


/**
 * Created by 逢双 on 14-2-19.
 */
public class WelcomeView extends View{


    private HardGame activity;
    Bitmap bCover;
    Bitmap bLogo;
    Bitmap bNew;
    Bitmap bChoose;

    public WelcomeView(HardGame activity) {
        super(activity);
        this.activity = activity;

        bCover = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.game_background), (float) 240, (float) 240);
        bNew = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.newgame), (float)30, (float)150);
        bChoose = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.chooselevel), (float)30, (float)150);
        bLogo = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.logo), (float)120, (float)200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bCover, 0, 0, null);
        canvas.drawBitmap(bLogo, 20, 10, null);
        canvas.drawBitmap(bNew, 45, 100, null);
        canvas.drawBitmap(bChoose, 45, 150, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getY() > 90 && event.getY() < 140){
            activity.setStatus(GameStatus.GAME_PLAYING);
            activity.gameView.changeLevel();
        }
        else if(event.getY() > 140 && event.getY() < 190){
            activity.setStatus(GameStatus.CHOOSE);
        }
        return super.onTouchEvent(event);
    }
}
