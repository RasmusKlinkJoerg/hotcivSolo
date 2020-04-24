package hotciv.standard.Invokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Tile;

import javax.servlet.http.HttpServletResponse;

import static hotciv.standard.OperationNames.GET_TYPESTRING_TILE;

public class TileInvoker implements Invoker {
    private final Tile tile;
    private final Gson gson;

    public TileInvoker(Tile tileServant) {
        tile = tileServant;
        gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payloadJSONArray) {
        ReplyObject reply = null;

        // Demarshall parameters into a JsonArray
        JsonParser parser = new JsonParser();
        JsonArray array =
                parser.parse(payloadJSONArray).getAsJsonArray();

        if (operationName.equals(GET_TYPESTRING_TILE)) {
            String type = tile.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
        }

        return reply;
    }
}
