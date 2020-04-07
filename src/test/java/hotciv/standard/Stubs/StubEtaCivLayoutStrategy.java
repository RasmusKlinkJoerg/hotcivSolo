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

public class StubEtaCivLayoutStrategy implements LayoutStrategy {
    private HashMap<Position, CityImpl> cities;
    private HashMap<Position, UnitImpl> units;
    private HashMap<Position, Tile> tiles;

    @Override
    public void createWorld(HashMap<Position, CityImpl> cities, HashMap<Position, UnitImpl> units, HashMap<Position, Tile> tiles) {
        this.cities = cities;
        this.units = units;
        this.tiles = tiles;

        //Cities
        putCity(new Position(0, 3), Player.RED);
        putCity(new Position(8, 12), Player.RED);
        putCity(new Position(4, 5), Player.BLUE);

        // A simple implementation to draw the map of DeltaCiv
        /* Define the world as the DeltaCiv layout */
        // Basically we use a 'data driven' approach - code the
        // layout in a simple semi-visual representation, and
        // convert it to the actual Game representation.
        String[] layout =
                new String[]{
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        // Conversion...
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            line = layout[r];
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = line.charAt(c);
                String type = "error";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }
                Position p = new Position(r, c);
                putTile(p, type);
            }
        }
        //Units
        putUnit(new Position(7, 4), Player.RED, GameConstants.ARCHER);
        putUnit(new Position(3, 5), Player.BLUE, GameConstants.LEGION);
        putUnit(new Position(5, 5), Player.RED, GameConstants.SETTLER);
    }

    private void putCity(Position p, Player owner) {
        cities.put(p, new CityImpl(owner));
    }

    private void putTile(Position p, String tileType) {
        tiles.put(p, new TileImpl(tileType));
    }

    private void putUnit(Position p, Player owner, String unitType) {
        units.put(p, new UnitImpl(owner, unitType));
    }
}
