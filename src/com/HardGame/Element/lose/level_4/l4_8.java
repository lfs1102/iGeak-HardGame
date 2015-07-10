package com.HardGame.Element.lose.level_4;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-2.
 */
public class l4_8 extends Lose{
    @Override
    public void move(long time) {
        int TIME = 8000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(15 * 12, 12 * 12), new Coordinate(5 * 12, 12 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(5 * 12, 12 * 12), new Coordinate(15 * 12, 12 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
