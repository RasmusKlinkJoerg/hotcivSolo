package hotciv.standard.StrategyImpls;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.ActionStrategy;
import hotciv.framework.Tile;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class GammaActionStrategy implements ActionStrategy {


    @Override
    public void performUnitActionAt(Game game, Position p, HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tiles) {
        UnitImpl unit = unitHashMap.get(p);
        String unitType = unit.getTypeString();
        boolean isArcher = unitType.equals(GameConstants.ARCHER);
        boolean isSettler = unitType.equals(GameConstants.SETTLER);
        if (isArcher) {
            performArcherAction(unit);
        }
        if (isSettler) {
            performSettlerAction(p, cityHashMap, unitHashMap, unit);
        }
    }

    private void performArcherAction(UnitImpl unit) {
        if (unit.getFortified()) {
            unit.setDefensiveStrength(3);
            unit.setStationary(false);
            unit.setFortified(false);
        } else {
            int oldDef = unit.getDefensiveStrength();
            unit.setDefensiveStrength(oldDef * 2);
            unit.setStationary(true);
            unit.setFortified(true);
        }
    }

    private void performSettlerAction(Position p, HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, UnitImpl unit) {
        CityImpl newCity = new CityImpl(unit.getOwner());
        cityHashMap.put(p, newCity);
        unitHashMap.remove(p);
    }
}

