package hotciv.standard;

import hotciv.framework.*;

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
        game = new GameImpl();
        ((GameImpl) game).createWorld();
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

    @Test
    public void gameStartsInYear4000BC() {
        assertThat(game.getAge(), is(-4000));
    }

    @Test
    public void afterRound1ItisYear3900BC() {
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getAge(), is(-3900));
    }

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
        Position p = new Position(4, 1);
        CityImpl bCity = (CityImpl) game.getCityAt(p);
        bCity.setProduction(GameConstants.ARCHER);
        assertThat(bCity.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void cityLosesTreasuryWhenProducesUnit() {
        for (int i = 0; i < 6; i++) {  // wait 6 turns so treasury is increased to 18
            game.endOfTurn();
        }
        Position p = new Position(4, 1);
        assertThat(game.getCityAt(p).getTreasury(), is(18 - 15));
    }



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
        Position mountainP = new Position(4 ,2);
        assertThat(game.moveUnit(blueLegionP, mountainP), is(false));
    }

    @Test
    public void blueCantMoveRed(){
        Position blueLegionP = new Position(3,2);
        Position mountainP = new Position(4 ,2);
        assertThat(game.moveUnit(blueLegionP, mountainP), is(false));
    }

    @Test
    public void successfulMoveUnitUpdatesUnitMap() {
        Position from = new Position(2,0);
        Position to = new Position(2,1);
        game.moveUnit(from,to);
        assertThat(game.getUnitAt(to), is(notNullValue()));
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
