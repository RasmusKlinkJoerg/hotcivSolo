package hotciv.standard.Proxies;

import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.standard.OperationNames;

public class CityProxy implements City {

    private final String id;
    private final Requestor requestor;

    public CityProxy(String id, Requestor requestor) {
        this.id = id;
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
        Player owner =
            requestor.sendRequestAndAwaitReply(getId(),
                OperationNames.GET_OWNER_CITY,
                Player.class);
        return owner;
    }

    @Override
    public int getSize() {
        int size =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_SIZE,
                        Integer.class);

        return size;
    }

    @Override
    public int getTreasury() {
        int treasury =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_TREASURY,
                        Integer.class);

        return treasury;
    }

    @Override
    public String getProduction() {
        String production =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_PRODUCTION,
                        String.class);
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        String workforceFocus =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_WORKFORCEFOCUS,
                        String.class);
        return workforceFocus;
    }

    @Override
    public int getFoodCount() {
        int foodCount =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_FOOD_COUNT,
                        Integer.class);
        return foodCount;
    }

    @Override
    public String getId() {
        return id;
    }

}
