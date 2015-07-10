package com.HardGame.thread;

import com.HardGame.GameStatus;
import com.HardGame.HardGame;
import android.os.Handler;
import com.HardGame.Map;

/**
 * Created by 逢双 on 14-2-20.
 */
public class GameThread extends Thread{

    private HardGame hardGame;
    private Handler handler;
    private boolean flag = true;
    private int count;
    public static final int span = 20;

    public GameThread(HardGame hardGame){
        this.hardGame = hardGame;
        handler = hardGame.handler;
    }

    @Override
    public void run() {
        try{
            while (flag){
                if(hardGame.getStatus() == GameStatus.WELCOME_NONE){
                    handler.sendEmptyMessage(GameStatus.WELCOME_NONE);
                }
                else if(hardGame.getStatus() == GameStatus.WELCOME_START){
                    handler.sendEmptyMessage(GameStatus.GAME_PLAYING);
                    hardGame.setWelcomeView(null);
                }
                else if(hardGame.getStatus() == GameStatus.GAME_PLAYING){
                    handler.sendEmptyMessage(GameStatus.GAME_PLAYING);
                    hardGame.gameView.postInvalidate();
                }
                else if(hardGame.getStatus() == GameStatus.GAME_LOSE){
                    handler.sendEmptyMessage(GameStatus.GAME_LOSE);
                    if(count == 0)HardGame.life--;
                    count++;
                    if(count > 50){
                        hardGame.gameView.changeLevel();
                        hardGame.setStatus(GameStatus.GAME_PLAYING);
                        count = 0;
                    }
                }
                else if(hardGame.getStatus() == GameStatus.GAME_PAUSE){
                    handler.sendEmptyMessage(GameStatus.GAME_PAUSE);
                }
                else if(hardGame.getStatus() == GameStatus.GAME_WIN){
                    handler.sendEmptyMessage(GameStatus.GAME_WIN);
                    if(count == 0)Map.level++;
                    count++;
                    if(count > 50){
                        hardGame.gameView.changeLevel();
                        hardGame.setStatus(GameStatus.GAME_PLAYING);
                        count = 0;
                    }
                }
                else if(hardGame.getStatus() == GameStatus.CHOOSE){
                    handler.sendEmptyMessage(GameStatus.CHOOSE);
                }
                Thread.sleep(span);
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
            System.out.println("Error: GameThread");
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
