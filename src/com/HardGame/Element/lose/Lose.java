package com.HardGame.Element.lose;

/**
 * Created by 逢双 on 14-2-24.
 */
public abstract class Lose {
    protected Coordinate coordinate;
    protected int narrow = 8;

    public abstract void move(long time);

    protected Coordinate pan(Coordinate from, Coordinate to, int time, int loop){
        return new Coordinate((from.x * (loop - time) + to.x * time) / loop,
                (from.y * (loop - time) + to.y * time) / loop);
    }

    public int getX(){
        return coordinate.x;
    }

    public int getY(){
        return coordinate.y;
    }

}
