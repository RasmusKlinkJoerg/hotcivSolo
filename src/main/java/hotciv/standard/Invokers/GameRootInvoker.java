package hotciv.standard.Invokers;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;
import hotciv.standard.NameService;
import hotciv.standard.OperationNames;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class GameRootInvoker implements Invoker {

    private final Game game;
    private final NameService nameService;
    private final Map<String, Invoker> invokerMap;
    private Gson gson;

    public GameRootInvoker(Game game) {
        this.game = game;
        gson = new Gson();

        nameService = new NameService();
        invokerMap = new HashMap<>();

        // Subinvokers
        Invoker gameInvoker = new GameInvoker(game, nameService);
        invokerMap.put(OperationNames.GAME_PREFIX, gameInvoker);

        Invoker cityInvoker = new CityInvoker(nameService);
        invokerMap.put(OperationNames.CITY_PREFIX, cityInvoker);

        Invoker unitInvoker = new UnitInvoker(nameService);
        invokerMap.put(OperationNames.UNIT_PREFIX, unitInvoker);

        Invoker tileInvoker = new TileInvoker(nameService);
        invokerMap.put(OperationNames.TILE_PREFIX, tileInvoker);
    }

    @Override
    public ReplyObject handleRequest(String objectId, String operationName, String payload) {
        ReplyObject reply = null;

        // Identify the invoker to use
        String type = operationName.substring(0, operationName.indexOf("-"));
        Invoker subInvoker = invokerMap.get(type);

        // And do the upcall
        try {
            reply = subInvoker.handleRequest(objectId, operationName, payload);

        } catch (Exception e) {
            reply =
                    new ReplyObject(
                            HttpServletResponse.SC_NOT_FOUND,
                            e.getMessage());
        }

        return reply;
    }
}

