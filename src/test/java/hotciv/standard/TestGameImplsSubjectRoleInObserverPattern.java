package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.standard.Stubs.StubGameObserver;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGameImplsSubjectRoleInObserverPattern {
    Game game;
    StubGameObserver stubGameObserver;
    ArrayList<String> stubGameObserverTranscript;
    @Before
    public void setUp() {
        game = new GameImpl(new AlphaCivFactory());
        stubGameObserver = new StubGameObserver();
        game.addObserver(stubGameObserver);
        stubGameObserverTranscript = stubGameObserver.getTranscript();
    }

    @Test
    public void worldChangedAtIsCalledWhenMovingUnit() {
        Position redArcherPos1 = new Position(2,0);
        Position redArcherPos2 = new Position(2,1);
        game.moveUnit(redArcherPos1, redArcherPos2);
        assertThat(stubGameObserverTranscript.get(0), containsString("worldChangedAt"));
        assertThat(stubGameObserverTranscript.get(1), containsString("worldChangedAt"));
    }

    @Test
    public void worldChangedAtIsCalledWhenCityProducesUnit() {
        Position redCityPos = new Position(1,1);
        //A city start with production: Legion, and workForceFocus: productionFocus
        makeRedCityProduceLegion();
        assertThat(game.getUnitAt(redCityPos).getTypeString(), is(GameConstants.LEGION));
        String lastElementInTranscript = stubGameObserverTranscript.get(stubGameObserverTranscript.size()-1);
        assertThat(lastElementInTranscript, containsString("worldChangedAt"));
    }

    @Test
    public void turnEndsIsCalledWhenRedTurnEnds() {
        game.endOfTurn();
        assertThat(stubGameObserverTranscript.get(0), containsString("turnEnds"));
        // It should say that it is blue's turn now:
        assertThat(stubGameObserverTranscript.get(0), containsString("blue"));
    }
    @Test
    public void turnEndsIsCalledWhenBlueTurnEnds() {
        game.endOfTurn();
        game.endOfTurn();
        assertThat(stubGameObserverTranscript.get(1), containsString("turnEnds"));
        // It should say that it is red's turn now:
        assertThat(stubGameObserverTranscript.get(1), containsString("red"));
    }

    @Test
    public void tileFocusChangedAtIsCalledCorrectly() {
        game.setTileFocus(new Position(10,4));
        assertThat(stubGameObserverTranscript.get(0), containsString("tileFocusChangedAt"));
    }


    private void makeRedCityProduceLegion() {
        //Legion costs 15, 6 production is used each round
        game.endOfTurn();
        game.endOfTurn(); //total treasury:6
        game.endOfTurn();
        game.endOfTurn();// total treasury: 12
        game.endOfTurn();
        game.endOfTurn();// total treasury: 3, has produced legion
    }
}
