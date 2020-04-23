package hotciv.standard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;

import javax.servlet.http.HttpServletResponse;

import static hotciv.standard.OperationNames.*;

public class GameInvoker implements Invoker {
    private final Game game;
    private final Gson gson;

    public GameInvoker(Game servant) {
        game = servant;
        gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payloadJSONArray) {
        ReplyObject reply = null;

        // Demarshall parameters into a JsonArray
        JsonParser parser = new JsonParser();
        JsonArray array =
                parser.parse(payloadJSONArray).getAsJsonArray();

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

