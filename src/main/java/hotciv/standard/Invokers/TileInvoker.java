package hotciv.standard.Invokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Tile;
import hotciv.standard.NameService;

import javax.servlet.http.HttpServletResponse;

import static hotciv.standard.OperationNames.GET_TYPESTRING_TILE;

public class TileInvoker implements Invoker {
    private final NameService nameService;
    private final Gson gson;

    public TileInvoker(NameService nameService, Gson gson) {
        this.nameService = nameService;
        this.gson = gson;
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payloadJSONArray) {
        ReplyObject reply = null;

        // Demarshall parameters into a JsonArray
        JsonParser parser = new JsonParser();
        JsonArray array =
                parser.parse(payloadJSONArray).getAsJsonArray();

        System.out.println("TileInvoker");
        Tile tile = nameService.getTile(objectId);
        if (operationName.equals(GET_TYPESTRING_TILE)) {
            String type = tile.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
            System.out.println("________reply = " + reply);
        }

        return reply;
    }
}
