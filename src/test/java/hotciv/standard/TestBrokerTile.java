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

public class TestBrokerTile {
    private Tile tile;
    private Game game;

    @Before
    public void setUp() {
        Game servant = new StubGame3();
        GameObserver nullObserver = new NullGameObserver();
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
