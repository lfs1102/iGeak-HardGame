package com.HardGame.Element;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import com.HardGame.Map;
import com.HardGame.thread.GameThread;
import com.HardGame.HardGame;
/**
 * Created by 逢双 on 14-2-20.
 */
public class Player {

    private double x;
    private double y;
    private double centerX;
    private double centerY;
    private double speed = 1;
    private SensorManager sensorManager;
    private Sensor accelerateSensor;
    private float[] values = new float[3];
    private boolean[][] booleanMap = new boolean[240][216];
    private Map map;

    public static int PLAYER_WIDTH = 7;
    public static int PLAYER_HEIGHT = 7;

    public Player(int x, int y, Map map){
        this.x = x;
        this.y = y;
        this.map = map;
        initBoolean(map.getMap());
        initSensor();
    }

    private void initBoolean(int[][] map){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] == 1){
                    for (int k = 0; k < 12; k++) {
                        for (int l = 0; l < 12; l++) {
                            booleanMap[j * 12 + l][i * 12 + k] = true;
                        }
                    }
                }
            }
        }
    }

    private void initSensor(){
        sensorManager = (SensorManager)HardGame.activity.getSystemService(Context.SENSOR_SERVICE);
        accelerateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, accelerateSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void move(){
        if(moveRate() > 1){
            speed = moveRate() * 1;
            double dx = (speed * values[0] / (Math.abs(values[0]) + Math.abs(values[1])));
            double dy = (speed * values[1] / (Math.abs(values[0]) + Math.abs(values[1])));
            for (double i = dx; Math.abs(i) > 0.1; i -= 0.1 * dx/Math.abs(dx)) {
                if(canMoveTo((int)(x - i), (int)y)){
                    x -= i;
                    break;
                }
            }
            for (double i = dy; Math.abs(i) > 0.1; i -= 0.1 * dy/Math.abs(dy)) {
                if(canMoveTo((int)x, (int)(y + i))){
                    y += i;
                    break;
                }
            }
        }
        if(x < 12) x = 12;
        if(x > HardGame.dm.widthPixels - PLAYER_WIDTH - 12) x = HardGame.dm.widthPixels - PLAYER_WIDTH - 12;

        if(y < 36) y = 36;
        if(y > HardGame.dm.heightPixels - PLAYER_HEIGHT - 12) y = HardGame.dm.heightPixels - PLAYER_HEIGHT - 12;

        centerX = x + PLAYER_WIDTH / 2;
        centerY = y + PLAYER_HEIGHT / 2;
    }

    private boolean canMoveTo(int x, int y){
        if(booleanMap[x][y - 24]
                || booleanMap[x + PLAYER_WIDTH][y - 24]
                || booleanMap[x][y - 24 + PLAYER_HEIGHT]
                || booleanMap[x + PLAYER_WIDTH][y - 24 + PLAYER_HEIGHT]) return false;
        else return true;
    }

    private double moveRate(){
        return Math.pow(values[0] * values[0] + values[1] * values[1], 0.25);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            values = event.values;
            System.out.println(values[0] + "---" + values[1] + "---" + values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
/*


        if(booleanMap[xNow][yPre - 24]){
            for (int i = 0; i < 12; i++) {
                x++;
                if(!booleanMap[x][yPre - 24])break;
            }
        }
        if(booleanMap[xNow][yNow - 24]){
            for (int i = 0; i < 12; i++) {
                x++;
                if(!booleanMap[x][yNow - 24])break;
            }
        }
        if(booleanMap[xNow][yPre + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                x++;
                if(!booleanMap[x][yPre + PLAYER_HEIGHT - 24])break;
            }
        }
        if(booleanMap[xNow][yNow + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                x++;
                if(!booleanMap[x][yNow + PLAYER_HEIGHT - 24])break;
            }
        }

        if(booleanMap[xPre][yNow - 24]){
            for (int i = 0; i < 12; i++) {
                y++;
                if(!booleanMap[xPre][y - 24])break;
            }
        }
        if(booleanMap[xNow][yNow - 24]){
            for (int i = 0; i < 12; i++) {
                y++;
                if(!booleanMap[xNow][y - 24])break;
            }
        }
        if(booleanMap[xPre + PLAYER_WIDTH][yNow - 24]){
            for (int i = 0; i < 12; i++) {
                y++;
                if(!booleanMap[xPre + PLAYER_WIDTH][y - 24])break;
            }
        }
        if(booleanMap[xNow + PLAYER_WIDTH][yNow - 24]){
            for (int i = 0; i < 12; i++) {
                y++;
                if(!booleanMap[xNow + PLAYER_WIDTH][y - 24])break;
            }
        }

        if(booleanMap[xNow + PLAYER_WIDTH][yPre - 24]){
            for (int i = 0; i < 12; i++) {
                x--;
                if(!booleanMap[x + PLAYER_WIDTH][yPre - 24])break;
            }
        }
        if(booleanMap[xNow + PLAYER_WIDTH][yNow - 24]){
            for (int i = 0; i < 12; i++) {
                x--;
                if(!booleanMap[x + PLAYER_WIDTH][yNow - 24])break;
            }
        }
        if(booleanMap[xNow + PLAYER_WIDTH][yPre + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                x--;
                if(!booleanMap[x + PLAYER_WIDTH][yPre + PLAYER_HEIGHT - 24])break;
            }
        }
        if(booleanMap[xNow + PLAYER_WIDTH][yNow + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                x--;
                if(!booleanMap[x + PLAYER_WIDTH][yNow + PLAYER_HEIGHT - 24])break;
            }
        }

        if(booleanMap[xPre][yNow + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                y--;
                if(!booleanMap[xPre][y + PLAYER_HEIGHT - 24])break;
            }
        }
        if(booleanMap[xNow][yNow + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                y--;
                if(!booleanMap[xNow][y + PLAYER_HEIGHT - 24])break;
            }
        }
        if(booleanMap[xPre + PLAYER_WIDTH][yNow + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                y--;
                if(!booleanMap[xPre + PLAYER_WIDTH][y + PLAYER_HEIGHT - 24])break;
            }
        }
        if(booleanMap[xNow + PLAYER_WIDTH][yNow + PLAYER_HEIGHT - 24]){
            for (int i = 0; i < 12; i++) {
                y--;
                if(!booleanMap[xNow + PLAYER_WIDTH][y + PLAYER_HEIGHT - 24])break;
            }
        }*/

        /*int xLeft = Math.min(x0 / 12,x / 12);
        int xRight = Math.max(((x + 10) % 12) >= 10 ? ((x + 10) / 12) : ((x + 10) / 12) + 1,
                ((x0 + 10) % 12) >= 10 ? ((x0 + 10) / 12) : ((x0 + 10) / 12) + 1);
        int yTop = Math.min(y0 / 12,y / 12) - 2;
        int yBottom = Math.max(((y + 10) % 12) >= 10 ? ((y + 10) / 12) : ((y + 10) / 12) + 1,
                ((y0 + 10) % 12) >= 10 ? ((y0 + 10) / 12) : ((y0 + 10) / 12) + 1) - 2;

        if(xLeft != xRight && yTop != yBottom){

        }
        else if(xLeft == xRight && yTop != yBottom){
            if(map.getMap()[yTop][xLeft] == 1){
                y = yTop * 12 + 24;
            }
            else if(map.getMap()[yBottom][xLeft] == 1){
                y = yBottom * 12 + 14;
            }
        }
        else if(xLeft != xRight && yTop == yBottom){
            if(map.getMap()[yTop][xLeft] == 1){
                x = xRight * 12;
            }
            else if(map.getMap()[yBottom][xRight] == 1){
                x = xLeft * 12 + 2;
            }
        }*/
