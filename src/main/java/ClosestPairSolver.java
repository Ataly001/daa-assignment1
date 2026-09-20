import java.util.*;

public class ClosestPairSolver {
    /*metrics*/
    private long distance;
    private int maxDepth;
    private Point[] auxBuffer;

    /*selector*/
    public double solve(Point[] points) {
        this.distance = 0;
        this.maxDepth = 0;

        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] sortX = Arrays.copyOf(points, points.length);
        Arrays.sort(sortX, Comparator.comparingDouble(p -> p.x));
        this.auxBuffer = new Point[points.length];

        return closestPair(sortX, 0, sortX.length - 1, 1);
    }

    /*main algorithm*/
    private double closestPair(Point[] points, int left, int right, int depth) {
        if (depth > maxDepth){
            maxDepth = depth;
        }

        if (right - left <= 2) {
            insertionSortY(points, left, right);
            return bruteForce(points, left, right);
        }

        int mid = left + (right - left) / 2;
        double midX = points[mid].x;

        double dl = closestPair(points, left, mid, depth + 1);
        double dr = closestPair(points, mid + 1, right, depth + 1);
        double d = Math.min(dl, dr);

        mergeY(points, left, mid, right);

        int stripSize = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - midX) < d) {
                auxBuffer[stripSize++] = points[i];
            }
        }

        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (auxBuffer[j].y - auxBuffer[i].y) < d; j++) {
                double dist = distance(auxBuffer[i], auxBuffer[j]);
                if (dist < d) {
                    d = dist;
                }
            }
        }
        return d;
    }

    /*merge by y*/
    private void mergeY(Point[] array, int left, int middle, int right) {
        for (int i = left; i <= right; i++) {
            auxBuffer[i] = array[i];
        }
        int leftIndex = left, rightIndex = middle + 1, i = left;

        while (leftIndex <= middle && rightIndex <= right) {
            if (auxBuffer[leftIndex].y <= auxBuffer[rightIndex].y){
                array[i++] = auxBuffer[leftIndex++];
                }
            else array[i++] = auxBuffer[rightIndex++];
        }
        while (leftIndex <= middle) array[i++] = auxBuffer[leftIndex++];
        while (rightIndex <= right) array[i++] = auxBuffer[rightIndex++];
    }

    /*insertion sort by y*/
    private void insertionSortY(Point[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Point key = array[i];
            int j = i - 1;
            while (j >= left && array[j].y > key.y) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    /*brute force*/
    public double solveBruteForce(Point[] points) {
        this.distance = 0;
        return bruteForce(points, 0, points.length - 1);
    }

    /*brute force helper*/
    private double bruteForce(Point[] points, int left, int right) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < min){
                    min = dist;
                }
            }
        }
        return min;
    }

    /*distance*/
    private double distance(Point p1, Point p2) {
        distance++;
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /*getters for experiments*/
    public long getDistance() { return distance; }
    public int getMaxDepth() { return maxDepth; }
}