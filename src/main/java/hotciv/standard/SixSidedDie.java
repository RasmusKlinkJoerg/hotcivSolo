package hotciv.standard;

import hotciv.framework.Die;

import java.util.Random;


public class SixSidedDie implements Die {


    @Override
    public int getDieFactor() {
        Random random = new Random();
        return random.nextInt(5)+1;
    }
}
