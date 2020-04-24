package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Tile;
import hotciv.framework.Unit;
import hotciv.standard.Stubs.StubTile;
import hotciv.standard.Stubs.StubUnit2;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerTile {
    private Tile tile;

    @Before
    public void setUp() {
        Tile tileServant = new StubTile(GameConstants.PLAINS, 0,0);

        Invoker invoker = new TileInvoker(tileServant);

        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        tile = new TileProxy(requestor);
    }

    @Test
    public void shouldHaveType() {
        assertThat(tile.getTypeString(), is(GameConstants.PLAINS));
    }

}
