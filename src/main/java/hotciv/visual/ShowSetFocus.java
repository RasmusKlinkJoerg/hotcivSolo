package hotciv.visual;

import minidraw.standard.*;
import minidraw.framework.*;

import java.awt.event.*;

import hotciv.framework.*;
import hotciv.stub.*;

import static hotciv.view.GfxConstants.getPositionFromXY;

/** Template code for exercise FRS 36.40.

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
public class ShowSetFocus {
  
  public static void main(String[] args) {
    Game game = new StubGame2();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click any tile to set focus",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Click a tile to see Game's setFocus method being called.");

    // TODO: Replace the setting of the tool with your SetFocusTool implementation.
    editor.setTool( new SetFocusTool(editor, game) );
  }
}

class SetFocusTool extends NullTool {
  private DrawingEditor editor;
  private Game game;
  private Position position;

  public SetFocusTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }

  public void mouseDown( MouseEvent e, int x, int y) {
    position = getPositionFromXY(x,y);
    boolean clickedOnGameBoard = x < 500; // The gameboad ends after 500 pixels, and the "info panel" begins
    if (clickedOnGameBoard) {
      game.setTileFocus(position);
    }
  }

}
