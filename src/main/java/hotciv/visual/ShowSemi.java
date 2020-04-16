package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.Factories.SemiCivFactory;
import hotciv.standard.GameImpl;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class ShowSemi {

    public static void main(String[] args) {
        Game game = new GameImpl(new SemiCivFactory());

        DrawingEditor editor =
                new MiniDrawApplication( "Move any unit using the mouse",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Move units to see Game's moveUnit method being called.");

        editor.setTool( new CompositionTool(editor, game));
    }
}

