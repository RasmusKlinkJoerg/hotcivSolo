package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.Factories.AlphaCivFactory;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class TestDecoratorChainWithTranscriptionAndUnitsMovedCount {


    @Test
    public void canAccessUnitCountAndTranscription() {
        Game game = new TranscribingGameDecorator(new CountUnitsMovedGameDecorator(new GameImpl(new AlphaCivFactory())));
        game.moveUnit(new Position(2,0), new Position(2,1));
        assertThat(((TranscribingGameDecorator) game).getTranscript(), is(notNullValue()));
        // check it is printed that one unit has been moved
    }

}
