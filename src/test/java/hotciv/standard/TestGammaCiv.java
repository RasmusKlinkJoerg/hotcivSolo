package hotciv.standard;


import hotciv.framework.*;


import hotciv.standard.Factories.GammaCivFactory;
import hotciv.standard.StrategyImpls.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestGammaCiv {

    private GameImpl game;

    @Before
    public void setUp() {
        game = new GameImpl(new GammaCivFactory());
    }

    @Test
    public void archerFortifyActionDoublesDefense() {
        Position redArcherP1 = new Position(2,0);
        game.performUnitActionAt(redArcherP1);
        assertThat(game.getUnitAt(redArcherP1).getDefensiveStrength(), is(3*2));
    }

    @Test
    public void archerFortifyActionMakesStationary() {
        Position redArcherP1 = new Position(2,0);
        Position redArcherP2 = new Position(2,1);
        game.performUnitActionAt(redArcherP1);
        assertThat(game.moveUnit(redArcherP1,redArcherP2), is(false));
    }

    @Test
    public void archerCanUndoFortifyAction(){
        Position redArcherP1 = new Position(2,0);
        Position redArcherP2 = new Position(2,1);
        game.performUnitActionAt(redArcherP1);  //Fortify
        game.performUnitActionAt(redArcherP1);  //Undo fortify
        assertThat(game.getUnitAt(redArcherP1).getDefensiveStrength(), is(3));  //check that def is normal
        assertThat(game.moveUnit(redArcherP1,redArcherP2), is(true));
    }

    @Test
    public void settlerActionTransformsSettlerToCity() {
        Position redSettlerP1 = new Position(4,3);
        game.performUnitActionAt(redSettlerP1);
        assertThat(game.getCityAt(redSettlerP1), is(notNullValue()));
    }

}
