package hotciv.standard.StrategyImpls;

import hotciv.framework.*;
import hotciv.framework.Strategies.AttackStrategy;

public class EpsilonAttackStrategy implements AttackStrategy {
    private Die die;

    public EpsilonAttackStrategy(Die die) {
        this.die = die;
    }

    @Override
    public boolean attack(Game game, Position from, Position to, Player player) {
        Player attackingPlayer = player;
        Player defendingPlayer;
        if (attackingPlayer == Player.RED) {
            defendingPlayer = Player.BLUE;
        }
        else defendingPlayer = Player.RED;
        return getCombinedAttackStrength(game, from, attackingPlayer) > getCombinedDefenseStrength(game, to, defendingPlayer);
    }

    public int getCombinedAttackStrength(Game game, Position position, Player player) {
        int sum;
            Unit unit =  game.getUnitAt(position);
            sum = unit.getAttackingStrength();
            sum += getFriendlySupport(game, position, player);
            sum *= getTerrainFactor(game, position);
            sum *= die.getDieFactor();
        return sum;
    }

    public int getCombinedDefenseStrength(Game game, Position position, Player player) {
        int sum;
        Unit unit =  game.getUnitAt(position);
        sum = unit.getDefensiveStrength();
        sum += getFriendlySupport(game, position, player);
        sum *= getTerrainFactor(game, position);
        sum *= die.getDieFactor();
        return sum;
    }

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
    public int getTerrainFactor(Game game, Position position) {
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
    public int getFriendlySupport(Game game, Position position,
                                         Player player) {
        int support = 0;
        int []rows = {-1, -1, 0, 1, 1, 1, 0, -1};
        int []cols = { 0, 1, 1, 1, 0, -1, -1, -1};
        for(int i = 0; i< rows.length; i++){
            int row = rows[i];
            int col = cols[i];
        Position p = new Position(position.getRow() + row, position.getColumn() + col);
            if (game.getUnitAt(p) != null &&
                    game.getUnitAt(p).getOwner() == player ) {
                support++;
            }
        }
        return support;
    }


}
