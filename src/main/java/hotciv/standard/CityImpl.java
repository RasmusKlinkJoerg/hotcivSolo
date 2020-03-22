package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.HashMap;


public class CityImpl implements City {

    private Player owner;
    private int treasury;
    private String production;
    private HashMap<String, Integer> unitPrices;


    public CityImpl(Player owner){
        this.owner = owner;
        this.treasury = 0;
        this.production = GameConstants.LEGION;
        unitPrices = new HashMap<>();
        putUnitPrices();
    }

    private void putUnitPrices() {
        unitPrices.put(GameConstants.ARCHER, 10);
        unitPrices.put(GameConstants.LEGION, 15);
        unitPrices.put(GameConstants.SETTLER, 30);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int getTreasury() {
        return treasury;
    }

    @Override
    public String getProduction() {
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }


    public void increaseTreasury(int i) {
         treasury += i;
    }

    public void decreaseTreasury(int i) {
        treasury -= i;
    }

    public void setProduction(String unitType) {
        production = unitType;
    }

    public int getCurrentUnitPrice() {
        return unitPrices.get(production);
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}


