import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class ClosestPairSolverTest {
    @Test
    public void test() {
        ClosestPairSolver solver = new ClosestPairSolver();
        Random random = new Random();

        Point[] points = new Point[1500];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }

        double fast = solver.solve(points);
        double brute = solver.solveBruteForce(points);

        assertEquals(brute, fast, 1e-9);
    }
}