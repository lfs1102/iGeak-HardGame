package com.HardGame.Element.lose.level_7;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-3.
 */
public class l7_5 extends Lose{
    @Override
    public void move(long time) {
        int TIME = 6000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(7 * 12, 7 * 12), new Coordinate(7 * 12, 12 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(7 * 12, 12 * 12), new Coordinate(7 * 12, 7 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
