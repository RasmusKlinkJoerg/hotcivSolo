package hotciv.standard.StrategyImpls;

import hotciv.framework.*;
import hotciv.standard.UnitImpl;

import java.util.Iterator;

public class EpsilonAttackStrategy {



    /**
     * get the terrain factor for the attack and defense strength according to the
     * GammaCiv specification
     *
     * @param game
     *          the game the factor should be given for
     * @param position
     *          the position that the factor should be calculated for
     * @return the terrain factor
     */
    public static int getTerrainFactor(Game game, Position position) {
        // cities overrule underlying terrain
        if ( game.getCityAt(position) != null ) { return 3; }
        Tile t = game.getTileAt(position);
        if ( t.getTypeString() == GameConstants.FOREST ||
                t.getTypeString() == GameConstants.HILLS ) {
            return 2;
        }
        return 1;
    }

    /**
     * calculate the additional support a unit at position p owned by a given
     * player gets from friendly units on the given game.
     *
     * @param game
     *          the game to calculate on
     * @param position
     *          the position of the unit whose support is wanted
     * @param player
     *          the player owning the unit at position 'position'
     * @return the support for the unit, +1 for each friendly unit in the 8
     *         neighborhood.
     */
    public static int getFriendlySupport(Game game, Position position,
                                         Player player) {
        int support = 0;
        int []rows = {-1, -1, 0, 1, 1, 1, 0, -1};
        int []cols = { 0, 1, 1, 1, 0, -1, -1, -1};
        for(int i = 0; i< rows.length; i++){
            int row = rows[i];
            int col = cols[i];
        Position p= new Position(position.getRow() + row, position.getColumn() + col);
            if ( game.getUnitAt(p) != null &&
                    game.getUnitAt(p).getOwner() == player ) {
                support++;
            }
        }
        return support;
    }
}
