package hotciv.standard.Stubs;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.LayoutStrategy;
import hotciv.framework.Tile;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class StubEpsilonLayout implements LayoutStrategy {
    HashMap<Position, CityImpl> cityHashMap;
    HashMap<Position, UnitImpl> unitHashMap;
    HashMap<Position, Tile> tileHashMap;

    @Override
    public void createWorld(HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tileHashMap) {
        this.cityHashMap = cityHashMap;
        this.unitHashMap = unitHashMap;
        this.tileHashMap = tileHashMap;

        //Cities
        putCity(new Position(1, 0), Player.BLUE);
        putCity(new Position(2, 0), Player.BLUE);
        putCity(new Position(3, 0), Player.BLUE);

        //Tiles
        for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
            for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                putTile(new Position(i, j), GameConstants.PLAINS);
            }
        }

        //Units
        putUnit(new Position(0, 0), Player.RED, GameConstants.LEGION);
        putUnit(new Position(0, 1), Player.BLUE, GameConstants.LEGION);
        putUnit(new Position(0, 2), Player.BLUE, GameConstants.LEGION);
        putUnit(new Position(0, 3), Player.BLUE, GameConstants.LEGION);

        putUnit(new Position(3, 3), Player.RED, GameConstants.ARCHER);
        putUnit(new Position(4, 3), Player.BLUE, GameConstants.SETTLER);
        putUnit(new Position(4, 4), Player.BLUE, GameConstants.SETTLER);
        putUnit(new Position(3, 4), Player.BLUE, GameConstants.SETTLER);

        putUnit(new Position(12, 4), Player.RED, GameConstants.SETTLER);
        putUnit(new Position(12, 5), Player.BLUE, GameConstants.ARCHER);
    }

    private void putCity(Position p, Player owner) {
        cityHashMap.put(p, new CityImpl(owner));
    }

    private void putTile(Position p, String tileType) {
        tileHashMap.put(p, new TileImpl(tileType));
    }

    private void putUnit(Position p, Player owner, String unitType) {
        unitHashMap.put(p, new UnitImpl(owner, unitType));
    }
}
