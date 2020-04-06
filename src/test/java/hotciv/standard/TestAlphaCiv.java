package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.standard.StrategyImpls.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Oct 2015 for using Hamcrest matchers
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
public class TestAlphaCiv {
    private Game game;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new AlphaCivFactory());
    }

    // FRS p. 455 states that 'Red is the first player to take a turn'.
    @Test
    public void shouldBeRedAsStartingPlayer() {
        assertThat(game, is(notNullValue()));
        assertThat(game.getPlayerInTurn(), is(Player.RED));
    }

    @Test
    public void secondPlayerIsBlue() {
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    //World
    @Test
    public void redCityAt_1_1() {
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));
    }

    @Test
    public void blueCityAt_4_1() {
        assertThat(game.getCityAt(new Position(4, 1)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void oceanAt_1_0() {
        assertThat(game.getTileAt(new Position(1, 0)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void hillsAt_0_1() {
        assertThat(game.getTileAt(new Position(0, 1)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void mountainAt_2_2() {
        assertThat(game.getTileAt(new Position(2, 2)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void plainsAt_0_0() {
        assertThat(game.getTileAt(new Position(0, 0)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void plainsAt_4_7() {
        assertThat(game.getTileAt(new Position(4, 7)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void plainsAt_15_15() {
        assertThat(game.getTileAt(new Position(15, 15)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void redArcherAt_2_0() {
        Position p = new Position(2, 0);
        assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void blueLegionAt_3_2() {
        Position p = new Position(3, 2);
        assertThat(game.getUnitAt(p).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void redSettlerAt_4_3() {
        Position p = new Position(4, 3);
        assertThat(game.getUnitAt(p).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(p).getTypeString(), is(GameConstants.SETTLER));
    }

    //Aging
    @Test
    public void gameStartsInYear4000BC() {
        assertThat(game.getAge(), is(-4000));
    }

    @Test
    public void afterRound1ItIsYear3900BC() {
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getAge(), is(-3900));
    }

    //City
    @Test
    public void cityHas0TreasuryAtStartOfGame() {
        Position p = new Position(1, 1);
        assertThat(game.getCityAt(p).getTreasury(), is(0));
    }

    @Test
    public void redCityGet6TreasuryAtEndOfTurn() {
        game.endOfTurn();
        game.endOfTurn();
        Position p = new Position(1, 1);
        assertThat(game.getCityAt(p).getTreasury(), is(6));
    }

    @Test
    public void blueCityGet6TreasuryAtEndOfTurn() {
        game.endOfTurn();
        game.endOfTurn();
        Position p = new Position(4, 1);
        assertThat(game.getCityAt(p).getTreasury(), is(6));
    }

    @Test
    public void citySizeAlways1() {
        Position p = new Position(4, 1);
        assertThat(game.getCityAt(p).getSize(), is(1));
    }

    @Test
    public void cityStartsWithLegionUnitProduction() {
        Position p = new Position(4, 1);
        assertThat(game.getCityAt(p).getProduction(), is(GameConstants.LEGION));
    }

    @Test
    public void cityCanChangeUnitProduction() {
        Position bCityPos = new Position(4, 1);
        CityImpl bCity = (CityImpl) game.getCityAt(bCityPos);
        game.changeProductionInCityAt(bCityPos, GameConstants.ARCHER);
        assertThat(bCity.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void cityLosesTreasuryWhenProducesUnit() {
        for (int i = 0; i < 6; i++) {  // wait 6 turns so treasury is increased to 18, (legion costs 15)
            game.endOfTurn();
        }
        Position p = new Position(4, 1);
        assertThat(game.getCityAt(p).getTreasury(), is(18 - 15));
    }

    @Test
    public void cityProducesUnitWhenHasEnoughProduction() {
        Position bCityPos = new Position(4, 1);
        assertThat(game.getUnitAt(bCityPos), is(nullValue())); // No unit to begin with
        for (int i = 0; i < 6; i++) {  // wait 6 turns (3 rounds) so treasury is increased to 18 (legion costs 15)
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(bCityPos), is(notNullValue())); // unit should first be placed in city if there is space
    }

    @Test
    public void producedUnitIsPlacedNorthIfCityIsOccupied() {
        Position northPos = new Position(3, 1);
        for (int i = 0; i < 12; i++) {  // wait 12 turns (6 rounds) so 36 is added to treasury and two legions are produced(legion costs 15)
            game.endOfTurn();
        }
        assertThat(game.getUnitAt(northPos), is(notNullValue())); // unit should first be placed in city if there is space
    }

    @Test
    public void producedUnitIsPlacedClockwise() {
        Position eastPos = new Position(4, 2);
        for (int i = 0; i < 18; i++) {  // wait 18 turns (9 rounds) so 54 is added to treasury and 3 legions are produced(legion costs 15),
            game.endOfTurn();           // there is already a legion at north east, therefore the third produced is placed east.
        }
        assertThat(game.getUnitAt(eastPos), is(notNullValue())); // unit should first be placed in city if there is space
    }

    @Test
    public void producedUnitInNorthWestAfterExcessAmountOfRounds() {
        Position northWestPos = new Position(4, 2);
        for (int i = 0; i < 69420; i++) {  // wait 69420 turns (34710 rounds) so 208260 is added to treasury and 10 (max (if none moved)) legions are produced(legion costs 15),
            game.endOfTurn();           // there is already a legion at north east, therefore the third produced is placed east.
        }
        assertThat(game.getUnitAt(northWestPos), is(notNullValue())); // unit should first be placed in city if there is space
    }

    @Test
    public void unitCannotBeProducedOnOcean() {
        Position oceanPos = new Position(1, 0); //adjacent (west) to red city at (1,1)
        for (int i = 0; i < 69420; i++) {  // wait 69420 turns (34710 rounds) so 208260 is added to treasury and 10 (max (if none moved)) legions are produced(legion costs 15),
            game.endOfTurn();           // there is already a legion at north east, therefore the third produced is placed east.
        }
        assertThat(game.getUnitAt(oceanPos), is(nullValue())); // unit should first be placed in city if there is space
    }

    @Test
    public void unitCannotBeProducedOnMountain() {
        Position mountPos = new Position(2, 2); //adjacent (south east) to red city at (1,1)
        for (int i = 0; i < 69420; i++) {  // wait 69420 turns (34710 rounds) so 208260 is added to treasury and 10 (max (if none moved)) legions are produced(legion costs 15),
            game.endOfTurn();           // there is already a legion at north east, therefore the third produced is placed east.
        }
        assertThat(game.getUnitAt(mountPos), is(nullValue())); // unit should first be placed in city if there is space
    }
    // TODO: Test that no unit can be produced outside the world map.


    //Winning
    @Test
    public void redWinsIn3000BC() {
        for (int i = 0; i < 20; i++) {  // wait 10 turns so year is 3000BC
            game.endOfTurn();
        }
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void noWinnerInStartOfGame() {
        assertThat(game.getWinner(), is(nullValue()));
    }

    //Move unit
    @Test
    public void cantMoveOverOcean() {
        Position redArcherP = new Position(2,0);
        Position oceanP = new Position(1,0);
        assertThat(game.moveUnit(redArcherP, oceanP), is(false));
    }

    @Test
    public void canMoveOverPlains() {
        Position redArcherP = new Position(2,0);
        Position plainP = new Position(3,0);
        assertThat(game.moveUnit(redArcherP, plainP), is(true));
    }

    @Test
    public void cantMoveOverMountain() {
        Position redArcherP1 = new Position(2,0);
        Position redArcherP2 = new Position(2,1);
        Position mountainP = new Position(2,2);
        game.moveUnit(redArcherP1, redArcherP2);
        assertThat(game.moveUnit(redArcherP2, mountainP), is(false));
    }

    @Test
    public void redCantMoveBlue(){
        Position blueLegionP = new Position(3,2);
        Position blueLegionP2 = new Position(3 ,3);
        assertThat(game.moveUnit(blueLegionP, blueLegionP2), is(false));
    }

    @Test
    public void blueCantMoveRed(){
        Position blueLegionP = new Position(3,2);
        Position blueLegionP2 = new Position(3 ,3);
        assertThat(game.moveUnit(blueLegionP, blueLegionP2), is(false));
    }

    @Test
    public void successfulMoveUnitUpdatesUnitMap() {
        Position from = new Position(2,0);
        Position to = new Position(2,1);
        game.moveUnit(from,to);
        assertThat(game.getUnitAt(to), is(notNullValue()));
        assertThat(game.getUnitAt(from), is(nullValue()));
    }

    @Test
    public void cantMoveUnitToOwnUnit() {
        Position redArcherP1 = new Position(2,0);
        Position redArcherP2 = new Position(3,1);
        Position redArcherP3 = new Position(4,2);
        Position redSettlerP = new Position(4,3);

        game.moveUnit(redArcherP1, redArcherP2); //move red archer towards red settler
        game.endOfTurn();
        game.endOfTurn();                        //end the round to refresh move count
        game.moveUnit(redArcherP2, redArcherP3);
        game.endOfTurn();
        game.endOfTurn();

        assertThat(game.moveUnit(redArcherP3, redSettlerP), is(false));
    }

    @Test
    public void canOnlyMoveIfMoveCountIsGreaterThan0() {
        Position redArcherP1 = new Position(2,0);
        Position redArcherP2 = new Position(3,1);
        Position redArcherP3 = new Position(4,2);
        game.moveUnit(redArcherP1, redArcherP2);
        assertThat(game.moveUnit(redArcherP2, redArcherP3), is(false));
    }
/* This is a precondition
    @Test
    public void mustBeUnitAtFrom() {
        Position emptyP = new Position(15,15);
        Position emptyP2 = new Position (14,14);
        assertThat(game.moveUnit(emptyP, emptyP2), is(false));
    }
 */
    @Test
    public void cantMoveMoreThan1Tile() {
        Position redArcherP = new Position(2,0);
        Position redArcherPTooFarAway = new Position(13,13);
        assertThat(game.moveUnit(redArcherP, redArcherPTooFarAway), is(false));
    }

    //Units
    @Test
    public void archerHas4Def() {
        Position redArcherP1 = new Position(2,0);
        assertThat(game.getUnitAt(redArcherP1).getDefensiveStrength(), is(3));
    }

    @Test
    public void legionHas2Def() {
        Position blueLegionP1 = new Position(3,2);
        assertThat(game.getUnitAt(blueLegionP1).getDefensiveStrength(), is(2));
    }

    @Test
    public void settlerHas3Def() {
        Position redSettlerP1 = new Position(4,3);
        assertThat(game.getUnitAt(redSettlerP1).getDefensiveStrength(), is(3));
    }

    @Test
    public void archerHas2Atc() {
        Position redArcherP1 = new Position(2,0);
        assertThat(game.getUnitAt(redArcherP1).getAttackingStrength(), is(2));
    }

    @Test
    public void legionHas4Atc() {
        Position blueLegionP1 = new Position(3,2);
        assertThat(game.getUnitAt(blueLegionP1).getAttackingStrength(), is(4));
    }

    @Test
    public void settlerHas0Atc() {
        Position redSettlerP1 = new Position(4,3);
        assertThat(game.getUnitAt(redSettlerP1).getAttackingStrength(), is(0));
    }

    // Attacking
    @Test
    public void attackingUnitAlwaysWins() {
        Position redSettlerP1 = new Position(4,3);
        Position redSettlerP2 = new Position(3,3);
        Position blueLegionP1 = new Position(3,2);
        game.moveUnit(redSettlerP1, redSettlerP2);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redSettlerP2,blueLegionP1); //The red settler moves to the blue legion and overrides (kills) the blue legion
        assertThat(game.getUnitAt(blueLegionP1).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void takesOverCityWhenAttacks(){
        redTakesOverBlueCityAt4_1();
        assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));
    }

    private void redTakesOverBlueCityAt4_1() {
        Position redSettlerPos1 = new Position(4,3);
        Position redSettlerPos2 = new Position(4,2);
        Position blueCityPos = new Position(4,1);
        game.moveUnit(redSettlerPos1, redSettlerPos2);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redSettlerPos2, blueCityPos);
    }




//______________________________________________________________________

    /**
     * REMOVE ME. Not a test of HotCiv, just an example of what
     * matchers the hamcrest library has...
     */
    @Test
    public void shouldDefinetelyBeRemoved() {
        // Matching null and not null values
        // 'is' require an exact match
        String s = null;
        assertThat(s, is(nullValue()));
        s = "Ok";
        assertThat(s, is(notNullValue()));
        assertThat(s, is("Ok"));

        // If you only validate substrings, use containsString
        assertThat("This is a dummy test", containsString("dummy"));

        // Match contents of Lists
        List<String> l = new ArrayList<String>();
        l.add("Bimse");
        l.add("Bumse");
        // Note - ordering is ignored when matching using hasItems
        assertThat(l, hasItems(new String[]{"Bumse", "Bimse"}));

        // Matchers may be combined, like is-not
        assertThat(l.get(0), is(not("Bumse")));
    }
}
