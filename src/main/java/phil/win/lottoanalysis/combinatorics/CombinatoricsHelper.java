package phil.win.lottoanalysis.combinatorics;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.exception.MathArithmeticException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.math.BigInteger;
import java.util.*;

@Slf4j
public class CombinatoricsHelper {
    public final static Character STAR  =   '*';
    public final static Character BAR   =   '|';

    /**
     * This generates the count for k-multiset with n-options where option repeats are allowed
     * E.G. You have n=4 donuts to choose from and you can buy 3 donuts, a multi-set would be
     * {1,1,4} where you bought two of donut option #1 and one of donut option #4
     *
     * This can also be used to get how many ways can you put n identical objects in k bins
     * Donut Example {1,1,4} --> **|||* where two donuts were in section 1 and 1 donut was in section 4
     *
     * This can also be used to get the number of permutations to add n whole numbers to sum up to k
     * {1,1,4} --> 2 + 0 + 0 + 1 = 3
     *
     * )
     * This can also be used for the permutation of solutions to have k
     * @param n
     * @param k
     * @return
     */
    public static long multisetCombinationCount(int n, int k) {
        return CombinatoricsUtils.binomialCoefficient(n + k -1, k);
    }

    public static BigInteger bigNChooseBigK(BigInteger n, BigInteger k) {
        BigInteger  returnInteger;
        try {
              returnInteger =  new BigInteger(String.valueOf(CombinatoricsUtils.binomialCoefficient(n.intValue(), k.intValue())));
              return returnInteger;
        } catch (MathIllegalArgumentException exception) {
            return BigInteger.ZERO;
        } catch (MathArithmeticException exception) {
            returnInteger   =   BigInteger.ONE;
            for (long i = n.longValue(); i > n.subtract(k).longValue(); i--) {
                returnInteger   =   returnInteger.multiply(new BigInteger(String.valueOf(i)));
            }

            BigInteger divisor  =   BigInteger.ONE;

            for (long i = 1; i <= k.longValue(); i++) {
                divisor =   divisor.multiply(new BigInteger(String.valueOf(i)));
            }

            return returnInteger.divide(divisor);
        }
    }

    public static BigInteger bigNChooseBigK(Integer n, Integer k) {
        return bigNChooseBigK(new BigInteger(String.valueOf(n)), new BigInteger(String.valueOf(k)));
    }
    public static BigInteger bigNChooseBigK(Long n, Long k) {
        return bigNChooseBigK(new BigInteger(String.valueOf(n)), new BigInteger(String.valueOf(k)));
    }

//TODO:
//    public static Iterator<int[]> multisetCombinationIterator(int n, int k) {
//        List<int[]> multisetList    =   new LinkedList<int[]>();
//
//        return null;
//
//    }

//    /**TODO:
//     * Translation from set -> star and bars is like identifying the position of the bars
//     * @param n
//     * @param k
//     * @return
//     */
//    public Iterator<Character[]> starAndBarPermutationIterator(int n, int k) {
//        return null;
//    }

    /**TODO: Already did this before realizing I don't need it...
     * Translating a set --> Stars and bars is like using the set to identify the position of the stars
     * E.G. A set of C5_2 = {3,5}... that says ((4 2)) donuts are on the 3rd and 5th position ||*|*
     */
    protected static Character[]   translateSetToStarsAndBars(int[] set, int n, int k) {
        Character[] characterArray  =   new Character[n + k - 1];
        for (int i=0; i < characterArray.length; i++) {
            characterArray[i]   =   BAR;
        }
        for (int i : set) {
            characterArray[i]   =   STAR;
        }
        return characterArray;
    }

    public static Iterator<List<Integer>>   generateSolutionCombinations(int n, int k) {
        List<List<Integer>> combinationList =   new ArrayList<List<Integer>>();
        Iterator<int[]> combinationIterator =   CombinatoricsUtils.combinationsIterator(n+k-1, k);
        while (combinationIterator.hasNext()) {
            combinationList.add(translateSetToSolutionArray(combinationIterator.next(), n, k));
        }
        return combinationList.listIterator();
    }

    public static Iterator<MultiSet<Integer>>   generateMultiSetCombinations(int n, int k) {
        List<MultiSet<Integer>> combinationList =   new ArrayList<MultiSet<Integer>>();
        Iterator<int[]> combinationIterator =   CombinatoricsUtils.combinationsIterator(n+k-1, k);
        while (combinationIterator.hasNext()) {
            combinationList.add(translateSetToMultiSetArray(combinationIterator.next(), n, k));
        }
        return combinationList.listIterator();
    }


    /**
     * Translates a combination set from Cn+k-1_k to a solution array of n, k
     * If you want to figure out all the solutions to n=4 whole numbers that sum to k=3 where order matters,
     * One set combination is {0,1,4} and this method translates that to [ 2 , 0, 1, 0] (think 0,1,4 = **||*|
     * @param inputArray
     * @param n
     * @param k
     * @return
     */
    protected static List<Integer>    translateSetToSolutionArray(int[] inputArray, int n, int k) {
        List<Integer>   integerList =   new LinkedList<Integer>();
        Set<Integer> setOfInterest  = Sets.newHashSet(
                ContiguousSet.create(Range.closed(0,n+k-2), DiscreteDomain.integers()));

        setOfInterest.removeAll(Ints.asList(inputArray));

        Integer binStart    =   -1;
        Integer intOfInterest;
        Iterator<Integer> iterator   =   setOfInterest.iterator();

        while (iterator.hasNext()) {
            intOfInterest   =   iterator.next();
            integerList.add(intOfInterest - binStart - 1);
            binStart    =   intOfInterest;
            if (!iterator.hasNext()) {
                integerList.add(n + k - 2 - binStart);
            }
        }
        return integerList;
    }

    /**
     * Translates a combination set from Cn+k-1_k to a multiset(set where repeats are allowed) of n, k
     * If you want to figure out all the solutions to pick k=3 donuts when you have n=4 donut options
     * One set combination is {2, 4, 5} (Think ||*|**) which is like picking 1 donut of category 3 and 2 donuts of category 4
     * {3,4,4}
     * @param inputArray
     * @param n
     * @param k
     * @return
     */
    protected static MultiSet<Integer>    translateSetToMultiSetArray(int[] inputArray, int n, int k) {
//        log.info("Start with : ");
//        for (int i : inputArray) {
//            log.info("{}", i);
//        }
        MultiSet<Integer>   multiSet =   new HashMultiSet<Integer>();
        Set<Integer> setOfInterest  = Sets.newHashSet(
                ContiguousSet.create(Range.closed(0,n+k-2), DiscreteDomain.integers()));

        setOfInterest.removeAll(Ints.asList(inputArray));

        Integer binStart    =   -1;
        Integer objectChosenLabel =   0;
        Integer intOfInterest;
        Iterator<Integer> iterator   =   setOfInterest.iterator();

        while (iterator.hasNext()) {
            intOfInterest   =   iterator.next();
            for (int i = 0 ; i < intOfInterest - binStart- 1 ; i ++) {
                multiSet.add(objectChosenLabel);
//                log.info("added the number {} when the int of interest was {}, bin {}", objectChosenLabel, intOfInterest, binStart);
            }
            binStart    =   intOfInterest;
            objectChosenLabel++;
            if (!iterator.hasNext() && (n + k - 2 != intOfInterest)) {
                for (int i = 0; i < n + k - 2 - binStart; i ++) {
                    multiSet.add(objectChosenLabel);
//                    log.info("added the number {} when the int of interest was {}, bin {}", objectChosenLabel, intOfInterest, binStart);
                }
            }
        }
        return multiSet;
    }


}
