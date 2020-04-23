package hotciv.standard;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;

public class GameProxy implements ClientProxy, Game {

    private final Requestor requestor;

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;
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
        Player playerInTurn =
                    requestor.sendRequestAndAwaitReply("No id required??",
                            OperationNames.GET_PLAYER_IN_TURN,
                            Player.class);
        return playerInTurn;
    }

    public Player getWinner() {
        Player winner =
                requestor.sendRequestAndAwaitReply("No ID required ???",
                        OperationNames.GET_WINNER,
                        Player.class);
        return winner;
    }

    public int getAge() {
        int age =
                requestor.sendRequestAndAwaitReply("No id",
                        OperationNames.GET_AGE,
                        Integer.class);
        return age;
    }

    public boolean moveUnit(Position from, Position to) {
        boolean move =
                requestor.sendRequestAndAwaitReply("No id",
                        OperationNames.MOVE_UNIT,
                        Boolean.class, from, to);

        return move;
    }

    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.END_OF_TURN, null);
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.CHANGE_WORKFORCEFOCUS, null, p, balance);
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.CHANGE_PRODUCTION, null, p, unitType);
    }

    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.PERFORM_UNIT_ACTION, null, p);

    }

    public void addObserver(GameObserver observer) {

    }

    public void setTileFocus(Position position) {}
    public Position getTileFocus() {return null;}
}
