package com.HardGame.view;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import com.HardGame.GameStatus;
import com.HardGame.Graphics.ImageUtil;
import com.HardGame.HardGame;
import com.HardGame.Map;
import com.HardGame.R;

/**
 * Created by 逢双 on 14-3-3.
 */
public class ChooseView extends View{
    
    public int level;
    private HardGame activity;
    private Map map;
    private Paint paint;
    private Bitmap background;
    private Bitmap left;
    private Bitmap right;
    private Bitmap bBarrier;
    private Bitmap bWinPoint;
    private Bitmap bSavePoint;
    private Bitmap bRight;
    private Bitmap bLeft;
    private Bitmap bLose;
    

    public ChooseView(HardGame activity) {
        super(activity);
        initBitmap();
        this.activity = activity;
        this.level = Map.level;
        this.map = activity.gameView.map;
        changeLevel();
        paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);

        for (int i = 0; i < map.getBarriers().size(); i++) {
            canvas.drawBitmap(bBarrier,
                    (float)(9 * map.getBarriers().get(i).getX() + 30),
                    (float)(9 * map.getBarriers().get(i).getY() + 60), null);
        }

        for (int i = 0; i < map.getWinPoint().size(); i++) {
            canvas.drawBitmap(bWinPoint,
                    (float)(9 * map.getWinPoint().get(i).getX() + 30),
                    (float)(9 * map.getWinPoint().get(i).getY() + 60), null);
        }

        for (int i = 0; i < map.getSavePoints().size(); i++) {
            canvas.drawBitmap(bSavePoint,
                    (float)(9 * map.getSavePoints().get(i).getX() + 30),
                    (float)(9 * map.getSavePoints().get(i).getY() + 60), null);
        }
        long now = System.currentTimeMillis();
        for (int i = 0; i < map.getLoses().size(); i++) {
            map.getLoses().get(i).move(now);
            canvas.drawBitmap(bLose,
                    (float)(9 * map.getLoses().get(i).getX() + 30),
                    (float)(9 * map.getLoses().get(i).getY() + 60), null);
        }

        /*for (int i = 0; i < map.getLoses().size(); i++) {
            canvas.drawBitmap(bLose,
                    (float)(9 * map.getLoses().get(i).getX() + 30),
                    (float)(9 * map.getLoses().get(i).getY() + 60), null);
        }
*/
        canvas.drawBitmap(bLeft, 0, 100, null);
        canvas.drawBitmap(bRight, 217, 100, null);

        canvas.drawText("第" + level + "关", 75, 50, paint);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX() < 30){
            if(level == 1) level = Map.TOTAL_LEVELS;
            else if(level > 1) level--;
            changeLevel();
        }
        else if(event.getX() > 210){
            if(level == Map.TOTAL_LEVELS) level = 1;
            else if(level < Map.TOTAL_LEVELS) level++;
            changeLevel();
        }
        else {
            Map.level = level;
            activity.gameView.changeLevel();
            activity.setStatus(GameStatus.GAME_PLAYING);
        }
        return super.onTouchEvent(event);
    }
    
    public void changeLevel(){
        switch (level){
            case 1 : map.initLevel_1();break;
            case 2 : map.initLevel_2();break;
            case 3 : map.initLevel_3();break;
            case 4 : map.initLevel_4();break;
            case 5 : map.initLevel_5();break;
            case 6 : map.initLevel_6();break;
            case 7 : map.initLevel_7();break;
            case 8 : map.initLevel_8();break;
            case 9 : map.initLevel_9();break;
            case 10 : map.initLevel_10();break;
            case 11 : map.initLevel_11();break;
            case 12 : map.initLevel_12();break;
            case 13 : map.initLevel_13();break;
            case 14 : map.initLevel_14();break;
            case 15 : map.initLevel_15();break;
            case 16 : map.initLevel_16();break;
        }
    }
    
    private void initBitmap(){
        background = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.game_background), (float)240, (float)240);
        bBarrier = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barrier), (float) 9, (float) 9);
        bWinPoint = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.win), (float)9, (float)9);
        bSavePoint = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.save), (float)9, (float)9);
        bRight = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        bLeft = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        bLose = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lose), (float)12, (float)12);
    }
}
