package phil.win.lottoanalysis.combinatorics;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.multiset.HashMultiSet;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import org.apache.commons.collections4.IteratorUtils;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CombinatoricsHelperTest {

    @Test
    void translateSetToStarsAndBars_AllSets_N4K3() {
        int n   =   4;
        int k   =   3;
        assertArrayEquals(new Character[]{'*','*','*','|','|','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,1,2}, n,k));
        assertArrayEquals(new Character[]{'*','*','|','*','|','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,1,3}, n,k));
        assertArrayEquals(new Character[]{'*','*','|','|','*','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,1,4}, n,k));
        assertArrayEquals(new Character[]{'*','*','|','|','|','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,1,5}, n,k));
        assertArrayEquals(new Character[]{'*','|','*','*','|','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,2,3}, n,k));
        assertArrayEquals(new Character[]{'*','|','*','|','*','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,2,4}, n,k));
        assertArrayEquals(new Character[]{'*','|','*','|','|','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,2,5}, n,k));
        assertArrayEquals(new Character[]{'*','|','|','*','*','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,3,4}, n,k));
        assertArrayEquals(new Character[]{'*','|','|','*','|','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,3,5}, n,k));
        assertArrayEquals(new Character[]{'*','|','|','|','*','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{0,4,5}, n,k));
        assertArrayEquals(new Character[]{'|','*','*','*','|','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{1,2,3}, n,k));
        assertArrayEquals(new Character[]{'|','*','*','|','*','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{1,2,4}, n,k));
        assertArrayEquals(new Character[]{'|','*','*','|','|','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{1,2,5}, n,k));
        assertArrayEquals(new Character[]{'|','*','|','*','*','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{1,3,4}, n,k));
        assertArrayEquals(new Character[]{'|','*','|','*','|','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{1,3,5}, n,k));
        assertArrayEquals(new Character[]{'|','*','|','|','*','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{1,4,5}, n,k));
        assertArrayEquals(new Character[]{'|','|','*','*','*','|'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{2,3,4}, n,k));
        assertArrayEquals(new Character[]{'|','|','*','*','|','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{2,3,5}, n,k));
        assertArrayEquals(new Character[]{'|','|','*','|','*','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{2,4,5}, n,k));
        assertArrayEquals(new Character[]{'|','|','|','*','*','*'}, CombinatoricsHelper.translateSetToStarsAndBars(new int[]{3,4,5}, n,k));
    }

    @Test
    void translateSetToSolutionArray_AllSets_N4K2() {
        int n   =   4;
        int k   =   2;
        assertEquals(listGenerator(new int[]{2,0,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,1}, n,k));
        assertEquals(listGenerator(new int[]{1,1,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,2}, n,k));
        assertEquals(listGenerator(new int[]{1,0,1,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,3}, n,k));
        assertEquals(listGenerator(new int[]{1,0,0,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,4}, n,k));
        assertEquals(listGenerator(new int[]{0,2,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,2}, n,k));
        assertEquals(listGenerator(new int[]{0,1,1,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,3}, n,k));
        assertEquals(listGenerator(new int[]{0,1,0,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,4}, n,k));
        assertEquals(listGenerator(new int[]{0,0,2,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{2,3}, n,k));
        assertEquals(listGenerator(new int[]{0,0,1,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{2,4}, n,k));
        assertEquals(listGenerator(new int[]{0,0,0,2}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{3,4}, n,k));
    }

    @Test
    void translateSetToSolutionArray_AllSets_N4K3() {
        int n   =   4;
        int k   =   3;
        assertEquals(listGenerator(new int[]{3,0,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,1,2}, n,k));
        assertEquals(listGenerator(new int[]{2,1,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,1,3}, n,k));
        assertEquals(listGenerator(new int[]{2,0,1,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,1,4}, n,k));
        assertEquals(listGenerator(new int[]{2,0,0,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,1,5}, n,k));
        assertEquals(listGenerator(new int[]{1,2,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,2,3}, n,k));
        assertEquals(listGenerator(new int[]{1,1,1,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,2,4}, n,k));
        assertEquals(listGenerator(new int[]{1,1,0,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,2,5}, n,k));
        assertEquals(listGenerator(new int[]{1,0,2,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,3,4}, n,k));
        assertEquals(listGenerator(new int[]{1,0,1,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,3,5}, n,k));
        assertEquals(listGenerator(new int[]{1,0,0,2}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{0,4,5}, n,k));
        assertEquals(listGenerator(new int[]{0,3,0,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,2,3}, n,k));
        assertEquals(listGenerator(new int[]{0,2,1,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,2,4}, n,k));
        assertEquals(listGenerator(new int[]{0,2,0,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,2,5}, n,k));
        assertEquals(listGenerator(new int[]{0,1,2,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,3,4}, n,k));
        assertEquals(listGenerator(new int[]{0,1,1,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,3,5}, n,k));
        assertEquals(listGenerator(new int[]{0,1,0,2}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{1,4,5}, n,k));
        assertEquals(listGenerator(new int[]{0,0,3,0}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{2,3,4}, n,k));
        assertEquals(listGenerator(new int[]{0,0,2,1}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{2,3,5}, n,k));
        assertEquals(listGenerator(new int[]{0,0,1,2}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{2,4,5}, n,k));
        assertEquals(listGenerator(new int[]{0,0,0,3}), CombinatoricsHelper.translateSetToSolutionArray(new int[]{3,4,5}, n,k));
    }

    @Test
    void generateSolutionCombinations_N4K3() {
        int n   =   4;
        int k   =   3;
        List<List<Integer>> expectedValue   =   new ArrayList<List<Integer>>();
        expectedValue.add(listGenerator(new int[]{3,0,0,0}));
        expectedValue.add(listGenerator(new int[]{2,1,0,0}));
        expectedValue.add(listGenerator(new int[]{2,0,1,0}));
        expectedValue.add(listGenerator(new int[]{2,0,0,1}));
        expectedValue.add(listGenerator(new int[]{1,2,0,0}));
        expectedValue.add(listGenerator(new int[]{1,1,1,0}));
        expectedValue.add(listGenerator(new int[]{1,1,0,1}));
        expectedValue.add(listGenerator(new int[]{1,0,2,0}));
        expectedValue.add(listGenerator(new int[]{1,0,1,1}));
        expectedValue.add(listGenerator(new int[]{1,0,0,2}));
        expectedValue.add(listGenerator(new int[]{0,3,0,0}));
        expectedValue.add(listGenerator(new int[]{0,2,1,0}));
        expectedValue.add(listGenerator(new int[]{0,2,0,1}));
        expectedValue.add(listGenerator(new int[]{0,1,2,0}));
        expectedValue.add(listGenerator(new int[]{0,1,1,1}));
        expectedValue.add(listGenerator(new int[]{0,1,0,2}));
        expectedValue.add(listGenerator(new int[]{0,0,3,0}));
        expectedValue.add(listGenerator(new int[]{0,0,2,1}));
        expectedValue.add(listGenerator(new int[]{0,0,1,2}));
        expectedValue.add(listGenerator(new int[]{0,0,0,3}));

        List<List<Integer>> actualValue =   IteratorUtils.toList(CombinatoricsHelper.generateSolutionCombinations(n, k));
        assertEquals(expectedValue.size(), actualValue.size());
        for (List<Integer> expectedCombination : expectedValue) {
            assertTrue(actualValue.contains(expectedCombination));
        }
    }

    @Test
    void translateSetToMultiSetArray_N4K3() {
        int n   =   4;
        int k   =   3;
        for (Integer i : multiSetGenerator(new int[]{0,0,0})) {
            log.info("Expected multiset has {}", i);
        }
        for (Integer i : CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,1,2}, n,k)) {
            log.info("Actual multiset has {}", i);
        }
        assertEquals(multiSetGenerator(new int[]{0,0,0}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,1,2}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,0,1}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,1,3}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,2,0}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,1,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{3,0,0}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,1,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,1,1}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,2,3}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,1,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,2,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,1,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,2,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,2,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,3,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,2,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,3,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,3,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,4,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,1,1}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,2,3}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,1,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,2,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,1,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,2,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,2,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,3,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,2,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,3,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,3,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,4,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{2,2,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{2,3,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{2,2,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{2,3,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{2,3,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{2,4,5}, n,k));
        assertEquals(multiSetGenerator(new int[]{3,3,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{3,4,5}, n,k));
    }
    @Test
    void translateSetToMultiSetArray_AllSets_N4K2() {
        int n   =   4;
        int k   =   2;
        assertEquals(multiSetGenerator(new int[]{0,0}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,1}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,1}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,2}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,3}, n,k));
        assertEquals(multiSetGenerator(new int[]{0,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{0,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,1}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,2}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,3}, n,k));
        assertEquals(multiSetGenerator(new int[]{1,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{1,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{2,2}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{2,3}, n,k));
        assertEquals(multiSetGenerator(new int[]{2,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{2,4}, n,k));
        assertEquals(multiSetGenerator(new int[]{3,3}), CombinatoricsHelper.translateSetToMultiSetArray(new int[]{3,4}, n,k));
    }

    @Test
    void generateMultiSetCombinations_N4K3() {
        int n   =   4;
        int k   =   3;
        List<MultiSet<Integer>> expectedValue   =   new ArrayList<MultiSet<Integer>>();
        expectedValue.add(multiSetGenerator(new int[]{0,0,0}));
        expectedValue.add(multiSetGenerator(new int[]{0,0,1}));
        expectedValue.add(multiSetGenerator(new int[]{0,2,0}));
        expectedValue.add(multiSetGenerator(new int[]{3,0,0}));
        expectedValue.add(multiSetGenerator(new int[]{0,1,1}));
        expectedValue.add(multiSetGenerator(new int[]{0,1,2}));
        expectedValue.add(multiSetGenerator(new int[]{0,1,3}));
        expectedValue.add(multiSetGenerator(new int[]{0,2,2}));
        expectedValue.add(multiSetGenerator(new int[]{0,2,3}));
        expectedValue.add(multiSetGenerator(new int[]{0,3,3}));
        expectedValue.add(multiSetGenerator(new int[]{1,1,1}));
        expectedValue.add(multiSetGenerator(new int[]{1,1,2}));
        expectedValue.add(multiSetGenerator(new int[]{1,1,3}));
        expectedValue.add(multiSetGenerator(new int[]{1,2,2}));
        expectedValue.add(multiSetGenerator(new int[]{1,2,3}));
        expectedValue.add(multiSetGenerator(new int[]{1,3,3}));
        expectedValue.add(multiSetGenerator(new int[]{2,2,2}));
        expectedValue.add(multiSetGenerator(new int[]{2,2,3}));
        expectedValue.add(multiSetGenerator(new int[]{2,3,3}));
        expectedValue.add(multiSetGenerator(new int[]{3,3,3}));

        List<MultiSet<Integer>> actualValue =   IteratorUtils.toList(CombinatoricsHelper.generateMultiSetCombinations(n, k));
        assertEquals(expectedValue.size(), actualValue.size());
        for (MultiSet<Integer> expectedCombination : expectedValue) {
            assertTrue(actualValue.contains(expectedCombination));
        }

    }


    private List<Integer> listGenerator(int[] intArray) {
        ArrayList<Integer> returnValue   =   new ArrayList<Integer>();
        for (int i : intArray) {
            returnValue.add(i);
        }

        return returnValue;
    }
    private MultiSet<Integer> multiSetGenerator(int[] intArray) {
        MultiSet<Integer> returnValue   =   new HashMultiSet<Integer>();
        for (int i : intArray) {
            returnValue.add(i);
        }

        return returnValue;
    }


    @Test
    void bigNChooseBigK_BasicExample() {
        BigInteger  expectedValue   =   new BigInteger("190");
        assertEquals(expectedValue, CombinatoricsHelper.bigNChooseBigK(new BigInteger("20"), new BigInteger("2")));

    }

    @Test
    void bigNChooseBigK_KisLarger() {
        BigInteger  expectedValue   =   new BigInteger("0");
        assertEquals(expectedValue, CombinatoricsHelper.bigNChooseBigK(new BigInteger("20"), new BigInteger("50")));
    }

    @Test
    void bigNChooseBigK_NisNegative() {
        BigInteger  expectedValue   =   new BigInteger("0");
        assertEquals(expectedValue, CombinatoricsHelper.bigNChooseBigK(new BigInteger("-29"), new BigInteger("3")));
    }

    @Test
    void bigNChooseBigK_TooBigForLong() {
        BigInteger  expectedValue   =   new BigInteger("212018059856315206528444929253781446623761183765748781616472993354061852469992795815349105250718039750000");
        assertEquals(expectedValue, CombinatoricsHelper.bigNChooseBigK(new BigInteger("500000"), new BigInteger("22")));

        assertTrue(expectedValue.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) > 0);
    }
}