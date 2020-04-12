package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.Stubs.StubEtaCivFactory;
import org.junit.*;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEtaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new StubEtaCivFactory());
    }

    //workforce focus
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
        endNumberOfRounds(1);
        assertThat(game.getCityAt(redCityP).getSize(), is(1));
        assertThat(game.getCityAt(redCityP).getTreasury(), is(1));
    }

    @Test
    public void shouldGet4ProductionInCityWithSize2NextToForest() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);  //it is (5+(city size)*3)+1, because it should exceed (5+(city size)*3)
        assertThat(game.getCityAt(redCityP).getSize(), is(2));
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.productionFocus);
        endNumberOfRounds(1);

        assertThat(game.getCityAt(redCityP).getTreasury(), is(4));
    }

    @Test
    public void shouldGet7ProductionInCityWithSize3NextTo2Forests() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);  //it is (5+(city size)*3)+1, because it should exceed (5+(city size)*3)
        //citySize is now 2, so it has to be set to 3.
        int foodNeededToIncreaseFrom2To3 = (5+2*3)+1;
        int foodPerRound = 4;
        endNumberOfRounds(foodNeededToIncreaseFrom2To3/foodPerRound);

        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.productionFocus);
        endNumberOfRounds(1);
        assertThat(game.getCityAt(redCityP).getTreasury(), is(7));
    }

    @Test
    public void shouldGet7FoodInCityWithSize3NextTo2Plains() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);  //it is (5+(city size)*3)+1, because it should exceed (5+(city size)*3)
        //citySize is now 2, so it has to be set to 3.
        int foodNeededToIncreaseFrom2To3 = (5+2*3)+1;
        int foodPerRound = 4;
        endNumberOfRounds(foodNeededToIncreaseFrom2To3/foodPerRound);
        endNumberOfRounds(1);
        assertThat(game.getCityAt(redCityP).getFoodCount(), is(7));
    }

    @Test
    public void cityGetsCorrectFoodFromOcean() {
        Position redCityNearOceanP = new Position(0, 3);
        game.changeWorkForceFocusInCityAt(redCityNearOceanP, GameConstants.foodFocus);
        //wait many rounds to be sure city has size 9
        endNumberOfRounds(12345);
        assertThat(game.getCityAt(redCityNearOceanP).getSize(), is(9));
        int foodCountBefore = game.getCityAt(redCityNearOceanP).getFoodCount();
        endNumberOfRounds(1);
        //foodCount should have increased with 3*2 + 1*1 + 1, because the city has 2 plains, 1 oceans and 1 food from itself
        assertThat(game.getCityAt(redCityNearOceanP).getFoodCount(), is(foodCountBefore+3*2+1+1));
    }

    //City Population
    @Test
    public void citySizeIncreaseCorrectlyFrom1to2() {
        Position redCityP = new Position(8,12);
        assertThat(game.getCityAt(redCityP).getSize(), is(1));
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);
        assertThat(game.getCityAt(redCityP).getSize(), is(2));
    }

    @Test
    public void citySizeIncreaseCorrectlyFrom2to3() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        endNumberOfRounds((5+game.getCityAt(redCityP).getSize()*3)+1);
        //citySize is now 2, it is next to a plain, so it produces 4 food per round, and needs (5+2*3)+1 = 12 food to increase in size
        int foodNeededToIncreaseFrom2To3 = (5+2*3)+1;
        int foodPerRound = 4;
        endNumberOfRounds(foodNeededToIncreaseFrom2To3/foodPerRound);
        assertThat(game.getCityAt(redCityP).getSize(), is(3));
    }

    @Test
    public void citySizeCantExceed9() {
        Position redCityP = new Position(8,12);
        game.changeWorkForceFocusInCityAt(redCityP, GameConstants.foodFocus);
        makeRedCityHaveSize9();
        assertThat(game.getCityAt(redCityP).getSize(), is(9));
        //trying to increase size to 10
        int foodNeededToIncreaseToSize10 = (5+9*3)+1;//=33
        int foodPerRound = 1+3*5;//=16 (there are only 5 surrounding plains and no ocean),  we have to round up, so wait 3 rounds
        endNumberOfRounds(foodNeededToIncreaseToSize10/foodPerRound+1);
        assertThat(game.getCityAt(redCityP).getSize(), is(9));
    }

    private void makeRedCityHaveSize9() {
        int foodNeededToIncreaseToNextSize = (5+1*3)+1;
        int foodPerRound = 1;
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound);
        //citySize is now 2, it is next to a plain, so it produces 4 food per round, and needs (5+2*3)+1 = 12 food to increase in size
        foodNeededToIncreaseToNextSize = (5+2*3)+1;
        foodPerRound = 1+3*1;
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound);
        //from 3 to 4:
        foodNeededToIncreaseToNextSize = (5+3*3)+1;  // = 5+9+1 = 15
        foodPerRound = 1+3*2;  // 7   15/7 = 2,14,  we have to round up so wait 3 rounds
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound + 1);
        //from 4 to 5:
        foodNeededToIncreaseToNextSize = 18;  // =  18
        foodPerRound = 1+3*3; //=10, we have to round up , wait 2 rounds
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound + 1);
        //from 5 to 6:
        foodNeededToIncreaseToNextSize = 21;  // =  21
        foodPerRound = 1+3*4;  // 13    we have to round up so wait 2 rounds
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound + 1);
        //from 6 to 7:
        foodNeededToIncreaseToNextSize = 24;  // =  24
        foodPerRound = 1+3*5;  // 16,  we have to round up so wait 2 rounds
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound + 1);
        //from 7 to 8:
        foodNeededToIncreaseToNextSize = 27;  // =  27
        foodPerRound = 1+3*5;  // 16 (there are only 5 surrounding plains and no ocean),  we have to round up, so wait 2 rounds,
        // to make sure we exceed the required amount
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound + 1);
        //from 8 to 9:
        foodNeededToIncreaseToNextSize = 30;  // =  30
        foodPerRound = 1+3*5;  // 16 (there are only 5 surrounding plains and no ocean),  we have to round up so wait 2 rounds
        endNumberOfRounds(foodNeededToIncreaseToNextSize/foodPerRound + 1);
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
