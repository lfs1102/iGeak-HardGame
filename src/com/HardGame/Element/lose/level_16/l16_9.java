package com.HardGame.Element.lose.level_16;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-19.
 */
public class l16_9 extends Lose{

    @Override
    public void move(long time) {
        int TIME = 4000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(12 * 12, 12 * 12), new Coordinate(12 * 12, 16 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(12 * 12, 16 * 12), new Coordinate(12 * 12, 12 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
