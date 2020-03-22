package hotciv.standard.StrategyImpls;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WinningStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class BetaWinningStrategy implements WinningStrategy {
    private Player winner;

    @Override
    public Player getWinner(int age, HashMap<Position, CityImpl> cityHashMap) {
        winner = null;
        int totalCities = 0;
        int redCities = 0;
        int blueCities = 0;
        for (City c : cityHashMap.values()) {
            totalCities += 1;
            if (c.getOwner() == Player.RED) {
                redCities += 1;
            }
            if (c.getOwner() == Player.BLUE) {
                blueCities += 1;
            }
        }
        if (totalCities == redCities) {
            winner = Player.RED;
        }
        if (totalCities == blueCities) {
            winner = Player.BLUE;
        }
        return winner;
    }
}
