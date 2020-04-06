package hotciv.standard.StrategyImpls;

import hotciv.framework.Strategies.PopulationGrowthStrategy;
import hotciv.standard.CityImpl;

public class EtaPopulationGrowthStrategy implements PopulationGrowthStrategy {
    @Override
    public void increaseCitySize(CityImpl c) {
        boolean isEnoughFoodToIncreaseSize = c.getFoodCount() > (5 + (c.getSize()) * 3);
        if (isEnoughFoodToIncreaseSize) {
            int currentSize = c.getSize();
            c.setSize(currentSize + 1);
            c.setFoodCount(0);
        }
    }
}
