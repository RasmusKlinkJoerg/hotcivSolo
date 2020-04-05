package hotciv.standard.Stubs;

import hotciv.framework.GameFactory;
import hotciv.framework.Strategies.*;
import hotciv.standard.StrategyImpls.AlphaActionStrategy;
import hotciv.standard.StrategyImpls.AlphaAgingStrategy;
import hotciv.standard.StrategyImpls.AlphaAttackStrategy;
import hotciv.standard.StrategyImpls.ZetaWinningStrategy;

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
}
