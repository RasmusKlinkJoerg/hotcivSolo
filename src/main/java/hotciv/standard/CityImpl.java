package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.HashMap;


public class CityImpl implements City {

    private Player owner;
    private int treasury;
    private String production;
    private String workForceFocus;
    private HashMap<String, Integer> unitPrices;
    private int foodCount;
    private int size;


    public CityImpl(Player owner){
        this.owner = owner;
        treasury = 0;
        foodCount = 0;
        production = GameConstants.LEGION;
        workForceFocus = GameConstants.productionFocus;
        unitPrices = new HashMap<>();
        putUnitPrices();
        size = 1;
    }

    private void putUnitPrices() {
        unitPrices.put(GameConstants.ARCHER, 10);
        unitPrices.put(GameConstants.LEGION, 15);
        unitPrices.put(GameConstants.SETTLER, 30);
        unitPrices.put(GameConstants.B52, 60);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return size;
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
        return workForceFocus;
    }

    @Override
    public int getFoodCount() {
        return foodCount;
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

    public void setWorkForceFocus(String balance) {
        workForceFocus = balance;
    }

    public int getCurrentUnitPrice() {
        return unitPrices.get(production);
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setSize(int i) {
        size = i;
    }

    public void increaseFoodCount(int i) {
        foodCount +=i;
    }

    public void setFoodCount(int i) {
        foodCount = i;
    }
}


