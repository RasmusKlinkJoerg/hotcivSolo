package hotciv.framework;

import hotciv.framework.Strategies.*;

public interface GameFactory {

    WinningStrategy createWinningStrategy();

    AgingStrategy createAgingStrategy();

    ActionStrategy createActionStrategy();

    LayoutStrategy createLayoutStrategy();

    AttackStrategy createAttackStrategy();

    WorkForceForceFocusStrategy createWorkForceFocusStrategy();


    PopulationGrowthStrategy createPopulationGrowthStrategy();
}
