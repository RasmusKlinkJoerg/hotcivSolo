package hotciv.standard.StrategyImpls;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.LayoutStrategy;
import hotciv.framework.Tile;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class AlphaLayoutStrategy implements LayoutStrategy {
    HashMap<Position, CityImpl> cityHashMap;
    HashMap<Position, UnitImpl> unitHashMap;
    HashMap<Position, Tile> tileHashMap;

    @Override
    public void createWorld(HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tileHashMap) {
        this.cityHashMap = cityHashMap;
        this.unitHashMap = unitHashMap;
        this.tileHashMap = tileHashMap;

            //Cities
            putCity(new Position(1, 1), Player.RED);
            putCity(new Position(4, 1), Player.BLUE);

            //Tiles
            for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
                for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
                    putTile(new Position(i, j), GameConstants.PLAINS);
                }
            }
            putTile(new Position(1, 0), GameConstants.OCEANS);
            putTile(new Position(0, 1), GameConstants.HILLS);
            putTile(new Position(2, 2), GameConstants.MOUNTAINS);

            //Units
            putUnit(new Position(2, 0), Player.RED, GameConstants.ARCHER);
            putUnit(new Position(3, 2), Player.BLUE, GameConstants.LEGION);
            putUnit(new Position(4, 3), Player.RED, GameConstants.SETTLER);
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
