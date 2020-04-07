package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.Factories.EpsilonCivFactory;
import hotciv.standard.StrategyImpls.*;
import hotciv.standard.Stubs.StubEpsilonFactory;
import hotciv.standard.Stubs.StubEpsilonLayout;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestEpsilonCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new StubEpsilonFactory());
    }


    @Test
    public void settlerWithNoSupportLoseAttackOnLegion() {  // Testing that the proper attackStrategy is used
        Position redSettlerP = new Position(10,4);
        Position blueArcherP = new Position(10,5);
        game.moveUnit(redSettlerP, blueArcherP);
        assertThat(game.getUnitAt(blueArcherP).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redSettlerP), is(nullValue()));
    }

    @Test public void attackAtEdgeOfMapWorkCorrectly() {
        Position redSettlerP = new Position(0,14);
        Position blueArcherP = new Position(0,15);
        //red attack blue: red attack strength=2*4, blue defenseStrength = 3*4, so red loose
        game.moveUnit(redSettlerP, blueArcherP);
        assertThat(game.getUnitAt(blueArcherP).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redSettlerP), is(nullValue()));
    }

    @Test public void shouldBeEpsilonWinningStrategy() {
        assertThat(game.getWinner(), is(nullValue())); // No winner at start of game
        redWinsThreeAttacks();
        assertThat(game.getWinner(), is(Player.RED)); // Red wins three attacks on three units or cities (here it is cities)
    }

    private void redWinsThreeAttacks() {
        Position redLegionP = new Position(0,0);
        Position blueCity1Pos = new Position(1,0);
        Position blueCity2Pos = new Position(2,0);
        Position blueCity3Pos = new Position(3,0);
        game.moveUnit(redLegionP, blueCity1Pos);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(blueCity1Pos, blueCity2Pos);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(blueCity2Pos, blueCity3Pos);
    }

}
