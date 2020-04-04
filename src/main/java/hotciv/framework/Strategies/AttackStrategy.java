package hotciv.framework.Strategies;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;

public interface AttackStrategy {


    boolean attack(Game game, Position from, Position to, Player player);
}
