package hotciv.standard.Invokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.*;
import hotciv.standard.NameService;

import javax.servlet.http.HttpServletResponse;

import static hotciv.standard.OperationNames.*;

public class GameInvoker implements Invoker {
    private final Game game;
    private final Gson gson;
    private final NameService nameService;

    public GameInvoker(Game servant, NameService nameService, Gson gson) {
        game = servant;
        this.nameService = nameService;
        this.gson = gson;
        System.out.println("created gameInvoker.");

    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payloadJSONArray) {
        ReplyObject reply = null;

        // Demarshall parameters into a JsonArray
        JsonParser parser = new JsonParser();
        JsonArray array =
                parser.parse(payloadJSONArray).getAsJsonArray();
        System.out.println("in gameInvoker");
        try {
            switch (operationName) {
                case GET_WINNER:
                    Player winner = game.getWinner();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(winner));
                    break;

                case GET_PLAYER_IN_TURN:
                    Player playerInTurn = game.getPlayerInTurn();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(playerInTurn));
                    break;

                case END_OF_TURN:
                    game.endOfTurn();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("end of turn called"));
                    break;

                case GET_AGE:
                    int age = game.getAge();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));
                    break;

                case MOVE_UNIT:
                    Position from = gson.fromJson(array.get(0), Position.class);
                    Position to = gson.fromJson(array.get(1), Position.class);
                    boolean move = game.moveUnit(from, to);
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(move));
                    break;

                case CHANGE_WORKFORCEFOCUS:
                    Position p = gson.fromJson(array.get(0), Position.class);
                    String balance = gson.fromJson(array.get(1), String.class);
                    game.changeWorkForceFocusInCityAt(p, balance);
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("change workforce focus called"));
                    break;

                case CHANGE_PRODUCTION:
                    p = gson.fromJson(array.get(0), Position.class);
                    String unitType = gson.fromJson(array.get(1), String.class);
                    game.changeProductionInCityAt(p, unitType);
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("change production called"));
                    break;

                case PERFORM_UNIT_ACTION:
                    p = gson.fromJson(array.get(0), Position.class);
                    game.performUnitActionAt(p);
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("performUnitActionAt called"));
                    break;

                case GET_CITY:
                    p = gson.fromJson(array.get(0), Position.class);
                    //System.out.println("The pos is " + p);
                    if (game.getCityAt(p) != null) {

                        City city = game.getCityAt(p);
                        String id = city.getId();
                        nameService.putCity(id, city);
                        reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
                    } else {
                        reply = new ReplyObject(HttpServletResponse.SC_OK, null);

                    }
                    break;

                case GET_UNIT:
                    p = gson.fromJson(array.get(0), Position.class);
                    if (game.getUnitAt(p) != null) {
                        Unit unit = game.getUnitAt(p);
                        String id = unit.getId();
                        nameService.putUnit(id, unit);
                        reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
                    } else {
                        reply = new ReplyObject(HttpServletResponse.SC_OK, null);

                    }
                    break;

                case GET_TILE:
                    System.out.println("In GameInvoker in case get tile at");
                    p = gson.fromJson(array.get(0), Position.class);
                    Tile tile = game.getTileAt(p);
                    String id = tile.getId();
                    nameService.putTile(id, tile);
                    reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
                    break;
            }

        } catch( Exception e ) {
            reply =
                    new ReplyObject(
                            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            e.getMessage());
        }


        return reply;
    }
}

