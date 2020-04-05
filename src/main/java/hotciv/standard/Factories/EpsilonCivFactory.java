package hotciv.standard.Factories;

import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.SixSidedDie;
import hotciv.standard.StrategyImpls.*;

public class EpsilonCivFactory implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new EpsilonWinningStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new AlphaAgingStrategy();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return new AlphaActionStrategy();
    }

    @Override
    public LayoutStrategy createLayoutStrategy() {
        return new AlphaLayoutStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new EpsilonAttackStrategy(new SixSidedDie());
    }
}
