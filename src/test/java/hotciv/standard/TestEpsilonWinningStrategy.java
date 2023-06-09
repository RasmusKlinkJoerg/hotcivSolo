package hotciv.standard;

import hotciv.framework.*;


import hotciv.framework.Strategies.AttackStrategy;
import hotciv.standard.StrategyImpls.*;

import hotciv.standard.Stubs.StubEpsilonFactory;
import hotciv.standard.Stubs.StubEpsilonLayout;
import hotciv.standard.Stubs.StubFixedDie;
import javafx.geometry.Pos;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestEpsilonWinningStrategy {

    private Game game;

    private EpsilonAttackStrategy attackStrategy;

    @Before
    public void setUp() {
        attackStrategy = new EpsilonAttackStrategy(new StubFixedDie());
        game = new GameImpl(new StubEpsilonFactory());
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
        game.endOfTurn();
        //archerDefense: 3*4  blueSettler1: 2*4  (2 from friendly support)
        game.moveUnit(blueSettler1Pos, redArcherP);
        game.moveUnit(blueSettler2Pos, redArcherP);
        game.moveUnit(blueSettler3Pos, redArcherP);
        assertThat(game.getUnitAt(redArcherP).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(blueSettler1Pos), is(nullValue()));
        assertThat(game.getUnitAt(blueSettler2Pos), is(nullValue()));
        assertThat(game.getUnitAt(blueSettler3Pos), is(nullValue()));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test public void shouldWinAfterTakingOver3Cities() {
        assertThat(game.getWinner(), is(nullValue()));
        redTakesOverBlueCities();
        assertThat(game.getWinner(), is(Player.RED));
    }

    private void redTakesOverBlueCities() {
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
