package hotciv.standard;

import hotciv.framework.GameConstants;

import java.util.UUID;

public class TileImpl implements hotciv.framework.Tile {
    private final String tileType;
    private final String id;

    public TileImpl(String tileType) {
        this.tileType = tileType;
        id = UUID.randomUUID().toString();
    }

    @Override
    public String getTypeString() {
        return tileType;
    }

    @Override
    public String getId() {
        return id;
    }
}
