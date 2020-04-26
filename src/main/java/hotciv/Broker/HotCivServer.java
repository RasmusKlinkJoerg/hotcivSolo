package hotciv.Broker;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.framework.Game;
import hotciv.standard.Invokers.GameInvoker;
import hotciv.standard.NameService;
import hotciv.stub.StubGame4;


public class HotCivServer {

    public static void main(String[] args) {
        new HotCivServer(); // No error handling!
    }

    public HotCivServer() {
        int port = 37123;

        Game gameServant = new StubGame4();
        NameService nameService = new NameService();
        Gson gson = new Gson();
        Invoker invoker = new GameInvoker(gameServant, nameService,gson);

        // Configure a socket based server request handler
        SocketServerRequestHandler ssrh =
                new SocketServerRequestHandler();
        ssrh.setPortAndInvoker(port, invoker);

        // Welcome
        // Welcome
        System.out.println("=== HotCiv Socket based Server Request Handler (port:"
                + port + ") ===");
        System.out.println(" Use ctrl-c to terminate!");
        ssrh.start();
    }
}
