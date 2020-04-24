package hotciv.standard.Invokers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import org.json.HTTPTokener;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static hotciv.standard.OperationNames.*;

public class UnitInvoker implements Invoker {
    private final Unit unit;
    private final Gson gson;

    public UnitInvoker(Unit unitServant) {
        unit = unitServant;
        gson = new Gson();
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payloadJSONArray) {
        ReplyObject reply = null;

    // Demarshall parameters into a JsonArray
        JsonParser parser = new JsonParser();
        JsonArray array =
                parser.parse(payloadJSONArray).getAsJsonArray();

        switch (operationName) {
            case GET_TYPESTRING_UNIT:
                String type = unit.getTypeString();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(type));
                break;

            case GET_OWNER_UNIT:
                Player owner = unit.getOwner();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));
                break;

            case GET_MOVECOUNT:
                int mc = unit.getMoveCount();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(mc));
                break;

            case GET_DEFENSE:
                int def = unit.getDefensiveStrength();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(def));
                break;

            case GET_ATTACK:
                int atc = unit.getAttackingStrength();
                reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(atc));
                break;
        }

        return reply;
    }
}
