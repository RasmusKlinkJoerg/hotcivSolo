package hotciv.standard.StrategyImpls;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WinningStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class AlphaWinningStrategy implements WinningStrategy {
    @Override
    public Player getWinner(int age, HashMap<Position, CityImpl> cityHashMap, HashMap<Player, Integer> attacksWonMap) {
        if (age==-3000) {
            return  Player.RED;
        }
        return null;
    }
}
