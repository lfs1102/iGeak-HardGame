package com.HardGame.Element.lose.level_9;

import com.HardGame.Element.lose.Coordinate;
import com.HardGame.Element.lose.Lose;

/**
 * Created by 逢双 on 14-3-3.
 */
public class l9_1 extends Lose{
    @Override
    public void move(long time) {
        int TIME = 4000;
        int loop = (int)(time % TIME);
        if(loop < TIME / 4){
            super.coordinate = pan(new Coordinate(7 * 12, 7 * 12), new Coordinate(8 * 12, 7 * 12), loop, TIME / 4);
        }
        else if(loop < TIME / 2){
            super.coordinate = pan(new Coordinate(8 * 12, 7 * 12), new Coordinate(8 * 12, 8 * 12), loop - TIME/4, TIME / 4);
        }
        else if(loop < 3 * TIME / 4){
            super.coordinate = pan(new Coordinate(8 * 12, 8 * 12), new Coordinate(7 * 12, 8 * 12), loop - TIME/2, TIME / 4);
        }
        else {
            super.coordinate = pan(new Coordinate(7 * 12, 8 * 12), new Coordinate(7 * 12, 7 * 12), loop - 3 * TIME/4, TIME / 4);
        }
    }
}
