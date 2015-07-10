package com.HardGame;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.HardGame.thread.GameThread;
import com.HardGame.view.*;

/**
 * Created by 逢双 on 14-2-17.
 */
public class HardGame extends Activity{

    private int status;
    public static DisplayMetrics dm;
    public static HardGame activity;
    private GameThread gameThread;
    private WelcomeView welcomeView;
    public GameView gameView;
    private LoseView loseView;
    private WinView winView;
    public ChooseView chooseView;
    private PowerManager.WakeLock wakeLock;
    public static int life = 0;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == GameStatus.WELCOME_NONE){
                if(welcomeView == null){
                    initWelcomeView();
                }
                else {
                    welcomeView.invalidate();
                }
            }
            else if(msg.what == GameStatus.GAME_PLAYING){
                if(gameView == null){
                    initGameView();
                }
                else {
                    setContentView(gameView);
                    //gameView.invalidate();
                }
            }
            else if(msg.what == GameStatus.GAME_WIN){
                setContentView(winView);
            }
            else if(msg.what == GameStatus.GAME_PAUSE){
                //setStatus(GameStatus.GAME_PLAYING);
            }
            else if(msg.what == GameStatus.GAME_LOSE){
                setContentView(loseView);
            }
            else if(msg.what == GameStatus.CHOOSE){
                setContentView(chooseView);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(welcomeView);
        activity = this;
        wakeLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "HardGame");
        dm = getResources().getDisplayMetrics();
        gameThread = new GameThread(this);
        gameThread.start();
        gameView = new GameView(this);
        chooseView = new ChooseView(this);
        loseView = new LoseView(this);
        winView = new WinView(this);
    }

    private void fullScreen(){
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        welcomeView = new WelcomeView(this);
    }

    private void initWelcomeView(){
        welcomeView = new WelcomeView(this);
        setContentView(welcomeView);
    }

    private void initGameView(){
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameThread.setFlag(false);
        this.finish();
        System.exit(0);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setWelcomeView(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    protected void onPause() {
        wakeLock.release();
        super.onPause();
    }

    @Override
    protected void onResume() {
        wakeLock.acquire();
        super.onResume();
    }
}
