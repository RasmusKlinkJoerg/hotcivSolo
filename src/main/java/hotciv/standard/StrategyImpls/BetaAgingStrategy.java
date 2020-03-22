package hotciv.standard.StrategyImpls;

import hotciv.framework.Strategies.AgingStrategy;

public class BetaAgingStrategy implements AgingStrategy {
    @Override
    public int increaseAge(int age) {
        if (age < -100) {
            age += 100;
        } else if (age == -100) {
            age = -1;
        }
        else if (age == -1) {
            age = 1;
        }
        else if (age == 1) {
            age = 50;
        }
        else if (age < 1750) {
            age += 50;
        }
        else if (age < 1900) {
            age += 25;
        }
        else if (age < 1970) {
            age += 5;
        }
        else{
            age += 1;
        }
        return age;
    }
}
