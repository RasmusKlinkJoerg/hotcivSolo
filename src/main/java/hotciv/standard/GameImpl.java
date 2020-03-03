package hotciv.standard;

import hotciv.framework.*;

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
    private HashMap<Position, CityImpl> cityHashMap;
    private HashMap<Position, Tile> tileHashMap;
    private HashMap<Position, Unit> unitHashMap;
    private int age;
    private Player winner;

    public GameImpl() {
        cityHashMap = new HashMap<>();
        tileHashMap = new HashMap<>();
        unitHashMap = new HashMap<>();
        age = -4000;
        winner = null;
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
        if (age==-3000) {
            winner = Player.RED;
        }
        return winner;
    }

    public int getAge() {
        return age;
    }

    public boolean moveUnit(Position from, Position to) {
        boolean toOcean = tileHashMap.get(to).getTypeString() == GameConstants.OCEANS;
        boolean toMountain = tileHashMap.get(to).getTypeString() == GameConstants.MOUNTAINS;
        boolean movingOwnUnit = getUnitAt(from).getOwner() == getPlayerInTurn();
        if ( toOcean || toMountain  || !movingOwnUnit) {
            return false;
        }
        unitHashMap.put(to, getUnitAt(from));
        unitHashMap.put(from, null);
        return true;
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
        age += 100;
        cityActions();
    }

    private void cityActions() {
        for (CityImpl c : cityHashMap.values()) {
            c.increaseTreasury(6);
            if (c.getTreasury() > c.getCurrentUnitPrice()){
                c.decreaseTreasury(c.getCurrentUnitPrice());
            }
        }
    }


    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
    }

    public void performUnitActionAt(Position p) {
    }


    private void putCity(Position p, Player owner) {
        cityHashMap.put(p, new CityImpl(owner));
    }

    private void putTile(Position p, String tileType) {
        tileHashMap.put(p, new TileImpl(tileType));
    }

    private void putUnit(Position p, Player owner, String unitType) {
        unitHashMap.put(p, new UnitImpl(owner, unitType));
    }


    public void createWorld() {
        //Cities
        putCity(new Position(1, 1), Player.RED);
        putCity(new Position(4, 1), Player.BLUE);
        //Tiles
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                putTile(new Position(i, j), GameConstants.PLAINS);
            }
        }
        putTile(new Position(1, 0), GameConstants.OCEANS);
        putTile(new Position(0, 1), GameConstants.HILLS);
        putTile(new Position(2, 2), GameConstants.MOUNTAINS);
        //Units
        putUnit(new Position(2, 0), Player.RED, GameConstants.ARCHER);
        putUnit(new Position(3, 2), Player.BLUE, GameConstants.LEGION);
        putUnit(new Position(4, 3), Player.RED, GameConstants.SETTLER);

    }


}
