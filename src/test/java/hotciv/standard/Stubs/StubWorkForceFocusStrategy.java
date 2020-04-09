package hotciv.standard.Stubs;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Strategies.WorkForceForceFocusStrategy;
import hotciv.standard.StrategyImpls.AlphaWorkForceFocusStrategy;
import hotciv.standard.StrategyImpls.EtaWorkForceFocusStrategy;

public class StubWorkForceFocusStrategy implements WorkForceForceFocusStrategy {
    EtaWorkForceFocusStrategy etaWorkForceFocusStrategy = new EtaWorkForceFocusStrategy();
    AlphaWorkForceFocusStrategy alphaWorkForceFocusStrategy = new AlphaWorkForceFocusStrategy();

    @Override
    public void changeWorkForceFocus(Game game, Position p, String balance) {
        etaWorkForceFocusStrategy.changeWorkForceFocus(game, p, balance);

    }

    @Override
    public void increaseTreasury(Game game, Position p) {
        if (game.getCityAt(p).getWorkforceFocus().equals(GameConstants.productionFocus)) {
            alphaWorkForceFocusStrategy.increaseTreasury(game, p);
        }
    }

    @Override
    public void increaseFoodCount(Game game, Position p) {

    }
}
