package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;


import java.util.HashMap;
import java.util.UUID;

public class UnitImpl implements hotciv.framework.Unit {
    private  HashMap<String, Integer> attackMap;
    private  HashMap<String, Integer> defenseMap;
    private  HashMap<String, Integer> moveCountMap;
    private String unitType;
    private Player owner;
    private int moveCount;
    private int defensiveStrength;
    private int attackingStrength;
    private boolean stationary;
    private boolean fortified;
    private String id;

    public UnitImpl(Player owner, String unitType) {
        this.owner = owner;
        this.unitType = unitType;
        defenseMap = new HashMap<>();
        attackMap = new HashMap<>();
        moveCountMap = new HashMap<>();
        putUnitDef();
        putUnitAtc();
        putUnitMoveCount();
        defensiveStrength = defenseMap.get(unitType);
        attackingStrength = attackMap.get(unitType);
        moveCount = moveCountMap.get(unitType);
        stationary = false;
        fortified = false;
        id = UUID.randomUUID().toString();
    }

    private void putUnitDef() {
        defenseMap.put(GameConstants.ARCHER, 3);
        defenseMap.put(GameConstants.LEGION, 2);
        defenseMap.put(GameConstants.SETTLER, 3);
        defenseMap.put(GameConstants.B52, 8);
    }

    private void putUnitAtc() {
        attackMap.put(GameConstants.ARCHER, 2);
        attackMap.put(GameConstants.LEGION, 4);
        attackMap.put(GameConstants.SETTLER, 0);
        attackMap.put(GameConstants.B52, 1);
    }
    private void putUnitMoveCount() {
        moveCountMap.put(GameConstants.ARCHER, 1);
        moveCountMap.put(GameConstants.LEGION, 1);
        moveCountMap.put(GameConstants.SETTLER, 1);
        moveCountMap.put(GameConstants.B52, 2);
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

    @Override
    public String getId() {
        return id;
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
