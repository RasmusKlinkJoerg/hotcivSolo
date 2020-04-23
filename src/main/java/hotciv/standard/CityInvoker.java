package hotciv.standard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.Player;
import javafx.scene.chart.ScatterChart;

import javax.servlet.http.HttpServletResponse;

import static hotciv.standard.OperationNames.*;

public class CityInvoker implements Invoker {
    private final City city;
    private final Gson gson;

    public CityInvoker(City cityServant) {
        city = cityServant;
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
                case GET_OWNER_CITY:
                    Player owner = city.getOwner();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));
                    break;

                case GET_SIZE:
                    int size = city.getSize();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(size));
                    break;

                case GET_TREASURY:
                    int treasury = city.getTreasury();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(treasury));
                    break;

                case GET_PRODUCTION:
                    String production = city.getProduction();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(production));
                    break;

                case GET_WORKFORCEFOCUS:
                    String workForceFocus = city.getWorkforceFocus();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(workForceFocus));
                    break;

                case GET_FOOD_COUNT:
                    int foodCount = city.getFoodCount();
                    reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(foodCount));
                    break;
            }
        } catch (Exception e) {
            reply =
                    new ReplyObject(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            e.getMessage());
        }




        return reply;
    }
}
