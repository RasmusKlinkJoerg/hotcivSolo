package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.Factories.EtaCivFactory;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestTranscribingGame {
    Game decoratee;
    TranscribingGame transcribingGame;
    Game game;
    @Before
    public void setUp() {
        decoratee = new GameImpl(new EtaCivFactory());
        transcribingGame = new TranscribingGame(decoratee);
        game = transcribingGame;
    }

    @Test
    public void endOfTurnIsTranscribed() {
        game.endOfTurn();
        assertThat(transcribingGame.getTranscript().size(), is(1));
    }

    @Test
    public void movingUnitIsTranscribed() {
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(transcribingGame.getTranscript().size(), is(1));
    }

    @Test
    public void canTurnTranscribingOff() {
        game = decoratee;
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(transcribingGame.getTranscript().size(), is(0));
    }

    @Test
    public void canTurnTranscribingOffAndOn() {
        game = decoratee;
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        Position redArcherPos3 = new Position(4,0);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(transcribingGame.getTranscript().size(), is(0));
        game = transcribingGame;
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redArcherPos2, redArcherPos3);
        assertThat(transcribingGame.getTranscript().size(), is(3));
    }

    @Test
    public void changeWorkforceFocusIsTranscribed() {
        Position redCityPos = new Position(1,1);
        game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.foodFocus);
        assertThat(transcribingGame.getTranscript().size(), is(1));
    }

    @Test
    public void changeProductionIsTranscribed() {
        Position redCityPos = new Position(1,1);
        game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
        assertThat(transcribingGame.getTranscript().size(), is(1));
    }

}
