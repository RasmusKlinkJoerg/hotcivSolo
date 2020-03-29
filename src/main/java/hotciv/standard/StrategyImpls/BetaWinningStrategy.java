package hotciv.standard.StrategyImpls;

import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WinningStrategy;
import hotciv.standard.CityImpl;

import java.util.HashMap;

public class BetaWinningStrategy implements WinningStrategy {
    private int totalCities;
    private int redCities;
    private int blueCities;


    @Override
    public Player getWinner(int age, HashMap<Position, CityImpl> cities) {
        countCities(cities);
        return determineWinner();
    }

    private void countCities(HashMap<Position, CityImpl> cities) {
        totalCities = 0;
        redCities = 0;
        blueCities = 0;
        for (City c : cities.values()) {
            totalCities += 1;
            if (c.getOwner() == Player.RED) {
                redCities += 1;
            }
            if (c.getOwner() == Player.BLUE) {
                blueCities += 1;
            }
        }
    }

    private Player determineWinner() {
        Player winner = null;
        if (totalCities == redCities) {
            winner = Player.RED;
        }
        if (totalCities == blueCities) {
            winner = Player.BLUE;
        }
        return winner;
    }
}
