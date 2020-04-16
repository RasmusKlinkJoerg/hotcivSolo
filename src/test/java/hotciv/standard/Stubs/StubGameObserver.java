package hotciv.standard.Stubs;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.ArrayList;

public class StubGameObserver implements GameObserver {
    private ArrayList<String> transcript = new ArrayList<>();

    @Override
    public void worldChangedAt(Position pos) {
        transcript.add("worldChangedAt(" + pos + ") was called");

    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        transcript.add("turnEnds was called. It is now " + nextPlayer.toString().toLowerCase() + "'s turn and the age is" + age);
    }

    @Override
    public void tileFocusChangedAt(Position position) {
        transcript.add("tileFocusChangedAt was called with position" + position);

    }

    public ArrayList<String> getTranscript() {
        return transcript;
    }

}
