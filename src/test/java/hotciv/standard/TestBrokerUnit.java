package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.GameConstants;
import hotciv.framework.Unit;
import hotciv.framework.Player;
import hotciv.standard.Stubs.StubUnit2;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerUnit {
    private Unit unit;

    @Before
    public void setUp() {
        Unit unitServant = new StubUnit2(GameConstants.SETTLER, Player.GREEN);

        Invoker invoker = new UnitInvoker(unitServant);

        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        unit = new UnitProxy(requestor);
    }

    @Test
    public void shouldHaveTypeString() {
        assertThat(unit.getTypeString(), is(GameConstants.SETTLER));
    }
    @Test
    public void shouldHaveOwner() {
        assertThat(unit.getOwner(), is(Player.GREEN));
    }
    @Test
    public void shouldHaveDefense() {
        assertThat(unit.getDefensiveStrength(), is(17));
    }
    @Test
    public void shouldHaveAttack() {
        assertThat(unit.getAttackingStrength(), is(37));
    }
    @Test
    public void shouldHaveMoveCount() {
        assertThat(unit.getMoveCount(), is(42));
    }
}
