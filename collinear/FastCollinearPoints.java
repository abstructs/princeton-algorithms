/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    public static void main(String[] args) {
        final int N = 12;
        Point[] points = new Point[N];

        for(int i = 0; i < N; i++) {
            points[i] = new Point(1, 2);
        }

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        System.out.println(collinearPoints.numberOfSegments());
    }

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        Arrays.sort(points);

        ArrayList<LineSegment> alSegments = new ArrayList<LineSegment>();

        final int N = points.length;

        for(int i = 0; i < N - 3; i++) {
            LineSegment segment = createSegement(points[i], points[i + 1], points[i + 2], points[i + 3]);

            if(segment != null) {
                alSegments.add(segment);
            }
        }

        segments = new LineSegment[alSegments.size()];

        alSegments.toArray(segments);
    }

    private LineSegment createSegement(Point p1, Point p2, Point p3, Point p4) {
        double slope1 = p1.slopeTo(p2);
        double slope2 = p2.slopeTo(p3);
        double slope3 = p3.slopeTo(p4);

        if(slope1 == slope2 && slope2 == slope3)
            return new LineSegment(p1, p4);

        return null;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}
