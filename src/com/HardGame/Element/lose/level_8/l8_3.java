package com.HardGame.Element.lose.level_8;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-3.
 */
public class l8_3 extends Lose{
    @Override
    public void move(long time) {
        int TIME = 5000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 4){
            super.coordinate = pan(new Coordinate(6 * 12, 11 * 12), new Coordinate(9 * 12, 11 * 12), loop, TIME / 4);
        }
        else if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(9 * 12, 11 * 12), new Coordinate(9 * 12, 14 * 12), loop - TIME/4, TIME / 4);
        }
        else if(loop < 3 * TIME / 4){
            super.coordinate = pan(new Coordinate(9 * 12, 14 * 12), new Coordinate(6 * 12, 14 * 12), loop - TIME/2, TIME / 4);
        }
        else {
            super.coordinate = pan(new Coordinate(6 * 12, 14 * 12), new Coordinate(6 * 12, 11 * 12), loop - 3 * TIME/4, TIME / 4);
        }
    }
}
