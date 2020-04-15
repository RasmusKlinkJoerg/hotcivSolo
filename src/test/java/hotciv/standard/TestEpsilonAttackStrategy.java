package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.StrategyImpls.EpsilonAttackStrategy;
import hotciv.standard.Stubs.GameStubForBattleTesting;
import hotciv.standard.Stubs.StubFixedDie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Henrik BÃ¦rbak Christensen, Aarhus University
 */
public class TestEpsilonAttackStrategy {

    Game game;
    private StubFixedDie fixedDie;
    EpsilonAttackStrategy attackStrategy;

    @Before
    public void setUp() {
        game = new GameStubForBattleTesting();
        fixedDie = new StubFixedDie();
        attackStrategy = new EpsilonAttackStrategy(fixedDie);
    }

    @Test public void shouldGiveCorrectTerrainFactors() {
        // plains have multiplier 1
        assertThat(attackStrategy.getTerrainFactor(game, new Position(0,1)), is(1));
        // hills have multiplier 2
        assertThat(attackStrategy.getTerrainFactor(game, new Position(1,0)), is(2));
        // forest have multiplier 2
        assertThat(attackStrategy.getTerrainFactor(game, new Position(0,0)), is(2));
        // cities have multiplier 3
        assertThat(attackStrategy.getTerrainFactor(game, new Position(1,1)), is(3));
    }

    @Test public void shouldGiveSum1ForBlueAtP5_5() {
        assertThat("Blue unit at (5,5) should get +1 support",
                attackStrategy.getFriendlySupport( game, new Position(5,5), Player.BLUE), is(+1));
    } 

    @Test public void shouldGiveSum0ForBlueAtP2_4() {
        assertThat("Blue unit at (2,4) should get +0 support",
                attackStrategy.getFriendlySupport( game, new Position(2,4), Player.BLUE), is(+0));
    }
    @Test public void shouldGiveSum2ForRedAtP2_4() {
        assertThat("Red unit at (2,4) should get +2 support",
                attackStrategy.getFriendlySupport( game, new Position(2,4), Player.RED), is(+2));
    }
    @Test public void shouldGiveSum3ForRedAtP2_2() {
        assertThat("Red unit at (2,2) should get +3 support",
                attackStrategy.getFriendlySupport( game, new Position(2,2), Player.RED), is(+3));
    }

    @Test public void shouldGiveCorrectFixedDieFactor() {
        assertThat(fixedDie.getDieFactor(), is(4));
    }

    @Test public void shouldGiveCorrectCombinedAttackStrength() {
        //red stub-archer: attack strength 1, friendly support: 2, terrain factor: 1 (plain) and fixedDie factor: 4
        assertThat(attackStrategy.getCombinedAttackStrength(game, new Position(2,3), Player.RED), is((1+2)*4));
    }

    @Test public void shouldGiveCorrectCombinedDefenseStrength() {
        //red stub-archer: defense strength 1, friendly support: 2, terrain factor: 1 (plain) and fixedDie factor: 4
        assertThat(attackStrategy.getCombinedDefenseStrength(game, new Position(2,3), Player.RED), is((1+2)*4));
    }

    @Test public void shouldGiveFalseForBlueAttackingRed() {
        Position blueArcherP = new Position(4,4);
        Position redArcherP = new Position(3,3);
        //redArcher has friendlySupport:2, blueArcher has friendlySupport: 0
        assertThat(attackStrategy.attack(game, blueArcherP, redArcherP, Player.BLUE), is(false));
    }

    @Test public void shouldGiveTrueForRedAttackingBlue() {
        Position blueArcherP = new Position(4,4);
        Position redArcherP = new Position(3,3);
        //redArcher has friendlySupport:2, blueArcher has friendlySupport: 0
        assertThat(attackStrategy.attack(game, redArcherP, blueArcherP, Player.RED), is(true));
    }


}
