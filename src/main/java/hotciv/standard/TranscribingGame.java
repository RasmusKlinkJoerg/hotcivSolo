package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Position;

import java.util.ArrayList;

public class TranscribingGame extends GameDecorator {
    private Game game;
    private ArrayList<String> transcript;

    public TranscribingGame(Game game) {
        super(game);
        this.game = game;
        transcript = new ArrayList<>();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        boolean successfulMove = super.moveUnit(from,to);
        if (successfulMove) {
            transcript.add("Unit moved from " + from + " to " + to);
        }
        return successfulMove;
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        transcript.add(game.getPlayerInTurn() + " Workforce focus changed in city at Pos " + p + "to " + balance);
        super.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        transcript.add(game.getPlayerInTurn() + " Production focus changed in city at Pos " + p + "to " + unitType);
        super.changeWorkForceFocusInCityAt(p, unitType);
    }

    @Override
    public void endOfTurn() {
        transcript.add(game.getPlayerInTurn() + " ends turn");
        game.endOfTurn();
    }


    public ArrayList<String> getTranscript() {
        return transcript;
    }

}
