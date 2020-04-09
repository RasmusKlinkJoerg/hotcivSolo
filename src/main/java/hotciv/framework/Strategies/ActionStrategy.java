package hotciv.framework.Strategies;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public interface ActionStrategy {
    void performUnitActionAt(Game game, Position p, HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tiles);
}
