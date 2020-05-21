package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.Position;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.*;

public class CompositionTool extends NullTool {
    private DrawingEditor editor;
    private Game game;
    private Position from;
    private Position to;
    private MoveTool moveTool;
    private SetFocusTool setFocusTool;
    private EndOfTurnTool endOfTurnTool;
    private ActionTool actionTool;
    private ChangeProductionAndWFFTool changeProductionAndWFFTool;
    private boolean isUnitAtFrom;



    public CompositionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
        moveTool = null;
        setFocusTool = new SetFocusTool(editor, game);
        endOfTurnTool = new EndOfTurnTool(editor, game);
        actionTool = new ActionTool(editor, game);
        changeProductionAndWFFTool = new ChangeProductionAndWFFTool(editor, game);
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        from = getPositionFromXY(x, y);
        isUnitAtFrom = game.getUnitAt(from) != null;
        if (isUnitAtFrom) {
            actionTool.mouseDown(e, x, y);
            moveTool = new MoveTool(editor, game);
        }
        setFocusTool.mouseDown(e, x, y);
        endOfTurnTool.mouseDown(e, x, y);
        changeProductionAndWFFTool.mouseDown(e, x, y);

        //refresh botton
        //System.out.println("x: " + x + " y: " + y);
        boolean clickedOnRefreshButton = REFRESH_BUTTON_X < x && x < REFRESH_BUTTON_X+50 &&
                REFRESH_BUTTON_Y < y && y < REFRESH_BUTTON_Y + 20;
        if (clickedOnRefreshButton) {
            editor.drawing().requestUpdate();
            //System.out.println("____________REQuest update");
        }


    }

    public void mouseUp(MouseEvent e, int x, int y) {
        to = getPositionFromXY(x, y);
        isUnitAtFrom = game.getUnitAt(from) != null; //make check again to check if settler became city
        if (moveTool != null && isUnitAtFrom) {
            game.moveUnit(from, to);
        }
    }
}
