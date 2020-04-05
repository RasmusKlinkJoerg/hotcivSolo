package hotciv.standard.Stubs;

import hotciv.framework.Die;
import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.StrategyImpls.AlphaActionStrategy;
import hotciv.standard.StrategyImpls.AlphaAgingStrategy;
import hotciv.standard.StrategyImpls.EpsilonAttackStrategy;
import hotciv.standard.StrategyImpls.EpsilonWinningStrategy;

public class StubEpsilonFactory implements GameFactory {
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
        return new StubEpsilonLayout();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new EpsilonAttackStrategy(new StubFixedDie());
    }
}
