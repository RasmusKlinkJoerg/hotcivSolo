package hotciv.standard.Stubs;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public class StubUnit2 implements Unit {
    private String type; private Player owner;
    public StubUnit2(String type, Player owner) {
        this.type = type; this.owner = owner;
    }
    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return 42; }
    public int getDefensiveStrength() { return 17; }
    public int getAttackingStrength() { return 37; }

    @Override
    public String getId() {
        return "StubUnit2-Id";
    }
}
