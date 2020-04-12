package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.Stubs.StubFractalFactory;
import org.junit.*;


import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestFractalLayout {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new StubFractalFactory());
    }

    @Test
    public void randomMapIsCreated() {
        ArrayList<String> tileTypes = getTypeOfTileAt1_1From25RandomMaps();
        boolean allTilesAreSameType = checkIfAllTilesAreSameType(tileTypes);
        assertThat(allTilesAreSameType, is(false));
    }

    private boolean checkIfAllTilesAreSameType(ArrayList<String> tileTypes) {
        String firstElement = tileTypes.get(0);
        for (String s: tileTypes) {
            if (!s.equals(firstElement)) {

                return false;
            }
        }
        return true;
    }

    private ArrayList<String> getTypeOfTileAt1_1From25RandomMaps() {
        ArrayList<String> tiles = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            game = new GameImpl(new StubFractalFactory());
            tiles.add(game.getTileAt(new Position(1,1)).getTypeString());
        }
        return tiles;
    }
}
