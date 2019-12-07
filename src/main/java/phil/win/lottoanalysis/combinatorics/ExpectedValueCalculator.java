package phil.win.lottoanalysis.combinatorics;

import lombok.extern.slf4j.Slf4j;
import phil.win.lottoanalysis.model.transformed.basic.Game;
import phil.win.lottoanalysis.model.transformed.basic.Prize;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import phil.win.lottoanalysis.model.transformed.expectedvalue.TicketCombinations;
import phil.win.lottoanalysis.model.transformed.expectedvalue.ValueRow;
import phil.win.lottoanalysis.model.transformed.expectedvalue.ValueTable;

@Slf4j
public class ExpectedValueCalculator {

    private Long        heroTicketsPurchased;
    private Long        ticketCost;
    private Long        totalTicketsStart;
    private Long        totalTicketsCurrent;
    private List<Prize> remainingPrizes;
    private List<Prize> initialPrizes;
    private BigDecimal  expectedValueTicketsPurchased;
    private TreeMap<BigInteger, BigInteger> valueCombinationMapInitial;
    private TreeMap<BigInteger, BigInteger> valueCombinationMapCurrent;

    public ExpectedValueCalculator(Game game, Long ticketsPurchased) {
        this.heroTicketsPurchased   =   ticketsPurchased;
        this.ticketCost             =   game.getCost();
        this.totalTicketsStart      =   game.getEstimatedInitialTotalNumberTickets();
        this.totalTicketsCurrent    =   game.getEstimatedRemainingTotalNumberTickets();
        this.remainingPrizes        =   game.getRemainingPrizes();
        this.initialPrizes          =   game.getInitialPrizes();
    }


    public ValueRow getValuesAbove(Long floor) {
        generateCombinationMaps();
        BigInteger  largestKey  =   (valueCombinationMapCurrent.lastKey().compareTo(valueCombinationMapInitial.lastKey()) > 0) ? valueCombinationMapCurrent.lastKey() : valueCombinationMapInitial.lastKey();
        return determineRangeCombinatorics(new BigInteger(String.valueOf(floor)), largestKey);
    }

    public ValueTable getValueRowListDollarRange(Long range) {
        ValueTable  valueTable  =   new ValueTable();
        List<ValueRow> valueRowList =   new ArrayList<ValueRow>();
        generateCombinationMaps();
        BigInteger  rangeAsBigInt   =   new BigInteger(String.valueOf(range));
        Set<BigInteger> keys    =   valueCombinationMapCurrent.keySet();
        BigInteger keyOfInterest = null;
        BigInteger  lowValue    =   null;
        for (Iterator<BigInteger> i = keys.iterator(); i.hasNext(); ) {
            keyOfInterest   =   i.next();
            if (lowValue    ==  null) {
                lowValue    =   keyOfInterest;
            }

            if ((keyOfInterest.subtract(lowValue).compareTo(rangeAsBigInt) >= 0) || (!i.hasNext())) {
                valueRowList.add(determineRangeCombinatorics(lowValue, keyOfInterest));
                log.info("Printing the low and high key {}:{}", lowValue, keyOfInterest);
                lowValue   =   null;
            }
        }
        
        BigInteger  largestKey  =   (valueCombinationMapCurrent.lastKey().compareTo(valueCombinationMapInitial.lastKey()) > 0) ? valueCombinationMapCurrent.lastKey() : valueCombinationMapInitial.lastKey();
        log.info("Printing the last key {}:{}and the largest {} along with the key of interest{}", valueCombinationMapCurrent.lastKey(), valueCombinationMapInitial.lastKey(), largestKey, keyOfInterest);

        //valueRowList.add(determineRangeCombinatorics(keyOfInterest, largestKey));
        valueTable.setValues(valueRowList);
        valueTable.setCurrentExpectedValue(calculateEV(this.valueCombinationMapCurrent));
        valueTable.setInitialExpectedValue(calculateEV(this.valueCombinationMapInitial));
        return valueTable;
    }

    public ValueTable getValueRowListPercentRange(Double rangePercentAsDecimal) {
        ValueTable  valueTable  =   new ValueTable();
        List<ValueRow> valueRowList =   new ArrayList<ValueRow>();
        generateCombinationMaps();
        Set<BigInteger> keys    =   valueCombinationMapCurrent.keySet();
        BigInteger keyOfInterest = null;
        BigInteger  lowValue    =   null;
        BigInteger  combinationCount    =   BigInteger.ZERO;
        BigDecimal  numberOfCombinationsInRange    =   new BigDecimal(CombinatoricsHelper.bigNChooseBigK(this.totalTicketsCurrent, this.heroTicketsPurchased));
        numberOfCombinationsInRange    =   numberOfCombinationsInRange.multiply(new BigDecimal(rangePercentAsDecimal));
        for (Iterator<BigInteger> i = keys.iterator(); i.hasNext(); ) {
            keyOfInterest   =   i.next();
            combinationCount.add(valueCombinationMapCurrent.get(keyOfInterest));

            if ((new BigDecimal(combinationCount).compareTo(numberOfCombinationsInRange) >= 0) || (!i.hasNext())) {
                valueRowList.add(determineRangeCombinatorics(lowValue, keyOfInterest));
                lowValue   =   null;
            }
        }
        BigInteger  largestKey  =   (valueCombinationMapCurrent.lastKey().compareTo(valueCombinationMapInitial.lastKey()) > 0) ? valueCombinationMapCurrent.lastKey() : valueCombinationMapInitial.lastKey();
        if (keyOfInterest != null && !keyOfInterest.equals(largestKey)) {
            valueRowList.add(determineRangeCombinatorics(keyOfInterest, largestKey));
        }
        valueTable.setValues(valueRowList);
        valueTable.setCurrentExpectedValue(calculateEV(this.valueCombinationMapCurrent));
        valueTable.setInitialExpectedValue(calculateEV(this.valueCombinationMapInitial));
        return valueTable;
    }

    public BigDecimal   calculateEV(TreeMap<BigInteger, BigInteger> valueCombinationMap) {
        BigDecimal ticketCostAndCount   =   new BigDecimal(String.valueOf(heroTicketsPurchased)).multiply(new BigDecimal(String.valueOf(-ticketCost)));
        BigDecimal  multiplicationMap   =   new BigDecimal(mapMultiplier(valueCombinationMap));
        BigDecimal  combinationSum      =   BigDecimal.ZERO;
        for (Map.Entry<BigInteger, BigInteger> entry : valueCombinationMap.entrySet()) {
            combinationSum  =   combinationSum.add(new BigDecimal(entry.getValue()));
        }

        multiplicationMap   =   multiplicationMap.divide(combinationSum, 200, RoundingMode.HALF_DOWN);
        multiplicationMap   =   multiplicationMap.add(ticketCostAndCount);
        return multiplicationMap;
    }

    public ValueRow determineRangeCombinatorics(BigInteger lowValue, BigInteger highValue) {
        ValueRow   expectedValue   =   new ValueRow();
        generateCombinationMaps();
        expectedValue.setTicketCombinationCurrent(generateTicketCombination(lowValue, highValue, this.valueCombinationMapCurrent, this.totalTicketsCurrent));
        expectedValue.setTicketCombinationInitial(generateTicketCombination(lowValue, highValue, this.valueCombinationMapInitial, this.totalTicketsStart));

        return expectedValue;
    }

    private TicketCombinations  generateTicketCombination(BigInteger lowValue, BigInteger highValue, TreeMap<BigInteger, BigInteger> treeMap, Long totalTickets) {
        log.info("Printing the low and high key generateTicketCombination {}:{}", lowValue, highValue);
        TicketCombinations ticketCombinations   =   new TicketCombinations();
        SortedMap<BigInteger, BigInteger> currentTree =  treeMap.subMap(lowValue, highValue);
        ticketCombinations.setLowValue(lowValue);
        ticketCombinations.setHighValue(highValue);
        BigInteger combinationCount  =   BigInteger.ZERO;
        BigDecimal valueAverage            =   BigDecimal.ZERO;
        for (BigInteger key : currentTree.keySet()) {
            log.info("Adding key:val of entry {}:{}", key, currentTree.get(key));
            combinationCount =   combinationCount.add(currentTree.get(key));
            valueAverage    =   valueAverage.add(new BigDecimal(key.multiply(currentTree.get(key))));
        }
        valueAverage    =   valueAverage.divide(new BigDecimal(combinationCount), 200, RoundingMode.HALF_DOWN);
        ticketCombinations.setTotalCombinations(CombinatoricsHelper.bigNChooseBigK(totalTickets, this.heroTicketsPurchased));
        ticketCombinations.setWaysToGetScenario(combinationCount);
        log.info("Printing the low and high key and combo count generateTicketCombination {}:{}:{}", lowValue, highValue,combinationCount);
        ticketCombinations.setAverageTicketValue(valueAverage);
        return ticketCombinations;
    }


    protected TreeMap<BigInteger, BigInteger> generateValueAndCombinationMap(List<Prize> prizes) {
        TreeMap<BigInteger, BigInteger> mapOfScenarioValueAndCombinations   =   new TreeMap<BigInteger, BigInteger>();
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

    public BigDecimal determineExpectedValue(TreeMap<BigInteger, BigInteger> valueComboMap, Long totalTickets) {
        BigDecimal expectedValue    =   new BigDecimal(mapMultiplier(valueComboMap));
        BigInteger totalTicketPurchaseCombination = CombinatoricsHelper.bigNChooseBigK(totalTickets, this.heroTicketsPurchased);

        return expectedValue.divide(new BigDecimal(totalTicketPurchaseCombination));
    }

    public BigInteger mapMultiplier(TreeMap<BigInteger, BigInteger> valueCombinationMap) {
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
        TreeMap<BigInteger, BigInteger> mapOfInitialValueCombinations   =   generateValueAndCombinationMap(this.initialPrizes);
        this.valueCombinationMapInitial =   mapOfInitialValueCombinations;
        return true;
    }

    protected Boolean   setValueCombinationMapCurrent() {
        TreeMap<BigInteger, BigInteger> mapOfInitialValueCombinations   =   generateValueAndCombinationMap(this.remainingPrizes);
        this.valueCombinationMapCurrent =   mapOfInitialValueCombinations;
        return true;
    }

    public void setHeroTicketsPurchased(Long heroTicketsPurchased) {
        this.heroTicketsPurchased = heroTicketsPurchased;
        this.valueCombinationMapInitial = null;
        this.valueCombinationMapCurrent = null;
        generateCombinationMaps();
    }
    private void generateCombinationMaps() {
        if (this.valueCombinationMapInitial == null || this.valueCombinationMapCurrent == null) {
            this.valueCombinationMapCurrent =   generateValueAndCombinationMap(remainingPrizes);
            this.valueCombinationMapInitial =   generateValueAndCombinationMap(initialPrizes);
        }
    }
}
