package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;


import java.util.HashMap;

public class UnitImpl implements hotciv.framework.Unit {
    private  HashMap<String, Integer> defenseMap;
    private String unitType;
    private Player owner;
    private int moveCount;
    private int defensiveStrength;

    public UnitImpl(Player owner, String unitType) {
        this.owner = owner;
        this.unitType = unitType;
        this.moveCount = 1;
        defenseMap = new HashMap<>();
        putUnitDef();
        this.defensiveStrength = defenseMap.get(unitType);
    }

    private void putUnitDef() {
        defenseMap.put(GameConstants.ARCHER, 3);
        defenseMap.put(GameConstants.LEGION, 2);
        defenseMap.put(GameConstants.SETTLER, 3);
    }

    @Override
    public String getTypeString() {
        return unitType;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }

    public void decreaseMoveCount(int i) {
        moveCount -= i;
    }

    public void resetMoveCount() {
        moveCount = 1;
    }
}
