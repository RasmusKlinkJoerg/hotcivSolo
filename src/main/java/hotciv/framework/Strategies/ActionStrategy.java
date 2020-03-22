package hotciv.framework.Strategies;

import hotciv.framework.Position;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public interface ActionStrategy {
    void performUnitActionAt(Position p, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, CityImpl> cityHashMap);
}
