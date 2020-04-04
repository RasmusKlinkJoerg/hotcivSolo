package hotciv.standard;

import hotciv.framework.Die;

public class FixedDie implements Die {

    @Override public int getDieFactor() {
        return 4;
    }
}
