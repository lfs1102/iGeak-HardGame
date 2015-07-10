package com.HardGame.Element.lose.level_12;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-1.
 */
public class l12_2 extends Lose {

    @Override
    public void move(long time) {
        int TIME = 2000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 2){
            super.coordinate = new Coordinate(11 * 12 + 10, 12 * 12);
        }
        else {
            super.coordinate = new Coordinate(24 * 12, 24 * 12);
        }
    }
}
