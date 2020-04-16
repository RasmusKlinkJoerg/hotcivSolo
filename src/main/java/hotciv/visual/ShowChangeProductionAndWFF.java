package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.stub.StubGame2;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

import static hotciv.view.GfxConstants.*;

/** WFF stands for WorkForceFocus
 */

public class ShowChangeProductionAndWFF {
    public static void main(String[] args) {
        Game game = new StubGame2();
        Position pos_city_red = new Position(10, 10);

        DrawingEditor editor =
                new MiniDrawApplication( "Change Production",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Change production");

        editor.setTool( new ChangeProductionAndWFFTool(editor, game) );

        game.setTileFocus(pos_city_red);
    }
}

class ChangeProductionAndWFFTool extends NullTool {
    private Position position;
    Position tileFocus;
    private DrawingEditor editor;
    private Game game;

    public ChangeProductionAndWFFTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    public void mouseDown(MouseEvent e, int x, int y) {
        tileFocus = game.getTileFocus();
        boolean cityInFocus = game.getCityAt(tileFocus)!=null;
        if (!cityInFocus) {
            return;
        }
        position = getPositionFromXY(x,y);

        changeProduction(x, y);
        changeWorkForceFocus(x, y);
    }

    private void changeWorkForceFocus(int x, int y) {
        String workForceFocus = game.getCityAt(tileFocus).getWorkforceFocus();
        boolean clickedOnCityWorkForceFocus = WORKFORCEFOCUS_X < x && x < WORKFORCEFOCUS_X + 30 &&
                WORKFORCEFOCUS_Y  < y && y < WORKFORCEFOCUS_Y + 40;
        if (clickedOnCityWorkForceFocus && workForceFocus.equals(GameConstants.productionFocus)) {
            game.changeWorkForceFocusInCityAt(tileFocus, GameConstants.foodFocus);
        }
        else if (clickedOnCityWorkForceFocus && workForceFocus.equals(GameConstants.foodFocus)) {
            game.changeWorkForceFocusInCityAt(tileFocus, GameConstants.productionFocus);
        }
    }

    private void changeProduction(int x, int y) {
        String production = game.getCityAt(tileFocus).getProduction();
        boolean clickedOnCityProduction =CITY_PRODUCTION_X < x && x < CITY_PRODUCTION_X + 30 &&
                CITY_PRODUCTION_Y  < y && y < CITY_PRODUCTION_Y + 40 ;
        if (clickedOnCityProduction && production.equals(GameConstants.LEGION)) {
            game.changeProductionInCityAt(tileFocus, GameConstants.ARCHER);
        }
        else if (clickedOnCityProduction && production.equals(GameConstants.ARCHER)) {
            game.changeProductionInCityAt(tileFocus, GameConstants.SETTLER);
        }
        else if (clickedOnCityProduction && production.equals(GameConstants.SETTLER)) {
            game.changeProductionInCityAt(tileFocus, GameConstants.LEGION);
        }
    }
}
