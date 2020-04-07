package hotciv.standard;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.Factories.SemiCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestSemiCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new SemiCivFactory());
    }

    @Test
    public void hasEtaCivWorkForceStrategy() {
        Position redCityP = new Position(8,12);
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(redCityP).getTreasury(), is(1));
    }
}
