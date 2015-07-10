package com.HardGame.Element.lose.level_16;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-19.
 */
public class l16_12 extends Lose{

    @Override
    public void move(long time) {
        int TIME = 2000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(10 * 12, 10 * 12), new Coordinate(11 * 12, 10 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(11 * 12, 10 * 12), new Coordinate(10 * 12, 10 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
