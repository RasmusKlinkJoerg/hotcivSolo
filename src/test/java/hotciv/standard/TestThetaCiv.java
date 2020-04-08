package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.Factories.ThetaCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestThetaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
    }

    @Test
    public void canCreateB52Bomber() {
        Position redCityPos = new Position(1,1);
        makeRedCityCreateB52Bomber();
        assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.B52));
    }

    @Test
    public void B52CanMoveTwoTilesPerTurn() {
        Position B52BomberPos1 = new Position(1,1);
        Position B52BomberPos2 = new Position(1,2);
        Position B52BomberPos3 = new Position(1,3);
        makeRedCityCreateB52Bomber();
        game.moveUnit(B52BomberPos1,B52BomberPos2);
        assertThat(game.moveUnit(B52BomberPos2,B52BomberPos3), is(true));
    }



    private void makeRedCityCreateB52Bomber() {
        Position redCityPos = new Position(1,1);
        game.changeProductionInCityAt(redCityPos, GameConstants.B52);
        //Wait 10 rounds to make 60 production
        for (int i = 0; i < 10; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }




}
