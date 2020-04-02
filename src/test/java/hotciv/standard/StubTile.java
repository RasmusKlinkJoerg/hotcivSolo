package hotciv.standard;

import hotciv.framework.Tile;

class StubTile implements Tile {
    private String type;
    public StubTile(String type, int r, int c) { this.type = type; }
    public String getTypeString() { return type; }
}
