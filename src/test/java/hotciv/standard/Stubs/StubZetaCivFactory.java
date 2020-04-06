package hotciv.standard.Stubs;

import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.StrategyImpls.*;

public class StubZetaCivFactory implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new ZetaWinningStrategy();
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
        return new AlphaAttackStrategy();
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
