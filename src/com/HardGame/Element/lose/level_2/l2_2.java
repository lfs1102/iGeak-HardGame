package com.HardGame.Element.lose.level_2;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-1.
 */
public class l2_2 extends Lose {

    @Override
    public void move(long time) {
        int TIME = 5000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(130, 80), new Coordinate(130, 160), loop, TIME / 2);
        }
        else {
            super.coordinate = pan(new Coordinate(130, 160), new Coordinate(130, 80), loop - 2500, TIME / 2);
        }
    }
}
