package hotciv.standard.StrategyImpls;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.ActionStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class ThetaActionStrategy implements ActionStrategy {
    private GammaActionStrategy gammaActionStrategy = new GammaActionStrategy();


    @Override
    public void performUnitActionAt(Game game, Position p, HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap) {
        gammaActionStrategy.performUnitActionAt(game, p, cityHashMap, unitHashMap);
        UnitImpl unit = unitHashMap.get(p);
        String unitType = unit.getTypeString();
        boolean isB52 = unitType.equals(GameConstants.B52);

    }
}

