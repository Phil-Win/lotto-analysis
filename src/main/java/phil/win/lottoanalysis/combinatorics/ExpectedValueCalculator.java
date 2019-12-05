package phil.win.lottoanalysis.combinatorics;

import lombok.extern.slf4j.Slf4j;
import phil.win.lottoanalysis.model.transformed.basic.Game;
import phil.win.lottoanalysis.model.transformed.basic.Prize;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import org.apache.commons.math3.util.CombinatoricsUtils;
import phil.win.lottoanalysis.model.transformed.expectedvalue.ExpectedValue;
import phil.win.lottoanalysis.model.transformed.expectedvalue.TicketCombinations;

@Slf4j
public class ExpectedValueCalculator {

    private Long        heroTicketsPurchased;
    private Long        ticketCost;
    private Long        totalTicketsStart;
    private Long        totalTicketsCurrent;
    private List<Prize> remainingPrizes;
    private List<Prize> initialPrizes;
    private BigDecimal  expectedValueTicketsPurchased;
    private Map<BigInteger, BigInteger> valueCombinationMapInitial;
    private Map<BigInteger, BigInteger> valueCombinationMapCurrent;

    public ExpectedValueCalculator(Game game, Long ticketsPurchased) {
        this.heroTicketsPurchased   =   ticketsPurchased;
        this.ticketCost             =   game.getCost();
        this.totalTicketsStart      =   game.getEstimatedInitialTotalNumberTickets();
        this.totalTicketsCurrent    =   game.getEstimatedRemainingTotalNumberTickets();
        this.remainingPrizes        =   game.getRemainingPrizes();
        this.initialPrizes          =   game.getInitialPrizes();
    }

    public ExpectedValue determineRangeCombinatorics(BigInteger lowValue, BigInteger highValue) {
        ExpectedValue   expectedValue   =   new ExpectedValue();

        expectedValue.setLowValue(lowValue);
        expectedValue.setHighValue(highValue);

        TicketCombinations  ticketCombinationsCurrent   =   new TicketCombinations();
        TicketCombinations  ticketCombinationsInitial   =   new TicketCombinations();

        this.valueCombinationMapCurrent =   generateValueAndCombinationMap(remainingPrizes);
        this.valueCombinationMapInitial =   generateValueAndCombinationMap(initialPrizes);

        TreeMap<BigInteger, BigInteger> currentTree = new TreeMap<BigInteger, BigInteger>(this.valueCombinationMapCurrent);
        TreeMap<BigInteger, BigInteger> initialTree = new TreeMap<BigInteger, BigInteger>(this.valueCombinationMapInitial);

        BigInteger countOfInterest  =   BigInteger.ZERO;
        for (Map.Entry<BigInteger, BigInteger> entry : currentTree.subMap(lowValue, highValue).entrySet()) {
            countOfInterest =   countOfInterest.add(entry.getValue());
        }
        ticketCombinationsCurrent.setWaysToGetScenario(countOfInterest);
        ticketCombinationsCurrent.setTotalCombinations(CombinatoricsHelper.bigNChooseBigK(totalTicketsCurrent, heroTicketsPurchased));
        expectedValue.setCurrent(ticketCombinationsCurrent);

        countOfInterest  =   BigInteger.ZERO;
        for (Map.Entry<BigInteger, BigInteger> entry : initialTree.subMap(lowValue, highValue).entrySet()) {
            countOfInterest =   countOfInterest.add(entry.getValue());
        }
        ticketCombinationsInitial.setWaysToGetScenario(countOfInterest);
        ticketCombinationsInitial.setTotalCombinations(CombinatoricsHelper.bigNChooseBigK(totalTicketsStart, heroTicketsPurchased));
        expectedValue.setInitial(ticketCombinationsInitial);

        return expectedValue;
    }


    protected Map<BigInteger, BigInteger> generateValueAndCombinationMap(List<Prize> prizes) {
        Map<BigInteger, BigInteger> mapOfScenarioValueAndCombinations   =   new HashMap<BigInteger, BigInteger>();
        List<Integer>   scenarioOfInterest;
        BigInteger      scenarioValue;
        BigInteger      scenarioCombinations;

        Iterator<List<Integer>> scenarioIterator    =   CombinatoricsHelper.generateSolutionCombinations(prizes.size(), Math.toIntExact(this.heroTicketsPurchased));

        while (scenarioIterator.hasNext()) {
            scenarioOfInterest  =   scenarioIterator.next();
            scenarioValue   =   determineScenarioValue(scenarioOfInterest, prizes);
            scenarioCombinations    =   determineScenarioCombinations(scenarioOfInterest, prizes);
            if (mapOfScenarioValueAndCombinations.containsKey(scenarioValue)) {
                scenarioCombinations    =   scenarioCombinations.add(mapOfScenarioValueAndCombinations.get(scenarioValue));
            }
            mapOfScenarioValueAndCombinations.put(scenarioValue, scenarioCombinations);
        }

        return mapOfScenarioValueAndCombinations;
    }

    protected BigInteger determineScenarioCombinations(List<Integer> scenarioOfInterest, List<Prize> remainingPrizes) {
        BigInteger scenarioCount    =   BigInteger.ONE;
        Long ticketsToChooseFrom;
        Integer ticketsPicking;
        BigInteger combinationsOfInterest;
        for (int i = 0; i < scenarioOfInterest.size(); i ++) {
            ticketsToChooseFrom =   remainingPrizes.get(i).getTicketCount();
            ticketsPicking      =   scenarioOfInterest.get(i);
            combinationsOfInterest  =   CombinatoricsHelper.bigNChooseBigK(ticketsToChooseFrom, ticketsPicking.longValue());
            if (combinationsOfInterest.equals(BigInteger.ZERO)) {
                return BigInteger.ZERO;
            }
            scenarioCount   =   scenarioCount.multiply(combinationsOfInterest);
        }
        return scenarioCount;
    }

    protected BigInteger determineScenarioValue(List<Integer> scenarioOfInterest, List<Prize> remainingPrizes) {
        BigInteger scenarioValue    =   BigInteger.ZERO;
        BigInteger bigIntegerOfInterest;

        for (int i = 0; i < scenarioOfInterest.size(); i ++) {
            bigIntegerOfInterest    =   new BigInteger(String.valueOf(remainingPrizes.get(i).getPrize()));
            bigIntegerOfInterest    =   bigIntegerOfInterest.multiply(BigInteger.valueOf(scenarioOfInterest.get(i)));
            scenarioValue   =   scenarioValue.add(bigIntegerOfInterest );
        }
        return scenarioValue;
    }

    public BigDecimal determineExpectedValue(Map<BigInteger, BigInteger> valueComboMap, Long totalTickets) {
        BigDecimal expectedValue    =   new BigDecimal(mapMultiplier(valueComboMap));
        BigInteger totalTicketPurchaseCombination = CombinatoricsHelper.bigNChooseBigK(totalTickets, this.heroTicketsPurchased);

        return expectedValue.divide(new BigDecimal(totalTicketPurchaseCombination));
    }

    public BigInteger mapMultiplier(Map<BigInteger, BigInteger> valueCombinationMap) {
        BigInteger returnValue    =   BigInteger.ZERO;
        BigInteger valueOfInterest;
        for (Map.Entry<BigInteger, BigInteger> entryValueCombination : valueCombinationMap.entrySet()) {
            valueOfInterest =   entryValueCombination.getKey();
            valueOfInterest =   valueOfInterest.multiply(entryValueCombination.getValue());
            returnValue   =   returnValue.add(valueOfInterest);
        }
        return returnValue;
    }

    protected Boolean setExpectedValueTicketsPurchased() {
        BigDecimal  bigInteger  =   new BigDecimal(String.valueOf(-this.heroTicketsPurchased));
        bigInteger  =   bigInteger.multiply(new BigDecimal(String.valueOf(this.ticketCost)));
        this.expectedValueTicketsPurchased  =   bigInteger;
        return true;
    }

    protected Boolean   setValueCombinationMapInitial() {
        Map<BigInteger, BigInteger> mapOfInitialValueCombinations   =   generateValueAndCombinationMap(this.initialPrizes);
        this.valueCombinationMapInitial =   mapOfInitialValueCombinations;
        return true;
    }

    protected Boolean   setValueCombinationMapCurrent() {
        Map<BigInteger, BigInteger> mapOfInitialValueCombinations   =   generateValueAndCombinationMap(this.remainingPrizes);
        this.valueCombinationMapCurrent =   mapOfInitialValueCombinations;
        return true;
    }

    public void setHeroTicketsPurchased(Long heroTicketsPurchased) {
        this.heroTicketsPurchased = heroTicketsPurchased;
    }

}
