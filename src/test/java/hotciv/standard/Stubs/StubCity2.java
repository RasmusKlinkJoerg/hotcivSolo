package hotciv.standard.Stubs;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class StubCity2 implements City {


    public StubCity2() {
    }

    @Override
    public Player getOwner() {
        return Player.GREEN;
    }

    @Override
    public int getSize() {
        return 42;
    }

    @Override
    public int getTreasury() {
        return 42;
    }

    @Override
    public String getProduction() {
        return GameConstants.LEGION;
    }

    @Override
    public String getWorkforceFocus() {
        return GameConstants.productionFocus;
    }

    @Override
    public int getFoodCount() {
        return 42;
    }

    @Override
    public String getId() {
        return "StubCity2-Id";
    }
}
