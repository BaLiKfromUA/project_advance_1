package main.tests;

import balik.advanced.consoleApp.heap.LeftistHeap;
import balik.advanced.consoleApp.parser.Message;
import io.qameta.allure.junit4.DisplayName;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import util.MyJUnitStopWatch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestWithBigSize {
    private String sizeName = "big";
    private final int size = 50000;

    private static Logger logger = Logger.getLogger(TestWithBigSize.class);

    private LeftistHeap heap;

    @Rule
    public MyJUnitStopWatch stopwatch = new MyJUnitStopWatch();

    /**
     * Генератор случайных чисел.
     */
    private static final Random RANDOM = new Random();

    /**
     * Верхняя граница (не включительно) генерации случайных чисел.
     */
    private static final int UPPER_BOUND_RANDOM = 1000;

    /**
     * Нижняя граница (включительно) генерации случайных чисел.
     */
    private static final int LOWER_BOUND_RANDOM = -999;

    public TestWithBigSize() {
        logger.info(String.format("Tests with heap size %d are running...", size));
    }

    @Before
    public void setUp() {
        heap = new LeftistHeap();
    }

    @Test
    @DisplayName("Increasing negative elements test")
    public void insertNegativeIncreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = size-1; i >= 0; --i) {
            heap.insert(-i);
            expected.add(-i);
            assertEquals("Min should be -size:", -(size-1), heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

    @Test
    @DisplayName("Decreasing negative elements test")
    public void insertNegativeDecreasing(){
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            heap.insert(-i);
            expected.add(-i);
            assertEquals("Min should be 0:", -i, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }


    @Test
    @DisplayName("Increasing positive elements test")
    public void insertIncreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            expected.add(i);
            assertEquals("Min should be 0:", 0, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

    @Test
    @DisplayName("Decreasing positive elements test")
    public void insertDecreasing() {
        List<Integer> expected = new ArrayList<>();
        for (int i = size; i > 0; --i) {
            heap.insert(i);
            expected.add(i);
            assertEquals("Min should be current index:", i, heap.getMin());
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

    @Test
    @DisplayName("Zero Test")
    public void insertZero() {
        Integer[] expectedArray = new Integer[size];
        Arrays.fill(expectedArray, 0);

        for (int i = 0; i < size; ++i) {
            heap.insert(0);
            assertEquals("Min should be 0:", 0, heap.getMin());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

    @Test
    @DisplayName("Same Random Test")
    public void insertOneRandomNumber() {
        RANDOM.setSeed(System.currentTimeMillis());
        final int randomNumber = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        Integer[] expectedArray = new Integer[size];
        Arrays.fill(expectedArray, randomNumber);

        for (int i = 0; i < size; ++i) {
            heap.insert(randomNumber);
            assertEquals("Min should be your random number:", randomNumber, heap.getMin());
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

    @Test
    @DisplayName("Insert-extract test")
    public void insertExtract() {
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.extractMin();
            assertTrue("Heap should be empty:", heap.isEmpty());
        }

        assertEquals("Min should be -1:", -1, heap.getMin());
        assertTrue("Heap should be empty:", heap.isEmpty());
    }

    @Test
    @DisplayName("GetMin Test")
    public void getMinFromOneElementHeap() {
        RANDOM.setSeed(System.currentTimeMillis());
        final int randomNumber = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
        heap.insert(randomNumber);
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be your random number:", randomNumber, heap.getMin());
        }
    }

    @Test
    @DisplayName("Empty Heap Test")
    public void extractFromEmptyHeap() {
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be -1", -1, heap.getMin());
            heap.extractMin();
        }
    }

    @Test
    @DisplayName("Insert-Extract Increasing Test")
    public void insertExtractIncreasing() {
        for (int i = 0; i < size; ++i) {
            heap.insert(i);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.insert(i + 1);
            assertEquals("Min should be current index:", i, heap.getMin());
            heap.extractMin();
            assertEquals("Min should be current index+1:", i + 1, heap.getMin());
            heap.extractMin();
        }
    }

    @Test
    @DisplayName("Insert-Extract Increasing Test with random values")
    public void insertExtractRandom() {
        int previousElement = -1;
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            assertEquals("Min should be previous random element:", previousElement, heap.getMin());
            heap.extractMin();
            previousElement = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            heap.insert(previousElement);
        }
    }

    @Test
    @DisplayName("Insert Random Test")
    public void insertRandom() {
        List<Integer> expected = new ArrayList<>();
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            final int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            heap.insert(randomValue);
            expected.add(randomValue);
        }

        Integer[] expectedArray = new Integer[expected.size()];
        expectedArray = expected.toArray(expectedArray);
        Arrays.sort(expectedArray);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            result.add(heap.getMin());
            heap.extractMin();
        }

        Integer[] resultArray = new Integer[result.size()];
        resultArray = result.toArray(resultArray);

        assertArrayEquals("Arrays should be equal", expectedArray, resultArray);
    }

    @Test
    @DisplayName("Compare with priority queue Test")
    public void insertCompareWithPriorityQueue() {
        PriorityQueue<Integer> javaPriorityQueue = new PriorityQueue<>();
        RANDOM.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; ++i) {
            final int randomValue = RANDOM.nextInt(UPPER_BOUND_RANDOM * 2) + LOWER_BOUND_RANDOM;
            javaPriorityQueue.add(randomValue);
            heap.insert(randomValue);
            assertEquals("Minimums in heaps should be equal:", (int) javaPriorityQueue.peek(), heap.getMin());
        }

        for (int i = 0; i < size; ++i) {
            assertEquals("Minimums in heaps should be equal:", (int) javaPriorityQueue.peek(), heap.getMin());
            javaPriorityQueue.poll();
            heap.extractMin();
        }

        assertEquals("Heaps should be empty", heap.isEmpty(), javaPriorityQueue.isEmpty());
    }

    @After
    public void closeTest() {
        heap.clear();
    }
}
