package hotciv.standard;

import com.google.gson.Gson;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.standard.Invokers.GameInvoker;
import hotciv.standard.Invokers.UnitInvoker;
import hotciv.standard.Proxies.GameProxy;
import hotciv.standard.Proxies.GameRootInvoker;
import hotciv.standard.Proxies.UnitProxy;
import hotciv.standard.Stubs.StubGame3;
import hotciv.standard.Stubs.StubUnit2;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerUnit {
    private Unit unit;
    private Game game;

    @Before
    public void setUp() {
        Game servant = new StubGame3();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new GameRootInvoker(servant);

        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
        game.addObserver(nullObserver);
        unit = game.getUnitAt(new Position(0,0));
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
