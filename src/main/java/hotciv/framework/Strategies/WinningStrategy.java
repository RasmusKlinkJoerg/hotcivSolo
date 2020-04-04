package hotciv.framework.Strategies;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public interface WinningStrategy {

    Player getWinner(int age, HashMap<Position, CityImpl> cityHashMap, HashMap<Player, Integer> attacksWonMap);
}
