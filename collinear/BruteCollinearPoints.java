/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class BruteCollinearPoints {
    public static void main(String[] args) {
        final int N = 12;
        Point[] points = new Point[N];

        for(int i = 0; i < N; i++) {
            points[i] = new Point(1, 2);
        }

        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);

        System.out.println(collinearPoints.numberOfSegments());
    }

    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        ArrayList<LineSegment> alSegements = new ArrayList<LineSegment>();

        final int N = points.length;

        for(int i = 0; i < N; i += 4)
            for(int j = 1; j < N; j += 4)
                for(int k = 2; k < N; k += 4)
                    for(int l = 3; l < N; l += 4) {
                        LineSegment segment = createSegement(points[i], points[j], points[k], points[l]);

                        if(segment != null)
                            alSegements.add(segment);
                    }

        segments = new LineSegment[alSegements.size()];

        alSegements.toArray(segments);
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
