package com.HardGame.view;

import android.graphics.*;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import com.HardGame.Element.Barrier;
import com.HardGame.Element.Player;
import com.HardGame.Element.WinPoint;
import com.HardGame.Element.lose.Lose;
import com.HardGame.GameStatus;
import com.HardGame.Graphics.DrawToLocation;
import com.HardGame.Graphics.ImageSizeAndLocation;
import com.HardGame.Graphics.ImageUtil;
import com.HardGame.HardGame;
import com.HardGame.Map;
import com.HardGame.R;

/**
 * Created by 逢双 on 14-2-20.
 */
public class GameView extends View{

    private Bitmap bPause;
    private Bitmap bGround;
    private Bitmap bPlayer;
    private Bitmap bBarrier;
    private Bitmap bWinPoint;
    private Bitmap bSavePoint;
    private Bitmap bLose;
    private Bitmap bRestart;
    private Bitmap bChoose;
    private ImageSizeAndLocation iPlayer;
    private Player player;
    private Paint paint = new Paint();
    public Map map;
    private HardGame activity;
    private Bitmap background = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888);

    private int ELEMENT_WIDTH = 12;
    private int ELEMENT_HEIGHT = 12;

    public GameView(HardGame activity) {
        super(activity);
        this.activity = activity;
        map = new Map();
        initImage();
        paint.setColor(Color.GRAY);
        paint.setTextSize(17);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
        drawPlayer(canvas);
        drawLose(canvas);
        win();
        dead();
    }
    private void loadBackground(){
        background = Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        drawBackground(canvas);
        drawHeadLine(canvas);
        drawMap(canvas);
        if(Map.level == 1){
            paint.setTextSize(20);
            paint.setColor(Color.DKGRAY);
            canvas.drawText("重力感应控制移动~", 30, 65, paint);
            paint.setColor(Color.GRAY);
            paint.setTextSize(17);
        }
    }

    private void win(){
        for (int i = 0; i < map.getWinPoint().size(); i++) {
            WinPoint winPoint = map.getWinPoint().get(i);
            if(getDistance(winPoint.getX() * 12 + 6, winPoint.getY() * 12 + 30) < 10 ){
                activity.setStatus(GameStatus.GAME_WIN);
            }
        }
    }

    private void dead(){
        for (int i = 0; i < map.getLoses().size(); i++) {
            Lose lose = map.getLoses().get(i);
            if(getDistance(lose.getX() + 6, lose.getY() + 6) < 7){
                activity.setStatus(GameStatus.GAME_LOSE);
            }
        }
    }

    private double getDistance(int x, int y){
        return Math.pow((x - player.getX() - 5) * (x - player.getX() - 5) + (y - player.getY() - 5) * (y - player.getY() - 5) ,0.5);
    }

    private void drawBackground(Canvas canvas){
        DrawToLocation.draw(bGround, new ImageSizeAndLocation(canvas));
    }

    private void drawHeadLine(Canvas canvas){
        //canvas.drawBitmap(bTitle, 0, 0, null);
        //canvas.drawBitmap(bPause, 108, 0, null);
        //canvas.drawBitmap(bChoose, 0, 0, null);
        //canvas.drawBitmap(bRestart, 216, 0, null);
        canvas.drawText("选关", 205, 18, paint);
    }

    private void drawMap(Canvas canvas){

        for (int i = 0; i < map.getBarriers().size(); i++) {
            canvas.drawBitmap(bBarrier,
                    (float)(ELEMENT_WIDTH * map.getBarriers().get(i).getX()),
                    (float)(ELEMENT_HEIGHT * map.getBarriers().get(i).getY() + 24), null);
        }

        for (int i = 0; i < map.getWinPoint().size(); i++) {
            canvas.drawBitmap(bWinPoint,
                (float)(ELEMENT_WIDTH * map.getWinPoint().get(i).getX()),
                (float)(ELEMENT_HEIGHT * map.getWinPoint().get(i).getY() + 24), null);
        }

        for (int i = 0; i < map.getSavePoints().size(); i++) {
            canvas.drawBitmap(bSavePoint,
                    (float)(ELEMENT_WIDTH * map.getSavePoints().get(i).getX()),
                    (float)(ELEMENT_HEIGHT * map.getSavePoints().get(i).getY() + 24), null);
        }
    }

    private void drawPlayer(Canvas canvas){
        player = map.getPlayer();
        player.move();
        canvas.drawBitmap(bPlayer, (float)player.getX(), (float)player.getY(), null);
    }

    private void drawLose(Canvas canvas){
        long now = System.currentTimeMillis();
        for (int i = 0; i < map.getLoses().size(); i++) {
            map.getLoses().get(i).move(now);
            canvas.drawBitmap(bLose, map.getLoses().get(i).getX(), map.getLoses().get(i).getY(), null);
        }

    }

    public void changeLevel(){
        switch (Map.level){
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

            default: map.initLevel_1();Map.level = 1;
        }
        loadBackground();
    }

    private void initImage(){
        bPause = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pause), (float) 24, (float) 24);
        bGround = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.game_background), (float)240, (float)240);
        bPlayer = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.player), (float)7, (float)7);
        bBarrier = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barrier), (float)12, (float)12);
        bWinPoint = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.win), (float)12, (float)12);
        bSavePoint = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.save), (float)12, (float)12);
        bLose = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lose), (float)12, (float)12);
        bRestart = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.restart), (float)24, (float)24);
        bChoose = ImageUtil.zoomBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.choose), (float)24, (float)24);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getX() >= 200 && event.getY() <= 30){
            activity.chooseView.level = Map.level;
            activity.setStatus(GameStatus.CHOOSE);
        }
        return super.onTouchEvent(event);
    }
}