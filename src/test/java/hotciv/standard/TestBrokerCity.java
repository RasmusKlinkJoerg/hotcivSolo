package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.standard.Proxies.GameProxy;
import hotciv.standard.Invokers.GameRootInvoker;
import hotciv.standard.Stubs.StubGame3;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerCity {
    private City city;
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
        city = game.getCityAt(new Position(0,0));

    }

    @Test
    public void shouldHaveOwner() {
        assertThat(city.getOwner(), is(Player.GREEN));
    }

    @Test
    public void shouldHaveSize() {
        assertThat(city.getSize(), is(42));
    }

    @Test
    public void shouldHaveTreasury() {
        assertThat(city.getTreasury(), is(42));
    }

    @Test
    public void shouldHaveProduction() {
        assertThat(city.getProduction(), is(GameConstants.LEGION));
    }

    @Test
    public void shouldHaveWorkForceFocus() {
        assertThat(city.getWorkforceFocus(), is(GameConstants.productionFocus));
    }

    @Test
    public void shouldHave() {
        assertThat(city.getFoodCount(), is(42));
    }
}
