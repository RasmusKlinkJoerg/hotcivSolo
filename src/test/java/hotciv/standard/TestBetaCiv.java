package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.Factories.BetaCivFactory;
import hotciv.standard.StrategyImpls.*;


import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestBetaCiv {
    Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new BetaCivFactory());
    }


    //Winning

    @Test
    public void owningAllCitiesWinsTheGame() {
        assertThat(game.getWinner(), is(nullValue())); // No winner at start of game
        redTakesOverBlueCityAt4_1();
        assertThat(game.getWinner(), is(Player.RED));
    }

    private void redTakesOverBlueCityAt4_1() {
        Position redSettlerPos1 = new Position(4,3);
        Position redSettlerPos2 = new Position(4,2);
        Position blueCityPos = new Position(4,1);
        game.moveUnit(redSettlerPos1, redSettlerPos2);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redSettlerPos2, blueCityPos);
    }

    // Aging, integration testing with game
    @Test
    public void AgingIncreases100From4000BCTo100BC() {
        endNumberOfRounds(1);
        assertThat(game.getAge(), is(-3900));
        endNumberOfRounds(38); //end 37 rounds
        assertThat(game.getAge(), is(-100));
    }

    @Test
    public void AgeIsCorrectAroundChrist() {
        endNumberOfRounds(40);
        assertThat(game.getAge(), is(-1));
        endNumberOfRounds(1);
        assertThat(game.getAge(), is(1));
        endNumberOfRounds(1);
        assertThat(game.getAge(), is(50));
    }

    @Test
    public void AgingIncreases50From50ACTo1750AC() {
        endNumberOfRounds(42);
        assertThat(game.getAge(), is(50));
        endNumberOfRounds((1750-50)/50 ); //end right number of rounds
        assertThat(game.getAge(), is(1750));
    }

    @Test
    public void AgingIncreases25From1750ACTo1900AC() {
        endNumberOfRounds(42 + (1750-50)/50 + (1900-1750)/25);
        assertThat(game.getAge(), is(1900));
    }

    @Test
    public void AgingIncreases5From1900ACTo1970AC() {
        endNumberOfRounds(42 + (1750-50)/50 + (1900-1750)/25 + (1970-1900)/5);
        assertThat(game.getAge(), is(1970));
    }

    @Test
    public void AgingIncreases5From1970ACTo9001AC() { // Over 9000!!!
        endNumberOfRounds(42 + (1750-50)/50 + (1900-1750)/25 + (1970-1900)/5 + (9001 - 1970));
        assertThat(game.getAge(), is(9001));
    }


    private void endNumberOfRounds(int n) {
        for (int i = 0; i < n*2; i++) {
            game.endOfTurn();
        }
    }


}
