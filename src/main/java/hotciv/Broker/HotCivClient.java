package hotciv.Broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.standard.Proxies.GameProxy;
import hotciv.visual.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;


public class HotCivClient {
    private static Game gameProxy;


    public static void main(String[] args){
        new HotCivClient(args[0]);
        DrawingEditor editor =
                new MiniDrawApplication( "Title: Play a game of semiCiv",
                        new HotCivFactory4(gameProxy) );
        editor.open();
        editor.showStatus("Status: Playing semiCiv");

        editor.setTool( new CompositionTool(editor, gameProxy));
    }

    public HotCivClient(String hostname) {
        System.out.println( " === Hotciv client (socket) host: " + hostname + ") ===");

        //set broker part up
        ClientRequestHandler crh = new SocketClientRequestHandler();
        crh.setServer(hostname, 37123);

        Requestor requestor = new StandardJSONRequestor(crh);

        gameProxy = new GameProxy(requestor);
    }

}
