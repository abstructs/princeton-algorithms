/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

    private Node root;
    private int size;

    private static class Node {
        private Node lb;
        private Node rt;
        private Point2D point;
//        private RectHV rect;
        private int rank;

        private Node(Point2D point, int rank) {
            this.point = point;
            this.rank = rank;
        }

        private RectHV getRect() {
            if(lb == null && rt == null) return null;
            Point2D a;
            Point2D b;
            if(lb != null && rt != null) {
                a = lb.point;
                b = rt.point;
            } else {
                a = lb != null ? lb.point : rt.point;
                b = point;
            }

            return new RectHV(Math.min(a.x(), b.x()), Math.min(a.y(), b.y()),
                    Math.max(a.x(), b.x()), Math.max(a.y(), b.y()));
        }

        private boolean isVertical() {
            return rank % 2 == 0;
        }

        private boolean isHorizontal() {
            return rank % 2 != 0;
        }
    }

    // construct an empty set of points
    public KdTree() {
        this.size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        this.root = insert(p, root, 0);
        size++;
    }

    private Node insert(Point2D p, Node root, int rank) {
        if(root == null) {
            return new Node(p, rank);
        }

        if(root.isVertical()) {
            if(root.point.x() > p.x()) {
                root.lb = insert(p, root.lb, rank + 1);
            } else {
                root.rt = insert(p, root.rt, rank + 1);
            }
        } else {
            if (root.point.y() < p.y()) {
                root.lb = insert(p, root.lb, rank + 1);
            } else {
                root.rt = insert(p, root.rt, rank + 1);
            }
        }

        return root;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return contains(p, root);
    }

    private boolean contains(Point2D p, Node root) {
        if(root == null) return false;
        if (root.point.equals(p)) return true;

        if (root.isVertical()) {
            if (root.point.x() > p.x()) {
                return contains(p, root.lb);
            } else {
                return contains(p, root.rt);
            }
        } else {
            if (root.point.y() < p.y()) {
                return contains(p, root.lb);
            } else {
                return contains(p, root.rt);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root);
    }

    private void draw(Node root) {
        if(root == null) return;

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledCircle(root.point.x(), root.point.y(), 0.005);

        RectHV rect = root.getRect();

        if(rect != null) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            rect.draw();
        }

        draw(root.lb);
        draw(root.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointsInRange = new ArrayList<>();
        range(rect, root, pointsInRange);
        return pointsInRange;
    }

    private boolean inRange(Point2D point, RectHV rect) {
        if(rect == null || point == null) throw new IllegalArgumentException();

        return point.x() >= rect.xmin() && point.x() <= rect.xmax() &&
                point.y() >= rect.ymin() && point.y() <= rect.ymax();
    }

    private void range(RectHV rect, Node root, List<Point2D> pointsInRange) {
        if(root == null) return;

        if(inRange(root.point, rect)) {
            pointsInRange.add(root.point);
        }

        RectHV rtRect = root.rt != null ? root.rt.getRect() : null;
        RectHV lbRect = root.lb != null ? root.lb.getRect() : null;

        if(rtRect != null && rtRect.intersects(rect)) {
            range(rect, root.rt, pointsInRange);
        }

        if(lbRect != null && lbRect.intersects(rect)) {
            range(rect, root.lb, pointsInRange);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }
}
