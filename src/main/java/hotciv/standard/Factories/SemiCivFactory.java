package hotciv.standard.Factories;

import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.SixSidedDie;
import hotciv.standard.StrategyImpls.*;

public class SemiCivFactory implements GameFactory {
    @Override
    public WinningStrategy createWinningStrategy() {
        return new EpsilonWinningStrategy();
    }

    @Override
    public AgingStrategy createAgingStrategy() {
        return new BetaAgingStrategy();
    }

    @Override
    public ActionStrategy createActionStrategy() {
        return new GammaActionStrategy();
    }

    @Override
    public LayoutStrategy createLayoutStrategy() {
        return new DeltaLayoutStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new EpsilonAttackStrategy(new SixSidedDie());
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
