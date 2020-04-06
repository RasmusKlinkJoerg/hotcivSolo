package hotciv.framework.Strategies;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.CityImpl;

public interface WorkForceForceFocusStrategy {

    void changeWorkForceFocus(Game game, Position p, String balance);

    void increaseTreasury(Game game, Position p);

    void increaseFoodCount(Game game, Position p);
}
