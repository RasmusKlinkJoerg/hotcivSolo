package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.StrategyImpls.*;
import hotciv.standard.Stubs.StubEpsilonLayout;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestEpsilonCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonWinningStrategy(), new AlphaAgingStrategy(), new AlphaActionStrategy(), new StubEpsilonLayout(), new EpsilonAttackStrategy(new randomDie()));
    }

    @Test
    public void settlerWithNoSupportLoseAttackOnArcher() {
        Position redSettlerPos = new Position(12,4);
        Position blueArcherPos = new Position(12, 5);
        game.moveUnit(redSettlerPos, blueArcherPos);
        assertThat(game.getUnitAt(blueArcherPos).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redSettlerPos), is(nullValue()));
    }

}
