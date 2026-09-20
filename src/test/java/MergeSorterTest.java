import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MergeSorterTest {
    @Test
    public void test() {
        MergeSorter sorter = new MergeSorter();
        Random random = new Random();

        int[][] testInputs = {
                {}, {42}, {1, 2, 3}, {5, 4, 3}, {3, 1, 3, 3, 2}, // Edge cases
                random.ints(10, -100, 100).toArray(),
                random.ints(1000, -1000, 1000).toArray(),
                random.ints(100000, -10000, 10000).toArray()
        };

        for (int[] arr : testInputs) {
            int[] expected = arr.clone();
            Arrays.sort(expected);
            sorter.sort(arr);
            assertArrayEquals(expected, arr);
        }
    }
}