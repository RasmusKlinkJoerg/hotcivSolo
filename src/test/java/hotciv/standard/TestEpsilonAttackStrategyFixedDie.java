package hotciv.standard;

import hotciv.framework.Game;
import hotciv.standard.StrategyImpls.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEpsilonAttackStrategyFixedDie {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new AlphaWinningStrategy(), new AlphaAgingStrategy(), new AlphaActionStrategy(), new AlphaLayoutStrategy(), new AlphaAttackStrategy());
    }
}
