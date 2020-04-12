package hotciv.standard;

import hotciv.framework.*;


import hotciv.standard.Stubs.StubZetaCivFactory;
    import org.junit.*;

    import static org.junit.Assert.*;
    import static org.hamcrest.CoreMatchers.*;

public class TestZetaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new StubZetaCivFactory());
    }

    @Test
    public void shouldBeBetaWinningToBeginWith() {
        assertThat(game.getWinner(), is(nullValue()));
        redTakesOverAllCities();
        assertThat(game.getWinner(), is(Player.RED));
    }

    private void redTakesOverAllCities() {
        Position redLegionP = new Position(0,0);
        Position blueCity1Pos = new Position(1,0);
        Position blueCity2Pos = new Position(2,0);
        Position blueCity3Pos = new Position(3,0);
        game.moveUnit(redLegionP, blueCity1Pos);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(blueCity1Pos, blueCity2Pos);
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(blueCity2Pos, blueCity3Pos);
    }

    @Test
    public void shouldBeEpsilonWinningAfter20Rounds() {
        end20rounds();
        assertThat(game.getWinner(), is(nullValue()));
        redWins3Attacks();
        assertThat(game.getWinner(), is(Player.RED));
    }

    private void redWins3Attacks() {
        Position redLegionP = new Position(0,0);
        Position blueLegionP = new Position(0,1);

        Position redArcherP = new Position(3,3);
        Position blueSettlerP = new Position(4,3);

        Position redSettlerP = new Position(10,4);
        Position blueArcherP = new Position(10,5);

        game.moveUnit(redLegionP, blueLegionP);

        game.moveUnit(redArcherP, blueSettlerP);

        game.moveUnit(redSettlerP, blueArcherP);
    }

    private void end20rounds() {
        for (int i = 0; i < 20; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
    }

}
