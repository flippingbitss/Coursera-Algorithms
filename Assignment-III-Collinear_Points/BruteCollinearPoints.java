import java.util.ArrayList;
import java.util.Arrays;


public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        validateNulls(points);

        Point[] sorted = Arrays.copyOf(points, points.length);
        Arrays.sort(sorted);
        int len = sorted.length;

        validateDuplicates(points);

        // finds all line segments containing 4 points
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int l = k + 1; l < len; l++) {
                        Point p1 = sorted[i], p2 = sorted[j], p3 = sorted[k], p4 = sorted[l];
                        if (p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p2) == p1.slopeTo(p4))
                            lineSegments.add(new LineSegment(p1, p4));
                    }
                }
            }
        }
    }


    private void validateNulls(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("points cannot contain null values");
            }
        }
    }

    private void validateDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("points cannot have duplicates");
            }
        }
    }


    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }
}