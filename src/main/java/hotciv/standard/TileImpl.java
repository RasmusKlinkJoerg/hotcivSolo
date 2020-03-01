package hotciv.standard;

import hotciv.framework.GameConstants;

public class TileImpl implements hotciv.framework.Tile {
    private final String tileType;

    public TileImpl(String tileType) {
        this.tileType = tileType;
    }

    @Override
    public String getTypeString() {
        System.out.println("hello from tileimpl");
        return tileType;
    }
}
