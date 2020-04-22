package hotciv.standard;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;

public class GameProxy implements ClientProxy, Game {

    private final Requestor requestor;

    public GameProxy(Requestor crh) {
        this.requestor = crh;
    }

    public Tile getTileAt(Position p) {
        return null;
    }

    public Unit getUnitAt(Position p) {
        return null;
    }

    public City getCityAt(Position p) {
        return null;
    }

    public Player getPlayerInTurn() {
        return null;
    }

    public Player getWinner() {
        Player winner =
                requestor.sendRequestAndAwaitReply("No ID required ???",
                        OperationNames.GET_WINNER,
                        Player.class);
        return winner;
    }

    public int getAge() {
        return 0;
    }

    public boolean moveUnit(Position from, Position to) {
        return false;
    }

    public void endOfTurn() {

    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    public void changeProductionInCityAt(Position p, String unitType) {

    }

    public void performUnitActionAt(Position p) {

    }

    public void addObserver(GameObserver observer) {

    }

    public void setTileFocus(Position position) {}
    public Position getTileFocus() {return null;}
}
