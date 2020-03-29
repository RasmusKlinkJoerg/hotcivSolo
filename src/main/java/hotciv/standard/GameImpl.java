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
    private Player playerInTurn = Player.RED;
    private HashMap<Position, CityImpl> cities;
    private HashMap<Position, Tile> tiles;
    private HashMap<Position, UnitImpl> units;
    private int age;
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private ActionStrategy actionStrategy;

    public GameImpl(WinningStrategy winningStrategy, AgingStrategy agingStrategy, ActionStrategy actionStrategy, LayoutStrategy layoutStrategy) {
        cities = new HashMap<>();
        tiles = new HashMap<>();
        units = new HashMap<>();
        age = -4000;
        this.winningStrategy = winningStrategy;
        this.agingStrategy = agingStrategy;
        this.actionStrategy = actionStrategy;
        layoutStrategy.createWorld(cities, units, tiles);
    }


    public Tile getTileAt(Position p) {
        return tiles.get(p);
    }

    public Unit getUnitAt(Position p) {
        return units.get(p);
    }

    public City getCityAt(Position p) {
        return cities.get(p);
    }

    public Player getPlayerInTurn() {
        return playerInTurn;
    }

    public Player getWinner() {
        return winningStrategy.getWinner(age, cities);
    }

    public int getAge() {
        return age;
    }

    public boolean moveUnit(Position from, Position to) {
        if (!isLegalMove(from, to)) return false;
        updateWorld(from, to);
        return true;
    }

    private boolean isLegalMove(Position from, Position to) {
        UnitImpl unit = (UnitImpl) getUnitAt(from);

        boolean isUnitAtTo = units.get(to) != null;
        if (isUnitAtTo) {
            boolean isOpponentAtTo = getUnitAt(to).getOwner() != getPlayerInTurn();
            return isOpponentAtTo;
        }
        boolean toOcean = tiles.get(to).getTypeString().equals(GameConstants.OCEANS);
        boolean toMountain = tiles.get(to).getTypeString().equals(GameConstants.MOUNTAINS);
        boolean movingToValidTerrain = !toOcean && !toMountain;
        boolean movingOwnUnit = getUnitAt(from).getOwner() == getPlayerInTurn();
        boolean moveCountGreaterThanZero = getUnitAt(from).getMoveCount() > 0;
        boolean moveDistanceIsOneOrLess = Math.abs(from.getRow()- to.getRow())<=1 && Math.abs(from.getColumn()-to.getColumn())<=1;
        boolean stationary = unit.getStationary();

        return  movingOwnUnit &&
                movingToValidTerrain &&
                moveCountGreaterThanZero &&
                moveDistanceIsOneOrLess &&
                !stationary;
    }

    private void updateWorld(Position from, Position to) {
        updateUnits(from, to);
        //take over city if wins attack on it
        boolean isCityAtTo = getCityAt(to) != null;
        if (isCityAtTo) {
            takeOverCity(to);
        }
    }

    private void updateUnits(Position from, Position to) {
        UnitImpl unit = (UnitImpl) getUnitAt(from);
        units.put(to, unit);
        units.remove(from);
        unit.decreaseMoveCount(1);
    }

    private void takeOverCity(Position to) {
        CityImpl city = (CityImpl) getCityAt(to);
        boolean ownsCityAtTo = city.getOwner() == playerInTurn;
        if (!ownsCityAtTo) {
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
        performCityActions();
        for (UnitImpl u : units.values()) {
            u.resetMoveCount();
        }
    }

    private void performCityActions() {
        for (Position p : cities.keySet()) {
            CityImpl c = cities.get(p);
            c.increaseTreasury(6);
            if (c.getTreasury() >= c.getCurrentUnitPrice()){
                placeUnit(p, c);
                c.decreaseTreasury(c.getCurrentUnitPrice());
            }
        }
    }
    //Get the matching pairs of row and column to first get inside the city,
    // then the surrounding positions in clockwise order, starting from north.
    private void placeUnit(Position position, City city) {
        int []rows = {0,-1, -1, 0, 1, 1, 1, 0, -1};
        int []cols = {0, 0, 1, 1, 1, 0, -1, -1, -1};
        for(int i = 0; i< rows.length; i++){
            int row = rows[i];
            int col = cols[i];
                Position tempPos = new Position(position.getRow()+row, position.getColumn()+col);
                if (isValidPlacement(tempPos)) {
                    units.put(tempPos, new UnitImpl(city.getOwner(), city.getProduction()));
                }
            }
        }

        private boolean isValidPlacement(Position position) {
            Tile t = getTileAt(position);
            boolean validTile = !t.getTypeString().equals(GameConstants.OCEANS) &&
                        !t.getTypeString().equals(GameConstants.MOUNTAINS);
            boolean unitAtPos = getUnitAt(position) != null;
            boolean validPos = 0 <= position.getRow() &&
                        position.getRow() < GameConstants.WORLDSIZE  &&
                        0 <= position.getColumn() &&
                        position.getColumn() < GameConstants.WORLDSIZE;
            return  validTile && !unitAtPos && validPos;
        }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    public void changeProductionInCityAt(Position p, String unitType) {
        CityImpl c = (CityImpl) getCityAt(p);
        c.setProduction(unitType);
    }

    public void performUnitActionAt(Position p) {
        actionStrategy.performUnitActionAt(p, units, cities);
    }

}
