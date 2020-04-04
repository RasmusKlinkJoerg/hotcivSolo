package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.StrategyImpls.*;

import hotciv.standard.Stubs.StubEpsilonLayout;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestEpsilonWinningStrategy {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonWinningStrategy(), new AlphaAgingStrategy(), new AlphaActionStrategy(), new StubEpsilonLayout(), new AlphaAttackStrategy());
    }

    @Test
    public void winnerIsFirstToWin3Attacks() {
        assertThat(game.getWinner(), is(nullValue()));
        Position redLegionP = new Position(0,0);
        Position redLegionP2 = new Position(0,1);
        Position redLegionP3 = new Position(0,2);
        Position redLegionP4 = new Position(0,3);
        game.moveUnit(redLegionP, redLegionP2);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redLegionP2, redLegionP3);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redLegionP3, redLegionP4);
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldNotWinGameAfter3SuccessfulDefenses() {
        Position redArcherP = new Position(3, 3);
        Position blueSettler1Pos = new Position(4, 3);
        Position blueSettler2Pos = new Position(4, 4);
        Position blueSettler3Pos = new Position(3, 4);
        game.moveUnit(blueSettler1Pos, redArcherP);
        game.moveUnit(blueSettler2Pos, redArcherP);
        game.moveUnit(blueSettler3Pos, redArcherP);
        assertThat(game.getWinner(), is(nullValue()));
    }

}
