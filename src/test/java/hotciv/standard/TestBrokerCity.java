package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.standard.Invokers.CityInvoker;
import hotciv.standard.Proxies.CityProxy;
import hotciv.standard.Stubs.StubCity2;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerCity {
    private City city;

    @Before
    public void setUp() {
        City cityServant = new StubCity2();

        Invoker invoker = new CityInvoker(cityServant);

        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        city = new CityProxy(requestor);
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
