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

public class TestBrokerGame {
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
    }

    @Test
    public void shouldHaveWinner() {
        Player winner = game.getWinner();
        assertThat(winner, is(Player.RED));
    }

    @Test
    public void shouldBeRedPlayerInTurnFirst() {
        Player player = game.getPlayerInTurn();
        assertThat(player, is(Player.RED));
    }

    @Test
    public void shouldBeAbleToEndTurn() {
        //endOfTurn makes it blues turn
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void shouldHaveAge() {
        int age = game.getAge();
        assertThat(age, is(0));
    }

    @Test
    public void shouldBeTrueWhenMoveUnit() {
        boolean move = game.moveUnit(new Position(0,0), new Position(0,0));
        assertThat(move, is(true));
    }

    @Test
    public void shouldBeAbleToCallChangeWorkForceFocusInCityAt() {
        //changeWorkForceFocusInCityAt sets the age to 37 in GameStub3
        game.changeWorkForceFocusInCityAt(new Position(0,0), "");
        assertThat(game.getAge(), is(37));
    }

    @Test
    public void shouldBeAbleToCallChangeProductionInCityAt() {
        //changeProductionInCityAt sets the age to 42 in GameStub3
        game.changeProductionInCityAt(new Position(0,0), "");
        assertThat(game.getAge(), is(42));
    }
    @Test
    public void shouldBeAbleToCallPerformUnitActionAt() {
        //performUnitActionAt sets the age to 101 in GameStub3
        game.performUnitActionAt(new Position(0,0));
        assertThat(game.getAge(), is(69));
    }

    @Test
    public void canGetCityAt() {
        City city = game.getCityAt(new Position(0,0));
        assertThat(city, is(notNullValue()));
    }

    @Test public void canGetUnitAt() {
        Unit unit = game.getUnitAt(new Position(0,0));
        assertThat(unit, is(notNullValue()));
    }

    @Test public void canGetTileAt() {
        Tile tile = game.getTileAt(new Position(0,0));
        assertThat(tile,is(notNullValue()));
    }

}
