package hotciv.standard.Proxies;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.OperationNames;

import java.util.ArrayList;

public class GameProxy implements ClientProxy, Game {

    private final Requestor requestor;

    private ArrayList<GameObserver> observerList;
    private Position tileFocus;

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;

        observerList = new ArrayList<>();

    }

    public Tile getTileAt(Position p) {
        Tile tile = null;
        System.out.println("GameProxy --- getTileAt Pos " + p);
        String id =
                requestor.sendRequestAndAwaitReply("none",
                        OperationNames.GET_TILE,
                        String.class, p);
        System.out.println("GameProxy --- The tile has id " + id);
        if (id != null) {
            tile = new TileProxy(id, requestor);
        }
        return tile;
    }

    public Unit getUnitAt(Position p) {
        Unit unit = null;
        System.out.println("GameProxy --- getUnitAt Pos " + p);
        String id = requestor.sendRequestAndAwaitReply("none",
                OperationNames.GET_UNIT,
                String.class, p);
        if (id != null) {
            unit = new UnitProxy(id, requestor);
        }
        return unit;
    }

    public City getCityAt(Position p) {
        City city = null;
        //System.out.println("In GameProxy calling getCityAt position Row " + p.getRow() + " col " + p.getColumn());
        String id =
                requestor.sendRequestAndAwaitReply("none",
                        OperationNames.GET_CITY,
                        String.class, p);
        if (id != null) {
            city = new CityProxy(id, requestor);
        }
        return city;
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

        for (GameObserver observer : observerList) {
            observer.worldChangedAt(from);
            observer.worldChangedAt(to);
        }

        return move;
    }

    public void endOfTurn() {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.END_OF_TURN, null);

        for (GameObserver g : observerList) {
            if (getPlayerInTurn() == Player.RED) {
                g.turnEnds(Player.RED, getAge());
            } else g.turnEnds(Player.BLUE, getAge());
        }
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.CHANGE_WORKFORCEFOCUS, null, p, balance);
        for (GameObserver g : observerList) {
                g.tileFocusChangedAt(p);
        }
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.CHANGE_PRODUCTION, null, p, unitType);
        for (GameObserver g : observerList) {
            g.tileFocusChangedAt(p);
        }
    }

    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply("No id", OperationNames.PERFORM_UNIT_ACTION, null, p);
        for (GameObserver observer : observerList) {
            observer.worldChangedAt(p);
        }
    }

    public void addObserver(GameObserver observer) {
        observerList.add(observer);
    }

    public void setTileFocus(Position position) {
        tileFocus = position;
        for (GameObserver g : observerList) {
            g.tileFocusChangedAt(position);
        }
    }

    public Position getTileFocus() {
        return tileFocus;
    }
}
