package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.Strategies.*;
import hotciv.view.CivDrawing;
import minidraw.standard.MiniDrawApplication;

import java.util.ArrayList;
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
    private int roundNumber;
    private HashMap<Player, Integer> attacksWonMap;
    private String workForceFocus;

    private GameFactory factory;
    private WinningStrategy winningStrategy;
    private AgingStrategy agingStrategy;
    private ActionStrategy actionStrategy;
    private AttackStrategy attackStrategy;
    private LayoutStrategy layoutStrategy;
    private WorkForceForceFocusStrategy workForceFocusStrategy;
    private PopulationGrowthStrategy populationGrowthStrategy;
    private ArrayList<GameObserver> observerList;
    private Position tileFocus;


    public GameImpl(GameFactory factory) {
        cities = new HashMap<>();
        tiles = new HashMap<>();
        units = new HashMap<>();
        age = -4000;
        roundNumber = 1; //The first round of the game is round number 1.
        attacksWonMap = new HashMap<>();
        attacksWonMap.put(Player.RED, 0);
        attacksWonMap.put(Player.BLUE, 0);
        workForceFocus = GameConstants.productionFocus;

        this.factory = factory;
        this.winningStrategy = factory.createWinningStrategy();
        this.agingStrategy = factory.createAgingStrategy();
        this.actionStrategy = factory.createActionStrategy();
        this.layoutStrategy = factory.createLayoutStrategy();
        this.attackStrategy = factory.createAttackStrategy();
        this.workForceFocusStrategy = factory.createWorkForceFocusStrategy();
        this.populationGrowthStrategy = factory.createPopulationGrowthStrategy();

        layoutStrategy.createWorld(cities, units, tiles);

        observerList = new ArrayList<>();
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
        return winningStrategy.getWinner(age, cities, attacksWonMap, roundNumber);
    }

    public int getAge() {
        return age;
    }

    //----- moveUnit Start -----
    public boolean moveUnit(Position from, Position to) {
        if (!isLegalMove(from, to)) return false;
        updateWorld(from, to);
        return true;
    }

    private boolean isLegalMove(Position from, Position to) {
        UnitImpl unit = (UnitImpl) getUnitAt(from);

        boolean isUnitAtTo = getUnitAt(to) != null;
        boolean ownsUnitAtTo = isUnitAtTo && getUnitAt(to).getOwner() == playerInTurn;

        boolean toOcean = getTileAt(to).getTypeString().equals(GameConstants.OCEANS);
        boolean toMountain = getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
        boolean movingToValidTerrain = !toOcean && !toMountain;
        boolean movingOwnUnit = getUnitAt(from).getOwner() == getPlayerInTurn();
        boolean moveCountGreaterThanZero = getUnitAt(from).getMoveCount() > 0;
        boolean moveDistanceIsOneOrLess = Math.abs(from.getRow() - to.getRow()) <= 1 && Math.abs(from.getColumn() - to.getColumn()) <= 1;
        boolean stationary = unit.getStationary();

        return movingOwnUnit &&
                movingToValidTerrain &&
                moveCountGreaterThanZero &&
                moveDistanceIsOneOrLess &&
                !stationary &&
                !ownsUnitAtTo;
    }

    private void updateWorld(Position from, Position to) {
        updateUnits(from, to);
    }

    private void updateUnits(Position from, Position to) {
        UnitImpl unit = units.get(from);
        boolean isUnitAtTo = getUnitAt(to) != null;
        boolean isCityAtTo = getCityAt(to) != null;
        if (isUnitAtTo) {
            boolean attackWon = attackStrategy.attack(this, from, to, playerInTurn);
            if (attackWon) {
                int attacksWon = attacksWonMap.get(playerInTurn);
                attacksWonMap.put(playerInTurn, attacksWon + 1);
                units.put(to, unit);
                if (isCityAtTo) {
                    takeOverCity(to);
                }
                for (GameObserver observer : observerList) {
                    observer.worldChangedAt(to);
                }
            }
            units.remove(from);
            for (GameObserver observer : observerList) {
                observer.worldChangedAt(from);
            }
            unit.decreaseMoveCount(1);
            return;
        }
        boolean unitIsB52Bomber = unit.getTypeString().equals(GameConstants.B52);
        if (isCityAtTo && !unitIsB52Bomber) {
            int attacksWon = attacksWonMap.get(playerInTurn);
            attacksWonMap.put(playerInTurn, attacksWon + 1);
            takeOverCity(to);
        }
        units.put(to, unit);
        units.remove(from);
        for (GameObserver observer : observerList) {
            observer.worldChangedAt(from);
            observer.worldChangedAt(to);
        }
        unit.decreaseMoveCount(1);
    }

    private void takeOverCity(Position to) {
        CityImpl city = (CityImpl) getCityAt(to);
        boolean ownsCityAtTo = city.getOwner() == playerInTurn;
        if (!ownsCityAtTo) {
            city.setOwner(playerInTurn);
        }
    }
    //----- moveUnit End -----

    //----- endOfTurn Start -----
    public void endOfTurn() {
        boolean redsTurn = playerInTurn == Player.RED;
        boolean bluesTurn = playerInTurn == Player.BLUE;
        if (redsTurn) {
            playerInTurn = Player.BLUE;
            for (GameObserver observer : observerList) {
                observer.turnEnds(Player.BLUE, age);
            }
        }
        if (bluesTurn) {
            playerInTurn = Player.RED;
            endOfRound();
        }
    }

    private void endOfRound() {
        age = agingStrategy.increaseAge(age);
        for (UnitImpl u : units.values()) {
            u.resetMoveCount();
        }
        performCityActions();
        roundNumber++;
        for (GameObserver observer : observerList) {
            observer.turnEnds(Player.RED, age);
        }
    }

    private void performCityActions() {
        for (Position p : cities.keySet()) {
            CityImpl c = cities.get(p);
            workForceFocusStrategy.increaseTreasury(this, p);
            workForceFocusStrategy.increaseFoodCount(this, p);
            populationGrowthStrategy.increaseCitySize(c);
            if (c.getTreasury() >= c.getCurrentUnitPrice()) {
                placeUnit(p, c);
                c.decreaseTreasury(c.getCurrentUnitPrice());
                for (GameObserver observer : observerList) {
                    observer.worldChangedAt(p);
                }
            }
        }
    }

    //Get the matching pairs of row and column to first get inside the city,
    // then the surrounding positions in clockwise order, starting from north.
    private void placeUnit(Position position, City city) {
        int[] rows = {0, -1, -1, 0, 1, 1, 1, 0, -1};
        int[] cols = {0, 0, 1, 1, 1, 0, -1, -1, -1};
        for (int i = 0; i < rows.length; i++) {
            int row = rows[i];
            int col = cols[i];
            Position tempPos = new Position(position.getRow() + row, position.getColumn() + col);
            if (isValidPlacement(tempPos)) {
                units.put(tempPos, new UnitImpl(city.getOwner(), city.getProduction()));
                return;
            }
        }
    }

    private boolean isValidPlacement(Position position) {
        Tile t = getTileAt(position);
        boolean validRow = 0 <= position.getRow() && position.getRow() < GameConstants.WORLDSIZE;
        boolean validColumn = 0 <= position.getColumn() && position.getColumn() < GameConstants.WORLDSIZE;
        boolean validPosition = validRow && validColumn;
        if (!validPosition) {
            return false;
        }
        boolean validTile = !t.getTypeString().equals(GameConstants.OCEANS) &&
                !t.getTypeString().equals(GameConstants.MOUNTAINS);
        boolean unitAtPos = getUnitAt(position) != null;
        return validTile && !unitAtPos;
    }
    //----- endOfTurn end -----

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        workForceFocusStrategy.changeWorkForceFocus(this, p, balance);
        for (GameObserver observer : observerList) {
            observer.tileFocusChangedAt(p);
        }
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        CityImpl c = (CityImpl) getCityAt(p);
        c.setProduction(unitType);
        for (GameObserver observer : observerList) {
            observer.tileFocusChangedAt(p);
        }
    }

    public void performUnitActionAt(Position p) {
        boolean isUnitAtTo = getUnitAt(p)!=null;
        if (!isUnitAtTo){
            return;
        }
        Unit unit = getUnitAt(p);
        boolean unitIsOwnedByPlayerInTurn = unit.getOwner().equals(playerInTurn);
        if (unitIsOwnedByPlayerInTurn) {
            actionStrategy.performUnitActionAt(this, p, cities, units, tiles);
            for (GameObserver observer : observerList) {
                observer.worldChangedAt(p);
            }
        }
    }


    public void addObserver(GameObserver observer) {
        observerList.add(observer);
    }

    public void setTileFocus(Position position) {
        tileFocus = position;
        for (GameObserver observer : observerList) {
            observer.tileFocusChangedAt(position);
        }
    }

    @Override
    public Position getTileFocus() {
        return tileFocus;
    }

}
