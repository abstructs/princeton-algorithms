import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
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

        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);

        System.out.println(collinearPoints.numberOfSegments());
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

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if(points == null || !hasNoDuplicatesOrNulls(points)) throw new IllegalArgumentException();

        Point[] sortArray = points.clone();

        ArrayList<PointPair> alPairs = new ArrayList<>();

        final int N = points.length;

        for(int i = 0; i < N; i++) {
            Point origin = points[i];

            Arrays.sort(sortArray, origin.slopeOrder());

            ArrayList<Point> linePoints = getEqualPoints(origin, sortArray);

            linePoints.add(origin);

            PointPair pair = createPair(linePoints);

            if(pair != null) {
                alPairs.add(pair);
            }
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

    private PointPair createPair(ArrayList<Point> points) {
        PointPair pair = null;

        if(points.size() >= 4) {
            Point minPoint = getMinPoint(points);
            Point maxPoint = getMaxPoint(points);

            pair = new PointPair(minPoint, maxPoint);
        }

        return pair;
    }

    private ArrayList<Point> getEqualPoints(Point origin, Point[] points) {
        ArrayList<Point> pointsWithEqualSlopes = new ArrayList<>();

        Arrays.sort(points, origin.slopeOrder());

        // matching doesn't include the origin point so we look for a set of "3"
        for(int i = 0; i < points.length - 2; i++) {
            if(origin.slopeTo(points[i]) == origin.slopeTo(points[i + 2])) {
                pointsWithEqualSlopes.add(points[i]);
                pointsWithEqualSlopes.add(points[i + 1]);
                pointsWithEqualSlopes.add(points[i + 2]);

                int j = i + 3;
                // collect all equal points and stop
                while(j < points.length && origin.slopeTo(points[i]) == origin.slopeTo(points[j])) {
                    pointsWithEqualSlopes.add(points[j]);
                    j++;
                }

                break;
            }
        }

        return pointsWithEqualSlopes;
    }

    private Point getMaxPoint(Iterable<Point> points) {
        Point max = null;

        for(Point p : points) {
            if(max == null)
                max = p;
            else if(max.compareTo(p) > 0)
                max = p;
        }

        return max;
    }

    private Point getMinPoint(Iterable<Point> points) {
        Point min = null;

        for(Point p : points) {
            if(min == null)
                min = p;
            else if(min.compareTo(p) < 0)
                min = p;
        }

        return min;
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
