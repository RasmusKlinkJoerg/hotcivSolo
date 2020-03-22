package hotciv.framework.Strategies;

import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public interface LayoutStrategy {
    void createWorld(HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tileHashMap);
}
