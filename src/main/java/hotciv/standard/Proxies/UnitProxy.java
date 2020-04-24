package hotciv.standard.Proxies;

import frds.broker.Requestor;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.standard.OperationNames;

public class UnitProxy implements Unit {

    private final Requestor requestor;

    public UnitProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String production =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_TYPESTRING_UNIT,
                        String.class);
        return production;
    }

    @Override
    public Player getOwner() {
        Player owner =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_OWNER_UNIT,
                        Player.class);
        return owner;
    }

    @Override
    public int getMoveCount() {
        int mc =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_MOVECOUNT,
                        Integer.class);
        return mc;
    }

    @Override
    public int getDefensiveStrength() {
        int def =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_DEFENSE,
                        Integer.class);
        return def;
    }

    @Override
    public int getAttackingStrength() {
        int atc =
                requestor.sendRequestAndAwaitReply("",
                        OperationNames.GET_ATTACK,
                        Integer.class);
        return atc;
    }
}
