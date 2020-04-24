package hotciv.standard.Stubs;

import hotciv.framework.Tile;

public class StubTile implements Tile {
    private String type;
    public StubTile(String type, int r, int c) { this.type = type; }
    public String getTypeString() { return type; }
}
