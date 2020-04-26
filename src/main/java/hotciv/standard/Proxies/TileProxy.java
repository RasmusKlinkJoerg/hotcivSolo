package hotciv.standard.Proxies;

import frds.broker.Requestor;
import hotciv.framework.Tile;
import hotciv.standard.OperationNames;

public class TileProxy implements Tile {

    private final String id;
    private final Requestor requestor;

    public TileProxy(String id, Requestor requestor) {
        this.id = id;
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        System.out.println(requestor);
        System.out.println("id: " + getId());
        String type =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_TYPESTRING_TILE,
                        String.class);

        System.out.println("TileProxy - type: " + type);
        return type;
    }

    @Override
    public String getId() {
        return id;
    }
}
