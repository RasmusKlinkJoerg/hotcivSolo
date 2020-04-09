package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.Factories.ThetaCivFactory;
import hotciv.standard.Stubs.StubThetaCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestThetaCiv {

    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new StubThetaCivFactory());
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



    @Test
    public void B52CanFlyOverEnemyCityWithoutAttacking() {
        Position blueCityPos = new Position(4,1);
        makeBlueCityCreateFoodSoNoUnitIsCreated();
        makeRedCityCreateB52Bomber();
        assertThat(game.getUnitAt(blueCityPos), is(nullValue()));
        makeRedB52BomberGoToBlueCity();
        assertThat(game.getUnitAt(blueCityPos).getTypeString(), is(GameConstants.B52));
        assertThat(game.getCityAt(blueCityPos).getOwner(), is(Player.BLUE));
    }

    @Test
    public void B52ActionCanBombCityBelow() {
        Position blueCityPos = new Position(4,1);
        CityImpl blueCity = (CityImpl) game.getCityAt(blueCityPos);
        blueCity.setSize(2);
        assertThat(game.getCityAt(blueCityPos).getSize(), is(2));
        makeBlueCityCreateFoodSoNoUnitIsCreated();
        makeRedCityCreateB52Bomber();
        makeRedB52BomberGoToBlueCity();
        game.performUnitActionAt(blueCityPos);
        assertThat(game.getCityAt(blueCityPos).getSize(), is(1));
    }

    @Test
    public void whenCitySizeReached0TheCityIsDestroyed() {
        Position blueCityPos = new Position(4,1);
        assertThat(game.getCityAt(blueCityPos).getSize(), is(1));
        makeBlueCityCreateFoodSoNoUnitIsCreated();
        makeRedCityCreateB52Bomber();
        makeRedB52BomberGoToBlueCity();
        game.performUnitActionAt(blueCityPos);
        assertThat(game.getCityAt(blueCityPos), is(nullValue()));
    }

    @Test
    public void B52ActionCanTransformForestIntoPlains() {
        Position forestPos = new Position(2,1);
        makeRedCityCreateB52Bomber();
        makeRedB52BomberGoToForest();
        assertThat(game.getTileAt(forestPos).getTypeString(), is(GameConstants.FOREST));
        game.performUnitActionAt(forestPos);
        assertThat(game.getTileAt(forestPos).getTypeString(), is(GameConstants.PLAINS));
    }

    private void makeRedB52BomberGoToForest() {
        Position redB52Pos = new Position(1,1);
        Position forestPos = new Position(2,1);
        game.moveUnit(redB52Pos,forestPos);
    }

    private void makeBlueCityCreateFoodSoNoUnitIsCreated() {
        Position blueCityPos = new Position(4,1);
        game.changeWorkForceFocusInCityAt(blueCityPos, GameConstants.foodFocus);
    }

    private void makeRedB52BomberGoToBlueCity() {
        //blueCity is at 4,1
        Position redB52Pos = new Position(1,1);
        Position redB52Pos2 = new Position(2,1);
        Position redB52Pos3= new Position(3,1);
        Position redB52Pos4 = new Position(4,1);
        game.moveUnit(redB52Pos, redB52Pos2);
        game.moveUnit(redB52Pos2, redB52Pos3);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redB52Pos3,redB52Pos4);
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
