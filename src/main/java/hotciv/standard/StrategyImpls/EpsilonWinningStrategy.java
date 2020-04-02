package hotciv.standard.StrategyImpls;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WinningStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class EpsilonWinningStrategy implements WinningStrategy {
    @Override
    public Player getWinner(int age, HashMap<Position, CityImpl> cityHashMap) {
        return null;
    }
}
