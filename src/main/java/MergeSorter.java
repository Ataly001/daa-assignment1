public class MergeSorter {
    /*required metrics*/
    private static final int CUTOFF = 16;
    private long comparisons;
    private int[] auxBuffer;
    private int maxDepth;

    public void sort(int[] array) {
        /*reset in each call*/
        this.comparisons = 0;
        this.maxDepth = 0;

        /*base case*/
        if (array == null || array.length <= 1) {
            return;
        }

        this.auxBuffer = new int[array.length];
        mergeSort(array, 0, array.length - 1, 1);
    }

    /*divide and conquer*/
    private void mergeSort(int[] array, int left, int right, int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
        if (right - left + 1 <= CUTOFF) {
            insertionSort(array, left, right);
            return;
        }
        int middle = left + (right - left) / 2;

        mergeSort(array, left, middle, depth + 1);
        mergeSort(array, middle + 1, right, depth + 1);
        comparisons++;

        if (array[middle] <= array[middle + 1]) {
            return;
        }
        merge(array, left, middle, right);
    }

    /*Linear merge*/
    private void merge(int[] array, int left, int middle, int right) {
        for (int i = left; i <= right; i++) {
            auxBuffer[i] = array[i];
        }
        int leftIndex = left;
        int rightIndex = middle + 1;
        int i = left;

        while (leftIndex <= middle && rightIndex <= right) {
            comparisons++;
            if (auxBuffer[leftIndex] <= auxBuffer[rightIndex]) {
                array[i++] = auxBuffer[leftIndex++];
            } else {
                array[i++] = auxBuffer[rightIndex++];
            }
        }
        while (leftIndex <= middle) {
            array[i++] = auxBuffer[leftIndex++];
        }
        while (rightIndex <= right) {
            array[i++] = auxBuffer[rightIndex++];
        }
    }

    /*for small arrays cutoff*/
    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= left) {
                comparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
    }
    /* getters for experiments in Csv*/
    public long getComparisons() {
        return comparisons;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
}
