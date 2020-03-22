package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.StrategyImpls.AlphaActionStrategy;
import hotciv.standard.StrategyImpls.AlphaAgingStrategy;
import hotciv.standard.StrategyImpls.AlphaWinningStrategy;
import hotciv.standard.StrategyImpls.DeltaLayoutStrategy;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestDeltaCiv {
    Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new AlphaWinningStrategy(), new AlphaAgingStrategy(), new AlphaActionStrategy(), new DeltaLayoutStrategy());
    }

    //Layout: Cities
    @Test
    public void redCityAt8_12() {
        Position redCityP = new Position(8, 12);
        assertThat(game.getCityAt(redCityP).getOwner(), is(Player.RED));
    }

    @Test
    public void blueCityAt4_5() {
        Position blueCityP = new Position(4, 5);
        assertThat(game.getCityAt(blueCityP).getOwner(), is(Player.BLUE));
    }

    //Layout: Units
    @Test
    public void redArcherAt7_4() {
        Position redArcherP = new Position(7,4);
        assertThat(game.getUnitAt(redArcherP).getOwner(), is(Player.RED));
    }

    @Test
    public void blueLegionAt3_5() {
        Position redArcherP = new Position(3,5);
        assertThat(game.getUnitAt(redArcherP).getOwner(), is(Player.BLUE));
    }

    @Test
    public void redSettlerAt5_5() {
        Position redSettlerP = new Position(5,5);
        assertThat(game.getUnitAt(redSettlerP).getOwner(), is(Player.RED));
    }

    //Layout: Tiles
    @Test
    public void mountainAt7_13() {
        Position mountainP = new Position(7,13);
        assertThat(game.getTileAt(mountainP).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void oceanAt0_0() {
        Position oceanP = new Position(0,0);
        assertThat(game.getTileAt(oceanP).getTypeString(), is(GameConstants.OCEANS));
    }


}
