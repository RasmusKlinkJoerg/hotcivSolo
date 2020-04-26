package hotciv.standard;

import com.google.gson.Gson;
import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.*;
import hotciv.standard.Invokers.GameInvoker;
import hotciv.standard.Invokers.TileInvoker;
import hotciv.standard.Proxies.GameProxy;
import hotciv.standard.Proxies.GameRootInvoker;
import hotciv.standard.Proxies.TileProxy;
import hotciv.standard.Stubs.StubGame3;
import hotciv.standard.Stubs.StubTile;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBrokerTile {
    private Tile tile;
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

        tile = game.getTileAt(new Position(0,0));
    }

    @Test
    public void shouldHaveType() {
        System.out.println("tile: " + tile);
        String type = tile.getTypeString();
        assertThat(type, is(GameConstants.PLAINS));
    }

}
