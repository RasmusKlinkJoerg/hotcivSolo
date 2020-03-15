package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;


import java.util.HashMap;

public class UnitImpl implements hotciv.framework.Unit {
    private  HashMap<String, Integer> attackMap;
    private  HashMap<String, Integer> defenseMap;
    private String unitType;
    private Player owner;
    private int moveCount;
    private int defensiveStrength;
    private int attackingStrength;
    private boolean stationary;
    private boolean fortified;

    public UnitImpl(Player owner, String unitType) {
        this.owner = owner;
        this.unitType = unitType;
        this.moveCount = 1;
        defenseMap = new HashMap<>();
        attackMap = new HashMap<>();
        putUnitDef();
        putUnitAtc();
        this.defensiveStrength = defenseMap.get(unitType);
        this.attackingStrength = attackMap.get(unitType);
        stationary = false;
        fortified = false;
    }

    private void putUnitDef() {
        defenseMap.put(GameConstants.ARCHER, 3);
        defenseMap.put(GameConstants.LEGION, 2);
        defenseMap.put(GameConstants.SETTLER, 3);
    }

    private void putUnitAtc() {
        attackMap.put(GameConstants.ARCHER, 2);
        attackMap.put(GameConstants.LEGION, 4);
        attackMap.put(GameConstants.SETTLER, 0);
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
        return attackingStrength;
    }

    public void decreaseMoveCount(int i) {
        moveCount -= i;
    }

    public void resetMoveCount() {
        moveCount = 1;
    }

    public void setDefensiveStrength(int i) {
        defensiveStrength = i;
    }

    public void setStationary(boolean b) {
        stationary = b;
    }

    public boolean getStationary() {
        return stationary;
    }

    public boolean getFortified() {
        return fortified;
    }

    public void setFortified(boolean b) {
        fortified = b;
    }

}
