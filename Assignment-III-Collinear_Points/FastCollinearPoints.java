import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        validateNulls(points);

        Point[] sorted = Arrays.copyOf(points, points.length);
        Arrays.sort(sorted);
        int len = sorted.length;

        validateDuplicates(points);

        for (int i = 0; i < len; i++) {
            Point p = sorted[i];

            Point[] pointsBySlope = Arrays.copyOf(sorted, len);
            Arrays.sort(pointsBySlope, p.slopeOrder());

            int j = 1;
            while (j < len) {
                List<Point> group = new ArrayList<>();
                double lastSlope = p.slopeTo(pointsBySlope[j]);

                do {
                    group.add(pointsBySlope[j++]);
                } while (j < len && p.slopeTo(pointsBySlope[j]) == lastSlope);

                if (group.size() >= 3 && p.compareTo(group.get(0)) < 0) {
                    lineSegments.add(new LineSegment(group.get(0), group.get(group.size() - 1)));
                }

            }
        }
    }

    private void validateNulls (Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("points cannot contain null values");
            }
        }
    }

    private void validateDuplicates (Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("points cannot have duplicates");
            }
        }
    }



    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] segments = new LineSegment[lineSegments.size()];
        return lineSegments.toArray(segments);
    }
}