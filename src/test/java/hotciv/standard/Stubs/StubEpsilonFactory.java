package hotciv.standard.Stubs;

import hotciv.framework.Die;
import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.StrategyImpls.*;

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

    @Override
    public WorkForceForceFocusStrategy createWorkForceFocusStrategy() {
        return new AlphaWorkForceFocusStrategy();
    }

    @Override
    public PopulationGrowthStrategy createPopulationGrowthStrategy() {
        return new AlphaPopulationGrowthStrategy();
    }
}
