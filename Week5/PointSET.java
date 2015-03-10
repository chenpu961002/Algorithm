public class PointSET {
    private SET<Point2D> points;
    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (null == p) throw new java.lang.NullPointerException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (null == p) throw new java.lang.NullPointerException();
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points)
            point.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> result = new Queue<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p))
                result.enqueue(p);
        }
        return result;
    }

    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        double nearestDis = Double.MAX_VALUE;
        for (Point2D pt : points) {
            double tmp = pt.distanceSquaredTo(p);
            if (tmp < nearestDis) {
                nearestDis = tmp;
                nearestPoint = pt;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {

    }
}
