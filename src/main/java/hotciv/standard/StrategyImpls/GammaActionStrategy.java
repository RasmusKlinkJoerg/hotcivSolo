package hotciv.standard.StrategyImpls;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.ActionStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class GammaActionStrategy implements ActionStrategy {
    @Override
    public void performUnitActionAt(Position p, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, CityImpl> cityHashMap) {
        UnitImpl unit = unitHashMap.get(p);
        String unitType = unit.getTypeString();
        if (unitType.equals(GameConstants.ARCHER)) {
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
        if (unitType.equals(GameConstants.SETTLER)) {
            CityImpl newCity = new CityImpl(unit.getOwner());
            cityHashMap.put(p, newCity);
            unitHashMap.remove(p);
        }
    }
}
