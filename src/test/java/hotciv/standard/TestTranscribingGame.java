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
    TranscribingGameDecorator transcribingGameDecorator;
    Game game;
    private String dectee;
    private String component;

    @Before
    public void setUp() {
        game = new GameImpl(new EtaCivFactory());
        decoratee = game;
        transcribingGameDecorator = new TranscribingGameDecorator(decoratee);
        game = transcribingGameDecorator;
    }

    /*
    @Test
    public void testStateIsRememberedWhenDecIsTurnedOffAndOn() {
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        Position redArcherPos3 = new Position(4,0);
        Position redArcherPos4 = new Position(5,0);

        decoratee.moveUnit(redArcherPos1, redArcherPos2);

        turnDecoratorOnOff(); //---------turn it on

        assertThat( transcribingGameDecorator.getTranscript().size(), is(0));
        assertThat(decoratee.getUnitAt(redArcherPos2).getTypeString(), is(GameConstants.ARCHER));

        turnDecoratorOnOff(); // ------- turns it off   game = dec

        assertThat(decoratee.getUnitAt(redArcherPos2).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redArcherPos2).getTypeString(), is(GameConstants.ARCHER));

        turnDecoratorOnOff(); // turn it on again   game = new T()

        assertThat( transcribingGameDecorator.getTranscript().size(), is(0));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redArcherPos2, redArcherPos3);

        assertThat( transcribingGameDecorator.getTranscript().size(), is(3));
        assertThat(decoratee.getUnitAt(redArcherPos3).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redArcherPos3).getTypeString(), is(GameConstants.ARCHER));

        turnDecoratorOnOff(); //off   game = dec

        assertThat(decoratee.getUnitAt(redArcherPos3).getTypeString(), is(GameConstants.ARCHER));
        assertThat(game.getUnitAt(redArcherPos3).getTypeString(), is(GameConstants.ARCHER));

        turnDecoratorOnOff(); //-----------on

        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redArcherPos3, redArcherPos4);

        assertThat(decoratee.getUnitAt(redArcherPos4).getTypeString(), is(GameConstants.ARCHER));

        turnDecoratorOnOff(); // --------off
        assertThat(decoratee.getUnitAt(redArcherPos4).getTypeString(), is(GameConstants.ARCHER));

    }

     */


    private void turnDecoratorOnOff() { // like done in FRS p. 317 pdf
        if (game == decoratee) {
            //turn dec on
            decoratee = game; // but remember the component
            game = new TranscribingGameDecorator(game);
        }
        else {
            //turn dec off
            game = decoratee;
        }
    }

    @Test
    public void endOfTurnIsTranscribed() {
        game.endOfTurn();
        assertThat(transcribingGameDecorator.getTranscript().size(), is(1));
    }

    @Test
    public void movingUnitIsTranscribed() {
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(transcribingGameDecorator.getTranscript().size(), is(1));
    }

    @Test
    public void canTurnTranscribingOff() {
        game = decoratee; //turn it off
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(transcribingGameDecorator.getTranscript().size(), is(0));
    }

    @Test
    public void canTurnTranscribingOffAndOn() {
        game = decoratee;  //turn it off
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(3,0);
        Position redArcherPos3 = new Position(4,0);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(transcribingGameDecorator.getTranscript().size(), is(0));
        game = transcribingGameDecorator;
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(redArcherPos2, redArcherPos3);
        assertThat(transcribingGameDecorator.getTranscript().size(), is(3));
    }


    @Test
    public void changeWorkforceFocusIsTranscribed() {
        Position redCityPos = new Position(1,1);
        game.changeWorkForceFocusInCityAt(redCityPos, GameConstants.foodFocus);
        assertThat(transcribingGameDecorator.getTranscript().size(), is(1));
    }

    @Test
    public void changeProductionIsTranscribed() {
        Position redCityPos = new Position(1,1);
        game.changeProductionInCityAt(redCityPos, GameConstants.ARCHER);
        assertThat(transcribingGameDecorator.getTranscript().size(), is(1));
    }



}
