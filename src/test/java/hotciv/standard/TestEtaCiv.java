package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.Factories.EtaCivFactory;
import hotciv.standard.Stubs.StubEtaCivFactory;
import org.junit.*;

import javax.crypto.spec.GCMParameterSpec;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEtaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new StubEtaCivFactory());
    }

    @Test
    public void canChangeWorkForceFocus() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        assertThat(game.getCityAt(redCityP).getWorkforceFocus(), is(GameConstants.foodFocus));
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.productionFocus);
        assertThat(game.getCityAt(redCityP).getWorkforceFocus(), is(GameConstants.productionFocus));
    }

    @Test
    public void shouldGet1ProductionInCityWithSize1() {
        Position redCityP = new Position(8,12);
        game.endOfTurn();
        game.endOfTurn();
        assertThat(game.getCityAt(redCityP).getSize(), is(1));
        assertThat(game.getCityAt(redCityP).getTreasury(), is(1));
    }

    @Test
    public void shouldGet4ProductionInCityWithSize2NextToForrest() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);  //it is (5+(city size)*3)+1, because it should exceed (5+(city size)*3)
        assertThat(game.getCityAt(redCityP).getSize(), is(2));
        System.out.println(game.getCityAt(redCityP).getTreasury());
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.productionFocus);
        System.out.println(game.getCityAt(redCityP).getTreasury());
        game.endOfTurn();
        System.out.println(game.getCityAt(redCityP).getTreasury());
        game.endOfTurn();
        System.out.println(game.getCityAt(redCityP).getTreasury());

        assertThat(game.getCityAt(redCityP).getTreasury(), is(4));
    }

    @Test
    public void citySizeIncreaseCorrectlyFrom1to2() {
        Position redCityP = new Position(8,12);
        assertThat(game.getCityAt(redCityP).getSize(), is(1));
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);
        assertThat(game.getCityAt(redCityP).getSize(), is(2));
    }

    @Test
    public void foodCountIsResetToZeroAfterCitySizeIncreased() {
        Position redCityP = new Position(8,12);
        assertThat(game.getCityAt(redCityP).getFoodCount(), is(0));
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);  //it is (5+(city size)*3)+1, because it should exceed (5+(city size)*3)
        assertThat(game.getCityAt(redCityP).getFoodCount(), is(0));
    }

    private void endNumberOfRounds(int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }



}
