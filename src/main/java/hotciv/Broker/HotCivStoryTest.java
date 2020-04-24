package hotciv.Broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.Proxies.GameProxy;

public class HotCivStoryTest {
    private static Game gameProxy;


    public static void main(String[] args) {
        HotCivStoryTest hotCivStoryTest = new HotCivStoryTest(args[0]);
        hotCivStoryTest.testSimpleMethods(gameProxy);

    }

    public HotCivStoryTest(String hostname) {
        System.out.println( " === Hotciv manual test client (socket) host: " + hostname + ") ===");

        //set broker part up
        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(hostname, 37123);

        Requestor requestor = new StandardJSONRequestor(crh);

        gameProxy = new GameProxy(requestor);
    }

    private void testSimpleMethods(Game game) {
        System.out.println( "=== Testing simple methods");
        System.out.println(" game age: " + game.getAge());
        System.out.println(" game winner: " + game.getWinner());
        System.out.println(" game player in turn" + game.getPlayerInTurn());
        System.out.println("game move from 0-0 to 1-1 is " + game.moveUnit(new Position(0,0), new Position(1,1)));
        game.endOfTurn();
        System.out.println("Now player in turn after end of turn is " + game.getPlayerInTurn());
    }


}
