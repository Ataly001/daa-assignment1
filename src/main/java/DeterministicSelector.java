public class DeterministicSelector {
    /*metrics*/
    private long comparisons;
    private int maxDepth;

    /*selector*/
    public int select(int[] array, int k) {
        this.comparisons = 0;
        this.maxDepth = 0;
        int index = selectIndex(array, 0, array.length - 1, k, 1);
        return array[index];
    }
    /*main algorithm*/
    private int selectIndex(int[] arr, int left, int right, int k, int depth) {
        if (depth > maxDepth){
            maxDepth = depth;
        }
        if (left == right) {
            return left;
        }

        /*groups of 5*/
        int medianCount = 0;
        for (int i = left; i <= right; i += 5) {
            int end = Math.min(i + 4, right);

            /*insertion sort*/
            for (int j = i + 1; j <= end; j++) {
                int key = arr[j];
                int p = j - 1;
                while (p >= i) {
                    comparisons++;
                    if (arr[p] > key) {
                        arr[p + 1] = arr[p];
                        p--;
                    } else break;
                }
                arr[p + 1] = key;
            }
            /*median placement*/
            int medianIndex = i + (end - i) / 2;
            swap(arr, left + medianCount, medianIndex);
            medianCount++;
        }

        /*median pivot*/
        int pivotIndex;
        if (medianCount == 1) {
            pivotIndex = left;
        } else {
            pivotIndex = selectIndex(arr, left, left + medianCount - 1,
                    left + medianCount / 2, depth + 1);
        }

        /*3-way partition*/
        int pivot = arr[pivotIndex];
        int lesser = left;
        int greater = right;
        int currentIndex = left;

        while (currentIndex <= greater) {
            comparisons++;
            if (arr[currentIndex] < pivot) {
                swap(arr, lesser, currentIndex);
                lesser++;
                currentIndex++;
            } else {
                comparisons++;
                if (arr[currentIndex] > pivot) {
                    swap(arr, currentIndex, greater);
                    greater--;
                } else {
                    currentIndex++;
                }
            }
        }

        /*recurse only into partition*/
        if (k >= lesser && k <= greater) {
            return lesser;
        } else if (k < lesser) {
            return selectIndex(arr, left, lesser - 1, k, depth + 1);
        } else {
            return selectIndex(arr, greater + 1, right, k, depth + 1);
        }
    }

    /*swapper*/
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /*getter for experiments*/
    public long getComparisons() { return comparisons; }
    public int getMaxDepth() { return maxDepth; }
}