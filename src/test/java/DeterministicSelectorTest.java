import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class DeterministicSelectorTest {

    @Test
    public void test() {
        DeterministicSelector selector = new DeterministicSelector();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int size = 10 + random.nextInt(100000);
            int[] arr = random.ints(size, -10000, 10000).toArray();
            int k = random.nextInt(size);

            int[] expected = arr.clone();
            Arrays.sort(expected);

            int result = selector.select(arr.clone(), k);
            assertEquals(expected[k], result);
        }
    }
}