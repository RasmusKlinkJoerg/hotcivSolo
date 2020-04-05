package hotciv.standard.StrategyImpls;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WinningStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class EpsilonWinningStrategy implements WinningStrategy {
    private Player winner;


    @Override
    public Player getWinner(int age, HashMap<Position, CityImpl> cityHashMap, HashMap<Player, Integer> attacksWonMap, int roundNumber) {
        if (attacksWonMap.get(Player.RED) >= 3) {
            winner = Player.RED;
        }
        if (attacksWonMap.get(Player.BLUE) >= 3) {
            winner = Player.BLUE;
        }
        return winner;
    }


}
