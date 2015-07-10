package com.HardGame.Element.lose.level_15;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-19.
 */
public class l15_9 extends Lose{

    @Override
    public void move(long time) {
        int TIME = 2500;
        int loop = (int)(time % TIME);
        loop = (loop + 6482) % TIME;
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(10 * 12, 13 * 12), new Coordinate(10 * 12, 18 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(10 * 12, 18 * 12), new Coordinate(10 * 12, 13 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
