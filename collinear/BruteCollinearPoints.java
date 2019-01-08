import edu.princeton.cs.algs4.StdIn;
import java.util.ArrayList;

public class BruteCollinearPoints {
    public static void main(String[] args) {
        ArrayList<Point> alPoints = new ArrayList<>();

        StdIn.readLine();

        for(String line : StdIn.readAllLines()) {
            String[] splitStr = line.trim().split("\\s+");

            int x = Integer.parseInt(splitStr[0]);
            int y = Integer.parseInt(splitStr[1]);

            Point p = new Point(x, y);

            alPoints.add(p);
        }

        Point[] points = new Point[alPoints.size()];

        alPoints.toArray(points);

        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
    }

    private LineSegment[] segments;

    private class PointPair extends LineSegment {

        private Point p1;
        private Point p2;
        private PointPair(Point p1, Point p2) {
            super(p1, p2);

            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object that) {
            if(that instanceof PointPair) {
                PointPair obj = (PointPair) that;

                return p1.compareTo(obj.p1) == 0 && p2.compareTo(obj.p2) == 0;
            }

            return false;
        }
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if(points == null || !hasNoDuplicatesOrNulls(points)) throw new IllegalArgumentException();

        ArrayList<PointPair> alPairs = new ArrayList<>();

        final int N = points.length;

        for(int i = 0; i < N; i++)
            for(int j = i + 1; j < N; j++)
                for(int k = j + 1; k < N; k++)
                    for(int l = k + 1; l < N; l++) {
                        PointPair pair = createPair(points[i], points[j], points[k], points[l]);

                        if(pair != null)
                            alPairs.add(pair);
                    }

        segments = toLineSet(alPairs);
    }

    private boolean hasNoDuplicatesOrNulls(Object[] arr) {
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == null) return false;

            for(int j = i + 1; j < arr.length; j++) {
                if(arr[i].equals(arr[j])) {
                    return false;
                }
            }
        }

        return true;
    }

    private LineSegment[] toLineSet(ArrayList<PointPair> pairs) {
        ArrayList<PointPair> alPairs = new ArrayList<>();

        boolean foundCopy;

        for(int i = 0; i < pairs.size(); i++) {
            foundCopy = false;
            PointPair p1 = pairs.get(i);

            for(int j = i + 1; j < pairs.size(); j++) {
                PointPair p2 = pairs.get(j);

                if(p1.equals(p2)) {
                    foundCopy = true;
                    break;
                }
            }

            if(foundCopy)
                continue;

            alPairs.add(p1);
        }

        LineSegment[] newSegments = new LineSegment[alPairs.size()];

        alPairs.toArray(newSegments);

        return newSegments;
    }

    private Point getMaxPoint(Point[] points) {
        if(points.length == 0) return null;

        Point max = points[0];

        for(Point p : points) {
            if(max.compareTo(p) < 0)
                max = p;
        }

        return max;
    }

    private Point getMinPoint(Point[] points) {
        if(points.length == 0) return null;

        Point min = points[0];

        for(Point p : points) {
            if(min.compareTo(p) > 0)
                min = p;
        }

        return min;
    }

    private PointPair createPair(Point p1, Point p2, Point p3, Point p4) {
        double slope1 = p1.slopeTo(p2);
        double slope2 = p1.slopeTo(p3);
        double slope3 = p1.slopeTo(p4);

        Point[] points = new Point[] { p1, p2, p3, p4 };

        if(slope1 == slope2 && slope2 == slope3)
            return new PointPair(getMinPoint(points), getMaxPoint(points));

        return null;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }
}
