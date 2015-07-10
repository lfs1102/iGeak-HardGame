package com.HardGame.Element.lose.level_5;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-2.
 */
public class l5_13 extends Lose{
    @Override
    public void move(long time) {
        int TIME = 5000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(8 * 12, 5 * 12), new Coordinate(8 * 12, 16 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(8 * 12, 16 * 12), new Coordinate(8 * 12, 5 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
