package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.StrategyImpls.*;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestEpsilonCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonWinningStrategy(), new AlphaAgingStrategy(), new AlphaActionStrategy(), new AlphaLayoutStrategy());
    }

    @Test
    public void winnerIsFirstToWin3Attacks() {

        assertThat(game.getWinner(), is(Player.RED));
    }

}
