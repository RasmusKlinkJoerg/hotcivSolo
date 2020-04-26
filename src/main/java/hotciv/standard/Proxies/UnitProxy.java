package hotciv.standard.Proxies;

import frds.broker.Requestor;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.standard.OperationNames;

public class UnitProxy implements Unit {

    private final String id;
    private final Requestor requestor;

    public UnitProxy(String id, Requestor requestor) {
        this.id = id;
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String production =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_TYPESTRING_UNIT,
                        String.class);
        return production;
    }

    @Override
    public Player getOwner() {
        Player owner =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_OWNER_UNIT,
                        Player.class);
        return owner;
    }

    @Override
    public int getMoveCount() {
        int mc =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_MOVECOUNT,
                        Integer.class);
        return mc;
    }

    @Override
    public int getDefensiveStrength() {
        int def =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_DEFENSE,
                        Integer.class);
        return def;
    }

    @Override
    public int getAttackingStrength() {
        int atc =
                requestor.sendRequestAndAwaitReply(getId(),
                        OperationNames.GET_ATTACK,
                        Integer.class);
        return atc;
    }

    @Override
    public String getId() {
        return id;
    }
}
