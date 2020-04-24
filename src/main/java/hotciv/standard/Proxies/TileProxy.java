package hotciv.standard.Proxies;

import frds.broker.Requestor;
import hotciv.framework.Tile;
import hotciv.standard.OperationNames;

public class TileProxy implements Tile {

    private final Requestor requestor;

    public TileProxy(Requestor requestor) {
    this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String type =
                requestor.sendRequestAndAwaitReply("no id",
                        OperationNames.GET_TYPESTRING_TILE,
                        String.class);
        return type;
    }
}
