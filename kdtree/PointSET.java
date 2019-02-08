/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.TreeSet;

public class PointSET {
    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        this.points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if(p == null) throw new IllegalArgumentException();

        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if(p == null) throw new IllegalArgumentException();

        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for(Point2D point : points) {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledCircle(point.x(), point.y(), 0.005);
        }
    }

    private boolean inRange(Point2D point, RectHV rect) {
        if(rect == null || point == null) throw new IllegalArgumentException();

        return point.x() >= rect.xmin() && point.x() <= rect.xmax() &&
                point.y() >= rect.ymin() && point.y() <= rect.ymax();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new IllegalArgumentException();

        Point2D bottomLeft = new Point2D(rect.xmin(), rect.ymin());
        Point2D topRight = new Point2D(rect.xmax(), rect.ymax());

        return points.subSet(bottomLeft, topRight);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if(p == null) throw new IllegalArgumentException();
        if(isEmpty()) return null;

        Point2D closestPoint = points.first();

        for(Point2D point : points)
            if(p.distanceTo(closestPoint) > p.distanceSquaredTo(point))
                closestPoint = point;

        return closestPoint;
    }
}
