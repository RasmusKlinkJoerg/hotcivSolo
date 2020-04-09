package hotciv.standard.StrategyImpls;

import hotciv.framework.*;
import hotciv.framework.Strategies.ActionStrategy;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.HashMap;

public class ThetaActionStrategy implements ActionStrategy {
    private GammaActionStrategy gammaActionStrategy = new GammaActionStrategy();


    @Override
    public void performUnitActionAt(Game game, Position p, HashMap<Position, CityImpl> cityHashMap, HashMap<Position, UnitImpl> unitHashMap, HashMap<Position, Tile> tileHashMap) {
        gammaActionStrategy.performUnitActionAt(game, p, cityHashMap, unitHashMap, tileHashMap);
        UnitImpl unit = (UnitImpl) game.getUnitAt(p);
        String unitType = unit.getTypeString();
        boolean isB52 = unitType.equals(GameConstants.B52);
        if (isB52) {
            if (game.getCityAt(p) != null) {
                CityImpl city = (CityImpl) game.getCityAt(p);
                int oldSize = city.getSize();
                city.setSize(oldSize - 1);
                if (city.getSize() <= 0) {
                    cityHashMap.remove(p);
                }
            }
            if (game.getTileAt(p).getTypeString().equals(GameConstants.FOREST)) {
                tileHashMap.put(p, new TileImpl(GameConstants.PLAINS));
            }

        }

    }
}

