package hotciv.standard;


import hotciv.framework.Strategies.AgingStrategy;
import hotciv.standard.StrategyImpls.AlphaAgingStrategy;
import hotciv.standard.StrategyImpls.BetaAgingStrategy;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestBetaAgingStrategy {
    AgingStrategy as;
    int age;

    @Before
    public void setUp() {
        as = new BetaAgingStrategy();
    }


    // Aging
    @Test
    public void AgingIncreases100Between4000BCTo100BC() {
        age = -4000;
        increaseAgeNumberOfTimes((4000-100)/100);
        assertThat(age, is(-100));
    }

    @Test
    public void AgeIsCorrectAroundChrist() {
        age=-100;
        age = as.increaseAge(age);
        assertThat(age, is(-1));
        age = as.increaseAge(age);
        assertThat(age, is(1));
        age = as.increaseAge(age);
        assertThat(age, is(50));
    }

    @Test
    public void AgingIncreases50From50ACTo1750AC() {
        age = 50;
        increaseAgeNumberOfTimes((1750-50)/50);
        assertThat(age, is(1750));
    }

    @Test
    public void AgingIncreases25From1750ACTo1900AC() {
        age=1750;
        increaseAgeNumberOfTimes((1900 - 1750)/25);
        assertThat(age, is(1900));
    }

    @Test
    public void AgingIncreases5From1900ACTo1970AC() {
        age = 1900;
        increaseAgeNumberOfTimes( (1970 - 1900) / 5);
        assertThat(age, is(1970));
    }

    @Test
    public void AgingIncreases5From1970ACTo9001AC() { // Over 9000!!!
        age = 1970;
        increaseAgeNumberOfTimes( (9001 - 1970));
        assertThat(age, is(9001));
    }

    private void increaseAgeNumberOfTimes(int n) {
        for (int i = 0; i < n; i++) {
            age = as.increaseAge(age);
        }
    }

}
