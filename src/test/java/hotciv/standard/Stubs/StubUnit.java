package hotciv.standard.Stubs;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public class StubUnit implements Unit {
    private String type; private Player owner;
    public StubUnit(String type, Player owner) {
        this.type = type; this.owner = owner;
    }
    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return 1; }
    public int getDefensiveStrength() { return 1; }
    public int getAttackingStrength() { return 1; }

    @Override
    public String getId() {
        return null;
    }
}
