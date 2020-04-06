package hotciv.standard.StrategyImpls;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WorkForceForceFocusStrategy;
import hotciv.standard.CityImpl;

public class AlphaWorkForceFocusStrategy implements WorkForceForceFocusStrategy {
    @Override
    public void changeWorkForceFocus(Game game, Position p, String balance) {

    }

    @Override
    public void increaseTreasury(Game game, Position p) {
        CityImpl c = (CityImpl) game.getCityAt(p);
        c.increaseTreasury(6);
    }

    @Override
    public void increaseFoodCount(Game game, Position p) {

    }

}
