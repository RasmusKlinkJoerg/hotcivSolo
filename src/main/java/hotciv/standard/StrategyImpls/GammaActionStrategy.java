package hotciv.standard.StrategyImpls;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.ActionStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class GammaActionStrategy implements ActionStrategy {
    @Override
    public void performUnitActionAt(Position p, HashMap<Position, UnitImpl> units, HashMap<Position, CityImpl> cities) {
        UnitImpl unit = units.get(p);
        String unitType = unit.getTypeString();
        boolean isArcher = unitType.equals(GameConstants.ARCHER);
        boolean isSettler = unitType.equals(GameConstants.SETTLER);
        if (isArcher) {
            performArcherAction(unit);
        }
        if (isSettler) {
            performSettlerAction(p, units, cities, unit);
        }
    }

    private void performArcherAction(UnitImpl unit) {
        if (unit.getFortified()) {
            unit.setDefensiveStrength(3);
            unit.setStationary(false);
            unit.setFortified(false);
        } else {
            int oldDef = unit.getDefensiveStrength();
            unit.setDefensiveStrength(oldDef*2);
            unit.setStationary(true);
            unit.setFortified(true);
        }
    }

    private void performSettlerAction(Position p, HashMap<Position, UnitImpl> units, HashMap<Position, CityImpl> cities, UnitImpl unit) {
        CityImpl newCity = new CityImpl(unit.getOwner());
        cities.put(p, newCity);
        units.remove(p);
    }
}
