package hotciv.standard;

import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;

public class CityProxy implements City {

    private final Requestor requestor;

    public CityProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public Player getOwner() {
        Player owner =
            requestor.sendRequestAndAwaitReply("No id",
                OperationNames.GET_OWNER_CITY,
                Player.class);
        return owner;
    }

    @Override
    public int getSize() {
        int size =
                requestor.sendRequestAndAwaitReply("No id",
                        OperationNames.GET_SIZE,
                        Integer.class);

        return size;
    }

    @Override
    public int getTreasury() {
        int treasury =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_TREASURY,
                        Integer.class);

        return treasury;
    }

    @Override
    public String getProduction() {
        String production =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_PRODUCTION,
                        String.class);
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        String workforceFocus =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_WORKFORCEFOCUS,
                        String.class);
        return workforceFocus;
    }

    @Override
    public int getFoodCount() {
        int foodCount =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_FOOD_COUNT,
                        Integer.class);
        return foodCount;
    }
}
