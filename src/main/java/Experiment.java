import java.io.*;
import java.util.*;

public class Experiment {
    private static final int[] inputSizes = {1000, 10000, 100000};
    private static final String[] inputTypes = {"Random", "Sorted", "Reverse", "Duplicate"};
    private static final Random random = new Random();

    /*main runner*/
    public static void main(String[] args) {
        File dir = new File("results");
        if (!dir.exists()) dir.mkdirs();

        try (FileWriter csv = new FileWriter("results/results.csv")) {
            csv.append("Algorithm;Size;InputType;TimeNs;MaxDepth;Operations;ActualVsTheoreticalRatio\n");

            for (int size : inputSizes) {
                for (String type : inputTypes) {
                    sortTest(size, type, csv);
                    selectTest(size, type, csv);
                }
                closestPairTest(size, csv);
            }
            System.out.println("Saved to results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*sorting experiments*/
    private static void sortTest(int size, String type, FileWriter csv) throws IOException {
        int[] arr = createInput(size, type);

        /* mergeSort*/
        MergeSorter merge = new MergeSorter();
        int[] mergeCopy = arr.clone();
        long start = System.nanoTime();
        merge.sort(mergeCopy);
        long timeMerge = System.nanoTime() - start;

        /* O(n logn) in theory*/
        double expected = size * (Math.log(size) / Math.log(2));
        double ratioMerge = timeMerge / expected;

        csv.append(String.format(new Locale("ru"), "MergeSort;%d;%s;%d;%d;%d;%.2f\n",
                size, type, timeMerge, merge.getMaxDepth(), merge.getComparisons(), ratioMerge));

        /*quickSort*/
        QuickSorter quick = new QuickSorter();
        int[] quickCopy = arr.clone();
        start = System.nanoTime();
        quick.sort(quickCopy);
        long timeQuick = System.nanoTime() - start;

        double ratioQuick = timeQuick / expected;

        csv.append(String.format(new Locale("ru"), "QuickSort;%d;%s;%d;%d;%d;%.2f\n",
                size, type, timeQuick, quick.getMaxDepth(), quick.getSwaps(), ratioQuick));
    }

    /*select experiment*/
    private static void selectTest(int size, String type, FileWriter csv) throws IOException {
        int[] arr = createInput(size, type);
        DeterministicSelector select = new DeterministicSelector();

        long start = System.nanoTime();
        select.select(arr.clone(), size / 2);
        long time = System.nanoTime() - start;

        /* O(n) in theory*/
        double ratio = (double) time / size;
        csv.append(String.format(new Locale("ru"), "DetSelect;%d;%s;%d;%d;%d;%.2f\n",
                size, type, time, select.getMaxDepth(), select.getComparisons(), ratio));
    }

    /*closest pair experiment*/
    private static void closestPairTest(int size, FileWriter csv) throws IOException {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(random.nextDouble(), random.nextDouble());
        }

        ClosestPairSolver solver = new ClosestPairSolver();
        long start = System.nanoTime();
        solver.solve(points);
        long time = System.nanoTime() - start;

        double expected = size * (Math.log(size) / Math.log(2));
        double ratio = time / expected;

        csv.append(String.format(new Locale("ru"), "ClosestPair;%d;Random;%d;%d;%d;%.2f\n",
                size, time, solver.getMaxDepth(), solver.getDistance(), ratio));
    }

    /*data generator*/
    private static int[] createInput(int size, String type) {
        int[] arr = new int[size];
        switch (type) {
            case "Random": for (int i = 0; i < size; i++) arr[i] = random.nextInt(); break;
            case "Sorted": for (int i = 0; i < size; i++) arr[i] = i; break;
            case "Reverse": for (int i = 0; i < size; i++) arr[i] = size - i; break;
            case "Duplicate": for (int i = 0; i < size; i++) arr[i] = random.nextInt(5); break;
        }
        return arr;
    }
}