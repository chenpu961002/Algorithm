/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE
    private  class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            double dif = slope1 - slope2;
            if (Double.isNaN(dif)) {
                return 0;
            } else if (0 == dif) {
                return 0;
            } else if (0 > dif) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        double difX = that.x - this.x;
        double difY = that.y - this.y;
        if (0 == difX && 0 == difY) {
            return Double.NEGATIVE_INFINITY;
        } else if (0 == difX) {
            return Double.POSITIVE_INFINITY;
        } else if (0 == difY) {
            return +0.0;
        } else {
            return difY / difX;
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        int difX = this.x - that.x;
        int difY = this.y - that.y;
        if (difY < 0) {
            return -1;
        } else if (difY > 0) {
            return 1;
        } else if (difX < 0) {
            return -1;
        } else if (difX > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point[] points = new Point[10];
        Point arch = new Point(0, 0);
        for (int i = 9; i >= 0; i--) {
            points[i] = new Point(1, 2);
        }
        Arrays.sort(points, arch.SLOPE_ORDER);
        for (int i = 0; i < 10; i++) {
            System.out.println(points[i]);
        }
    }
}
