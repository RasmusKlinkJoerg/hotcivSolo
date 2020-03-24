package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.Strategies.ActionStrategy;
import hotciv.framework.Strategies.AgingStrategy;
import hotciv.framework.Strategies.LayoutStrategy;
import hotciv.framework.Strategies.WinningStrategy;

import java.util.HashMap;


/**
 * Skeleton implementation of HotCiv.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class GameImpl implements Game {
    private LayoutStrategy layoutStrategy;
    private Player playerInTurn = Player.RED;
    private HashMap<Position, CityImpl> cityHashMap;
    private HashMap<Position, Tile> tileHashMap;
    private HashMap<Position, UnitImpl> unitHashMap;
    private int age;
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private ActionStrategy actionStrategy;

    public GameImpl(WinningStrategy winningStrategy, AgingStrategy agingStrategy, ActionStrategy actionStrategy, LayoutStrategy layoutStrategy) {
        cityHashMap = new HashMap<>();
        tileHashMap = new HashMap<>();
        unitHashMap = new HashMap<>();
        age = -4000;
        this.winningStrategy = winningStrategy;
        this.agingStrategy = agingStrategy;
        this.actionStrategy = actionStrategy;
        this.layoutStrategy = layoutStrategy;
        layoutStrategy.createWorld(cityHashMap, unitHashMap, tileHashMap);
    }


    public Tile getTileAt(Position p) {
        return tileHashMap.get(p);
    }

    public Unit getUnitAt(Position p) {
        return unitHashMap.get(p);
    }

    public City getCityAt(Position p) {
        return cityHashMap.get(p);
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public Player getWinner() {
        return winningStrategy.getWinner(age, cityHashMap);
    }

    public int getAge() {
        return age;
    }

    public boolean moveUnit(Position from, Position to) {
        UnitImpl unit = (UnitImpl) getUnitAt(from);
        if (illegalMove(from, to, unit)) return false;
        updateMap(from, to, unit);
        return true;
    }

    private boolean illegalMove(Position from, Position to, UnitImpl unit) {
        boolean toOcean = tileHashMap.get(to).getTypeString() == GameConstants.OCEANS;
        boolean toMountain = tileHashMap.get(to).getTypeString() == GameConstants.MOUNTAINS;
        boolean movingOwnUnit = getUnitAt(from).getOwner() == getPlayerInTurn();
        boolean isUnitAtTo = unitHashMap.get(to) != null;
        boolean ownsUnitAtTo =  isUnitAtTo && getUnitAt(to).getOwner() == getPlayerInTurn();
        boolean moveCountGreaterThanZero = getUnitAt(from).getMoveCount() > 0;
        boolean stationary = unit.getStationary();
        if (stationary) {
            return true;
        }
        if ( toOcean || toMountain  || !movingOwnUnit || ownsUnitAtTo || !moveCountGreaterThanZero) {
            return true;
        }
        return false;
    }

    private void updateMap(Position from, Position to, UnitImpl unit) {
        updateUnits(from, to, unit);
        //take over city if wins attack on it
        takeOverCity(to);
    }

    private void updateUnits(Position from, Position to, UnitImpl unit) {
        unitHashMap.put(to, unit);
        unitHashMap.remove(from);
        unit.decreaseMoveCount(1);
    }

    private void takeOverCity(Position to) {
        CityImpl city = (CityImpl) getCityAt(to);
        boolean isCityAtTo = city != null;
        boolean ownsCityAtTo = isCityAtTo && city.getOwner() == playerInTurn;
        if (isCityAtTo && !ownsCityAtTo) {
            city.setOwner(playerInTurn);
        }
    }

    public void endOfTurn() {
        boolean redsTurn = playerInTurn == Player.RED;
        boolean bluesTurn = playerInTurn == Player.BLUE;
        if (redsTurn) {
            playerInTurn = Player.BLUE;
        }
        if (bluesTurn) {
            playerInTurn = Player.RED;
            endOfRound();
        }
    }

    private void endOfRound() {
        age = agingStrategy.increaseAge(age);
        cityActions();
        for (UnitImpl u : unitHashMap.values()) {
            u.resetMoveCount();
        }
    }

    private void cityActions() {
        for (Position p : cityHashMap.keySet()) {
            CityImpl c = cityHashMap.get(p);
            c.increaseTreasury(6);
            if (c.getTreasury() > c.getCurrentUnitPrice()){
                placeUnit(p, c);
                c.decreaseTreasury(c.getCurrentUnitPrice());
            }
        }
    }

    private void placeUnit(Position cityPos, City c) {
        //in city
        if (getUnitAt(cityPos) == null) {
            unitHashMap.put(cityPos, new UnitImpl(c.getOwner(),c.getProduction()));
            return;
        }
        int []rows = {-1, -1, 0, 1, 1, 1, 0, -1};
        int []cols = {0, 1, 1, 1, 0, -1, -1, -1};
        int i = 0;
        for(int row : rows){
            int col = cols[i]; //get the matching pairs of row and column to get the surrounding positions in clockwise order, starting from north.
                Position tempPos = new Position(cityPos.getRow()+row, cityPos.getColumn()+col);
                Tile t = getTileAt(tempPos);
                boolean validTile = !t.getTypeString().equals(GameConstants.OCEANS) && !t.getTypeString().equals(GameConstants.MOUNTAINS);
                if (getUnitAt(tempPos)==null & validTile) {
                    unitHashMap.put(tempPos, new UnitImpl(c.getOwner(),c.getProduction()));
                    return;
                }
                i++;
            }
        }



    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    public void changeProductionInCityAt(Position p, String unitType) {
        CityImpl c = (CityImpl) getCityAt(p);
        c.setProduction(unitType);
    }

    public void performUnitActionAt(Position p) {
        actionStrategy.performUnitActionAt(p, unitHashMap, cityHashMap);
    }

}
