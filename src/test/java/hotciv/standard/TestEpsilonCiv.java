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

    /*@Test
    public void settlerWithNoSupportLoseAttackOnArcher() {
        Position redSettlerPos = new Position(10,4);
        Position blueArcherPos = new Position(10, 5);
        game.moveUnit(redSettlerPos, blueArcherPos);
        assertThat(game.getUnitAt(blueArcherPos).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redSettlerPos), is(nullValue()));
    }

     */

}
