import java.util.Random;
public class QuickSorter {

    /* required metrics */
    private long comparisons;
    private long swaps;
    private int maxDepth;
    private final Random random = new Random();

    public void sort(int[] array) {
        /* reset in each call */
        this.comparisons = 0;
        this.swaps = 0;
        this.maxDepth = 0;

        /* base case */
        if (array == null || array.length <= 1) {
            return;
        }

        quickSort(array, 0, array.length - 1, 1);
    }

    /* divide and conquer */
    private void quickSort(int[] array, int left, int right, int depth) {
        while (left < right) {
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            int pivotIndex = partition(array, left, right);

            if (pivotIndex - left < right - pivotIndex) {
                quickSort(array, left, pivotIndex - 1, depth + 1);
                left = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, right, depth + 1);
                right = pivotIndex - 1;
            }
        }
    }

    /* In-place partitioning */
    private int partition(int[] array, int left, int right) {
        int randomIndex = left + random.nextInt(right - left + 1);
        swap(array, randomIndex, right);
        int pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            comparisons++;
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, right);
        return i + 1;
    }

    /*swapper func*/
    private void swap(int[] array, int i, int j) {
        if (i != j) {
            swaps++;
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /* getters for experiments in Csv */
    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public int getMaxDepth() { return maxDepth; }
}