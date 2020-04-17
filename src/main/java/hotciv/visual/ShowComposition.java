package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.event.*;

import hotciv.framework.*;
import hotciv.stub.*;

import static hotciv.view.GfxConstants.*;
import static hotciv.view.GfxConstants.TURN_SHIELD_Y;

/** Template code for exercise FRS 36.44.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class ShowComposition {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click and/or drag any item to see all game actions",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click and drag any item to see Game's proper response.");

    editor.setTool( new CompositionTool(editor, game) );
  }
}

class CompositionTool extends NullTool {
  private DrawingEditor editor;
  private Game game;
  private Position from;
  private Position to;
  private MoveTool moveTool;
  private SetFocusTool setFocusTool;
  private EndOfTurnTool endOfTurnTool;
  private ActionTool actionTool;
  private ChangeProductionAndWFFTool changeProductionAndWFFTool;


  public CompositionTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
    moveTool = null;
    setFocusTool = new SetFocusTool(editor,game);
    endOfTurnTool = new EndOfTurnTool(editor,game);
    actionTool = new ActionTool(editor, game);
    changeProductionAndWFFTool = new ChangeProductionAndWFFTool(editor,game);
  }

  public void mouseDown(MouseEvent e, int x, int y) {
    setFocusTool.mouseDown(e, x, y);
    from = getPositionFromXY(x,y);
    boolean isUnitAtFrom = game.getUnitAt(from) != null;
    if (isUnitAtFrom) {
      if (e.isShiftDown()) {
        actionTool.mouseDown(e, x, y);
      }
      moveTool = new MoveTool(editor,game);
    }
    boolean clickedOnEndOfTurnShield =TURN_SHIELD_X < x && x < TURN_SHIELD_X + 30 &&
            TURN_SHIELD_Y  < y && y < TURN_SHIELD_Y + 40 ;
    if (clickedOnEndOfTurnShield) {
      endOfTurnTool.mouseDown(e, x, y);
    }
    boolean isCityInTileFocus = game.getCityAt(game.getTileFocus())!=null;
    if (isCityInTileFocus) {
        changeProductionAndWFFTool.mouseDown(e, x, y);
    }

  }

  public void mouseUp(MouseEvent e, int x, int y) {
    to = getPositionFromXY(x,y);
    boolean isUnitAtFrom = game.getUnitAt(from) != null;
    if (moveTool != null && isUnitAtFrom) {
      game.moveUnit(from,to);
    }
  }
}