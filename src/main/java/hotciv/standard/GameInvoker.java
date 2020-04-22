package hotciv.standard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;
import hotciv.framework.Player;

import javax.servlet.http.HttpServletResponse;

import static hotciv.standard.OperationNames.GET_WINNER;

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

