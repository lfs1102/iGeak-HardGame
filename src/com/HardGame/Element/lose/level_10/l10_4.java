package com.HardGame.Element.lose.level_10;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-1.
 */
public class l10_4 extends Lose {

    @Override
    public void move(long time) {
        int TIME = 4000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(13 * 12 + 6, 13 * 12), new Coordinate(9 * 12, 13 * 12), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(9 * 12, 13 * 12), new Coordinate(13 * 12 + 6, 13 * 12), loop - TIME / 2, TIME / 2);
        }
    }
}
