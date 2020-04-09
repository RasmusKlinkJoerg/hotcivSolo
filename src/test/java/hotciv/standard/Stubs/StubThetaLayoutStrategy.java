package hotciv.standard.Stubs;

import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.LayoutStrategy;
import hotciv.framework.Tile;
import hotciv.standard.CityImpl;
import hotciv.standard.StrategyImpls.AlphaLayoutStrategy;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class StubThetaLayoutStrategy implements LayoutStrategy {
    AlphaLayoutStrategy alphaLayoutStrategy = new AlphaLayoutStrategy();
    @Override
    public void createWorld(HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tileHashMap) {
        alphaLayoutStrategy.createWorld(cityHashMap, unitHashMap, tileHashMap);
        tileHashMap.put(new Position(2,1), new TileImpl(GameConstants.FOREST));
    }
}
