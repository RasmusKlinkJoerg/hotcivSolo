package hotciv.standard.StrategyImpls;

import hotciv.framework.Strategies.AgingStrategy;

public class AlphaAgingStrategy implements AgingStrategy {
    @Override
    public int increaseAge(int age) {
        return age + 100;
    }
}
