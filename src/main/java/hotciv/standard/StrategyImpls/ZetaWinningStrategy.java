package hotciv.standard.StrategyImpls;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WinningStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class ZetaWinningStrategy implements WinningStrategy {
    private WinningStrategy currentState = new BetaWinningStrategy();;

    @Override
    public Player getWinner(int age, HashMap<Position, CityImpl> cityHashMap, HashMap<Player, Integer> attacksWonMap, int roundNumber) {
        if (roundNumber > 20) {
            currentState = new EpsilonWinningStrategy();
        }
        return currentState.getWinner(age, cityHashMap, attacksWonMap, roundNumber);
    }
}
