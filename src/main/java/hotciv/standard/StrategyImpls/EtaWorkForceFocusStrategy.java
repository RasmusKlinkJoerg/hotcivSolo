package hotciv.standard.StrategyImpls;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WorkForceForceFocusStrategy;
import hotciv.standard.CityImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EtaWorkForceFocusStrategy implements WorkForceForceFocusStrategy {
    @Override
    public void changeWorkForceFocus(Game game, Position p, String balance) {
        CityImpl c = (CityImpl) game.getCityAt(p);
        c.setWorkForceFocus(balance);
    }

    @Override
    public void increaseTreasury(Game game, Position p) {
        CityImpl c = (CityImpl) game.getCityAt(p);
        HashMap<String, Integer> surroundingTerrain = getSurroundingTerrain(game,p);

        if (c.getWorkforceFocus().equals(GameConstants.productionFocus)) {
            int sum = 1;
            int populationLeft = c.getSize()-1;
            if (surroundingTerrain.get(GameConstants.FOREST) > populationLeft) {
                sum += populationLeft*3;
                populationLeft=0;
            }
            if (surroundingTerrain.get(GameConstants.FOREST) < populationLeft) {
                sum += surroundingTerrain.get(GameConstants.FOREST)*3;
                populationLeft -= surroundingTerrain.get(GameConstants.FOREST);
            }
            if (surroundingTerrain.get(GameConstants.HILLS) > populationLeft) {
                sum += populationLeft*2;
                populationLeft=0;
            }
            if (surroundingTerrain.get(GameConstants.HILLS) < populationLeft) {
                sum += surroundingTerrain.get(GameConstants.HILLS)*2;
                populationLeft -= surroundingTerrain.get(GameConstants.HILLS);
            }
            if (surroundingTerrain.get(GameConstants.MOUNTAINS) > populationLeft) {
                sum += populationLeft;
                populationLeft=0;
            }
            if (surroundingTerrain.get(GameConstants.MOUNTAINS) < populationLeft) {
                sum = surroundingTerrain.get(GameConstants.MOUNTAINS)*3;
            }

            c.increaseTreasury(sum);
        }
    }

    @Override
    public void increaseFoodCount(Game game, Position p) {
        CityImpl c = (CityImpl) game.getCityAt(p);
        HashMap<String, Integer> surroundingTerrain = getSurroundingTerrain(game,p);

        if (c.getWorkforceFocus().equals(GameConstants.foodFocus)) {
            int sum = 1;
            int populationLeft = c.getSize()-1;
            if (surroundingTerrain.get(GameConstants.PLAINS) > populationLeft) {
                sum += populationLeft*3;
                populationLeft=0;
            }
            if (surroundingTerrain.get(GameConstants.PLAINS) < populationLeft) {
                sum += surroundingTerrain.get(GameConstants.PLAINS)*3;
                populationLeft -= surroundingTerrain.get(GameConstants.PLAINS);
            }
            if (surroundingTerrain.get(GameConstants.OCEANS) > populationLeft) {
                sum += populationLeft;
                populationLeft=0;
            }
            if (surroundingTerrain.get(GameConstants.OCEANS) < populationLeft) {
                sum += surroundingTerrain.get(GameConstants.OCEANS);
            }

            c.increaseFoodCount(sum);
        }
    }

    private HashMap<String, Integer> getSurroundingTerrain(Game game, Position p) {
        ArrayList<Integer> rows;
        ArrayList<Integer> cols;
        rows = new ArrayList<>(Arrays.asList(-1, -1, 0, 1, 1, 1, 0, -1));
        cols = new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, -1, -1, -1));
        HashMap<String, Integer> surroundingTerrain = new HashMap<>();
        surroundingTerrain.put(GameConstants.MOUNTAINS, 0);
        surroundingTerrain.put(GameConstants.PLAINS, 0);
        surroundingTerrain.put(GameConstants.OCEANS, 0);
        surroundingTerrain.put(GameConstants.HILLS, 0);
        surroundingTerrain.put(GameConstants.FOREST, 0);
        for (int i = 0; i < rows.size(); i++) {
            int row = rows.get(i);
            int col = cols.get(i);
            Position tempPos = new Position(p.getRow() + row, p.getColumn() + col);
            switch (game.getTileAt(tempPos).getTypeString()) {
                case GameConstants.FOREST:
                    int numberOfSurroundingForests = surroundingTerrain.get(GameConstants.FOREST);
                    surroundingTerrain.put(GameConstants.FOREST, numberOfSurroundingForests + 1);
                    break;
                case GameConstants.HILLS:
                    int numberOfSurroundingHills = surroundingTerrain.get(GameConstants.HILLS);
                    surroundingTerrain.put(GameConstants.HILLS, numberOfSurroundingHills + 1);
                    break;
                case GameConstants.MOUNTAINS:
                    int numberOfSurroundingMountains = surroundingTerrain.get(GameConstants.MOUNTAINS);
                    surroundingTerrain.put(GameConstants.MOUNTAINS, numberOfSurroundingMountains + 1);
                    break;
                case GameConstants.PLAINS:
                    int numberOfSurroundingPlains = surroundingTerrain.get(GameConstants.PLAINS);
                    surroundingTerrain.put(GameConstants.PLAINS, numberOfSurroundingPlains + 1);
                    break;
                case GameConstants.OCEANS:
                    int numberOfSurroundingOceans = surroundingTerrain.get(GameConstants.OCEANS);
                    surroundingTerrain.put(GameConstants.FOREST, numberOfSurroundingOceans + 1);
                    break;
            }
        }
        return surroundingTerrain;
    }

}
