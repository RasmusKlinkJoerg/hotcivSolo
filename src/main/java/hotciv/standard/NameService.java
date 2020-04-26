package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

import java.util.HashMap;

public class NameService {


    private HashMap<String, City> cityHashMap;
    private HashMap<String, Unit> unitHashMap;
    private HashMap<String, Tile> tileHashMap;

    public NameService() {
        cityHashMap = new HashMap<>();
        unitHashMap = new HashMap<>();
        tileHashMap = new HashMap<>();
    }



    public City getCity(String objectId) {
        return cityHashMap.get(objectId);
    }

    public void putCity(String id, City city) {
        cityHashMap.put(id, city);
    }

    public void putUnit(String id, Unit unit) {
        unitHashMap.put(id, unit);
    }

    public Unit getUnit(String objectId) {
        return unitHashMap.get(objectId);
    }

    public void putTile(String id, Tile tile) {
        tileHashMap.put(id, tile);
    }

    public Tile getTile(String objectId) {
        return tileHashMap.get(objectId);
    }

}
