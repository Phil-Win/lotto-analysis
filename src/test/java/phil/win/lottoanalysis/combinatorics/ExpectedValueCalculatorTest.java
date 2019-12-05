package phil.win.lottoanalysis.combinatorics;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import phil.win.lottoanalysis.model.transformed.basic.Game;
import phil.win.lottoanalysis.model.transformed.basic.Prize;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
class ExpectedValueCalculatorTest {

    @InjectMocks
    ExpectedValueCalculator expectedValueCalculator;

    @Mock
    Game game;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        when(game.getCost()).thenReturn(Long.valueOf("1"));
        when(game.getEstimatedInitialTotalNumberTickets()).thenReturn(Long.valueOf("5000000"));
        when(game.getEstimatedRemainingTotalNumberTickets()).thenReturn(Long.valueOf("65461"));
        when(game.getRemainingPrizes()).thenReturn(null);
        when(game.getInitialPrizes()).thenReturn(null);
        expectedValueCalculator =   new ExpectedValueCalculator(game, Long.parseLong("1"));
    }

    @Test
    void determineRangeCombinatorics() {
    }

    @Test
    void generateValueAndCombinationMap() {
        Map<BigInteger, BigInteger> expectedValue    = new HashMap<BigInteger, BigInteger>()  ;
        expectedValue.put(new BigInteger("1"), new BigInteger("500"));
        expectedValue.put(new BigInteger("5"), new BigInteger("200"));
        expectedValue.put(new BigInteger("100"), new BigInteger("3"));
        expectedValue.put(new BigInteger("5000"), new BigInteger("1"));
        assertEquals(expectedValue, expectedValueCalculator.generateValueAndCombinationMap( generateDummyPrizeList()));
    }
    @Test
    void generateValueAndCombinationMap_2Tickets() {
        expectedValueCalculator.setHeroTicketsPurchased(Long.valueOf("2"));
        Map<BigInteger, BigInteger> expectedValue    = new HashMap<BigInteger, BigInteger>()  ;
        expectedValue.put(new BigInteger("2"), new BigInteger("124750"));
        expectedValue.put(new BigInteger("6"), new BigInteger("100000"));
        expectedValue.put(new BigInteger("101"), new BigInteger("1500"));
        expectedValue.put(new BigInteger("5001"), new BigInteger("500"));
        expectedValue.put(new BigInteger("10"), new BigInteger("19900"));
        expectedValue.put(new BigInteger("105"), new BigInteger("600"));
        expectedValue.put(new BigInteger("5005"), new BigInteger("200"));
        expectedValue.put(new BigInteger("200"), new BigInteger("3"));
        expectedValue.put(new BigInteger("5100"), new BigInteger("3"));
        expectedValue.put(new BigInteger("10000"), new BigInteger("0"));
        assertEquals(expectedValue, expectedValueCalculator.generateValueAndCombinationMap( generateDummyPrizeList()));
    }

    @Test
    void determineScenarioCombinations() {
        BigInteger expectedValue    = new BigInteger("3")  ;
        assertEquals(expectedValue, expectedValueCalculator.determineScenarioCombinations(generateDummyScenarios(), generateDummyPrizeList()));
    }

    @Test
    void determineScenarioValue() {
        BigInteger expectedValue    = new BigInteger("100")  ;
        assertEquals(expectedValue, expectedValueCalculator.determineScenarioValue(generateDummyScenarios(), generateDummyPrizeList()));
    }

    @Test
    void determineExpectedValue() {
        Map<BigInteger, BigInteger> inputMap    =   new HashMap<BigInteger, BigInteger>();
        inputMap.put(new BigInteger("1"), new BigInteger("500"));
        inputMap.put(new BigInteger("5"), new BigInteger("200"));
        inputMap.put(new BigInteger("100"), new BigInteger("3"));
        inputMap.put(new BigInteger("5000"), new BigInteger("1"));

        BigDecimal  expectedValue   =   new BigDecimal("6800").divide(BigDecimal.valueOf(5000000));
        assertEquals(expectedValue, expectedValueCalculator.determineExpectedValue(inputMap, Long.valueOf(5000000)));
    }

    @Test
    void mapMultiplier() {
        Map<BigInteger, BigInteger> inputMap    =   new HashMap<BigInteger, BigInteger>();
        inputMap.put(new BigInteger("1"), new BigInteger("500"));
        inputMap.put(new BigInteger("5"), new BigInteger("200"));
        inputMap.put(new BigInteger("100"), new BigInteger("3"));
        inputMap.put(new BigInteger("5000"), new BigInteger("1"));

        BigInteger expectedOutput   =   new BigInteger("6800");

        assertEquals(expectedOutput, expectedValueCalculator.mapMultiplier(inputMap));
    }

    List<Prize> generateDummyPrizeList() {
        List<Prize> prizes  =   new ArrayList<Prize>();
        prizes.add(new Prize(Long.valueOf("1"),Long.valueOf("500")));
        prizes.add(new Prize(Long.valueOf("5"),Long.valueOf("200")));
        prizes.add(new Prize(Long.valueOf("100"),Long.valueOf("3")));
        prizes.add(new Prize(Long.valueOf("5000"),Long.valueOf("1")));
        return prizes;
    }

    List<Integer> generateDummyScenarios() {
        List<Integer> scenarios  =   new ArrayList<Integer>();
        scenarios.add(0);
        scenarios.add(0);
        scenarios.add(1);
        scenarios.add(0);
        return scenarios;
    }

}