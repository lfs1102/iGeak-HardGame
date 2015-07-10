package com.HardGame.Element.lose.level_6;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-1.
 */
public class l6_3 extends Lose {

    @Override
    public void move(long time) {
        int TIME = 5000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(13 * 12, 10 * 12), new Coordinate(13 * 12, 5 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(13 * 12, 5 * 12), new Coordinate(13 * 12, 10 * 12), loop - 2500, TIME / 2);
        }
    }
}
