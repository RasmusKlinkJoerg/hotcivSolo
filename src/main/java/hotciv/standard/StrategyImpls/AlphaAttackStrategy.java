package hotciv.standard.StrategyImpls;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.AttackStrategy;

public class AlphaAttackStrategy implements AttackStrategy {
    @Override
    public boolean attack(Game game, Position from, Position to, Player player) {
        return true;
    }
}
