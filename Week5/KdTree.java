public class KdTree {
    private Node root;
    private int size;

    private class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private boolean isVertical;
        public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean isVertical) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.isVertical = isVertical;
        }

        public Point2D getPoint() {
            return p;
        }

        public RectHV getRect() {
            return rect;
        }

        public boolean getIsVertical() {
            return isVertical;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return 0 == size;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (null == p) throw new java.lang.NullPointerException();
        if (null == root) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null, true);
            size++;
            return;
        }
        Node currentNode = root;
        double xmin, ymin, xmax, ymax;
        boolean isVertical = true;

        while (true) {
            Point2D currentPoint = currentNode.getPoint();
            isVertical = currentNode.getIsVertical();
            if (currentPoint.equals(p)) return;
            // left
            else if (isVertical && p.x() < currentPoint.x()) {
                if (null == currentNode.lb) {
                    xmin = currentNode.getRect().xmin();
                    xmax = currentPoint.x();
                    ymin = currentNode.getRect().ymin();
                    ymax = currentNode.getRect().ymax();
                    currentNode.lb = new Node(p, new RectHV(xmin, ymin, xmax, ymax),
                                              null, null, !isVertical);
                    size++;
                    return;
                }
                currentNode = currentNode.lb;
                // right
            } else if (isVertical && p.x() >= currentPoint.x()) {
                if (null == currentNode.rt) {
                    xmin = currentPoint.x();
                    xmax = currentNode.getRect().xmax();
                    ymin = currentNode.getRect().ymin();
                    ymax = currentNode.getRect().ymax();
                    currentNode.rt = new Node(p, new RectHV(xmin, ymin, xmax, ymax),
                                              null, null, !isVertical);
                    size++;
                    return;
                }
                currentNode = currentNode.rt;
                // bottom
            } else if (p.y() < currentPoint.y()) {
                if (null == currentNode.lb) {
                    xmin = currentNode.getRect().xmin();
                    xmax = currentNode.getRect().xmax();
                    ymin = currentNode.getRect().ymin();
                    ymax = currentPoint.y();
                    currentNode.lb = new Node(p, new RectHV(xmin, ymin, xmax, ymax),
                                              null, null, !isVertical);
                    size++;
                    return;
                }
                currentNode = currentNode.lb;
                // top
            } else {
                if (null == currentNode.rt) {
                    xmin = currentNode.getRect().xmin();
                    xmax = currentNode.getRect().xmax();
                    ymin = currentPoint.y();
                    ymax = currentNode.getRect().ymax();
                    currentNode.rt = new Node(p, new RectHV(xmin, ymin, xmax, ymax),
                                              null, null, !isVertical);
                    size++;
                    return;
                }
                currentNode = currentNode.rt;
            }
        }
    }

    public boolean contains(Point2D p) {
        if (null == p) throw new java.lang.NullPointerException();
        Node currentNode = root;
        while (null != currentNode) {
            Point2D currentPoint = currentNode.getPoint();
            boolean isVertical = currentNode.getIsVertical();
            if (currentPoint.equals(p)) return true;
            else if (isVertical && p.x() < currentPoint.x())
                currentNode = currentNode.lb;
            else if (isVertical && p.x() >= currentPoint.x())
                currentNode = currentNode.rt;
            else if (p.y() < currentPoint.y())
                currentNode = currentNode.lb;
            else currentNode = currentNode.rt;
        }
        return false;
    }

    public void draw() {
        if (isEmpty()) return;
        Queue<Node> drawQueue = new Queue<Node>();
        drawQueue.enqueue(root);

        while (!drawQueue.isEmpty()) {
            Node currentNode = drawQueue.dequeue();

            // push children into queue
            if (null != currentNode.lb)
                drawQueue.enqueue(currentNode.lb);
            if (null != currentNode.rt)
                drawQueue.enqueue(currentNode.rt);

            // plot
            Point2D currentPoint = currentNode.getPoint();
            boolean isVertical = currentNode.getIsVertical();
            Point2D point1, point2;
            if (isVertical) {
                point1 = new Point2D(currentPoint.x(), currentNode.getRect().ymin());
                point2 = new Point2D(currentPoint.x(), currentNode.getRect().ymax());
                StdDraw.setPenColor(StdDraw.RED);
            } else {
                point1 = new Point2D(currentNode.getRect().xmin(), currentPoint.y());
                point2 = new Point2D(currentNode.getRect().xmax(), currentPoint.y());
                StdDraw.setPenColor(StdDraw.BLUE);
            }
            StdDraw.setPenRadius(0.005);
            point1.drawTo(point2);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            currentPoint.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> pointsQueue = new Queue<Point2D>();
        if (isEmpty()) return pointsQueue;
        rangeSearch(pointsQueue, rect, root);
        return pointsQueue;
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;
        Stack<Node> nodes = new Stack<Node>();
        nodes.push(root);
        Node nearestNode = root;
        double nearestDis = Double.MAX_VALUE;
        while (!nodes.isEmpty()) {
            Node currentNode = nodes.pop();
            // whether to prune
            if (nearestDis < currentNode.getRect().distanceSquaredTo(p))
                continue;

            // update distance
            double tmpDis = currentNode.getPoint().distanceSquaredTo(p);
            if (nearestDis > tmpDis) {
                nearestDis = tmpDis;
                nearestNode = currentNode;
            }

            // which first, stack, LIFO
            if (null != currentNode.lb && null == currentNode.rt)
                nodes.push(currentNode.lb);
            else if (null == currentNode.lb && null != currentNode.rt)
                nodes.push(currentNode.rt);
            else if (null != currentNode.lb && null != currentNode.rt) {
                double leftBottomDis = p.distanceSquaredTo(currentNode.lb.getPoint());
                double rightTopDis = p.distanceSquaredTo(currentNode.rt.getPoint());
                if (leftBottomDis < rightTopDis) {
                    nodes.push(currentNode.rt);
                    nodes.push(currentNode.lb);
                } else {
                    nodes.push(currentNode.lb);
                    nodes.push(currentNode.rt);
                }
            }
        }
        return nearestNode.getPoint();
    }

    private void rangeSearch(Queue<Point2D> pointsQueue, RectHV rect, Node currentNode) {
        if (null == currentNode) return;
        if (!rect.intersects(currentNode.getRect())) return;

        if (rect.contains(currentNode.getPoint()))
            pointsQueue.enqueue(currentNode.getPoint());

        rangeSearch(pointsQueue, rect, currentNode.lb);
        rangeSearch(pointsQueue, rect, currentNode.rt);
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        System.out.println("begin to construct");
        Stopwatch t1 = new Stopwatch();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.println("finish constructing" + t1.elapsedTime());

        t1 = new Stopwatch();
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            Point2D query = new Point2D(Math.random(), Math.random());
            Point2D result = kdtree.nearest(query);
            // System.out.println(query.toString() + "->" + result.toString());
        }
        double elapse = t1.elapsedTime();
        System.out.println("total time " + elapse);
        System.out.println("rate " + times / elapse);
    }
}
