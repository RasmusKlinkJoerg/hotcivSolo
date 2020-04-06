package hotciv.standard.Factories;

import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.StrategyImpls.*;

public class EtaCivFactory implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new AlphaWinningStrategy();
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
        return new AlphaAttackStrategy();
    }

    @Override
    public WorkForceForceFocusStrategy createWorkForceFocusStrategy() {
        return new EtaWorkForceFocusStrategy();
    }

    @Override
    public PopulationGrowthStrategy createPopulationGrowthStrategy() {
        return new EtaPopulationGrowthStrategy();
    }
}
