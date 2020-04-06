package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.Factories.EpsilonCivFactory;
import hotciv.standard.StrategyImpls.*;
import hotciv.standard.Stubs.StubEpsilonLayout;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestEpsilonCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonCivFactory());
    }


    @Test
    public void settlerWithNoSupportLoseAttackOnLegion() {  // Testing that the proper attackStrategy is used
        Position redSettlerPos = new Position(4,3);
        Position blueLegionPos = new Position(3, 2);
        game.moveUnit(redSettlerPos, blueLegionPos);
        assertThat(game.getUnitAt(blueLegionPos).getTypeString(), is(GameConstants.LEGION));
        assertThat(game.getUnitAt(redSettlerPos), is(nullValue()));
    }

    @Test public void shouldBeEpsilonWinningStrategy() {
        assertThat(game.getWinner(), is(nullValue())); // No winner at start of game
        redTakesOverBlueCityAt4_1();
        assertThat(game.getWinner(), is(nullValue())); // This shows that it is not BetaWinningStrategy
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

}
