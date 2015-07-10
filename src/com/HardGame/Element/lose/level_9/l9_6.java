package com.HardGame.Element.lose.level_9;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-3.
 */
public class l9_6 extends Lose{
    @Override
    public void move(long time) {
        int TIME = 2000;
        int loop = (int)(time % TIME);
        loop = (loop + 500) % 2000;
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(10 * 12, 12 * 12), new Coordinate(9 * 12, 13 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(9 * 12, 13 * 12), new Coordinate(10 * 12, 12 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
