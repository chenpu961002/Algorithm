public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        double[][] slopes = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                slopes[i][j] = points[i].slopeTo(points[j]);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j == i) continue;
                for (int k = 0; k < N; k++) {
                    if (k == j || k == i) continue;
                    for (int l = 0; l < N; l++) {
                        if (l == k || l == j || l == i) continue;
                        double slope12 = slopes[i][j];
                        double slope13 = slopes[i][k];
                        double slope14 = slopes[i][l];
                        if (slope12 == slope13 && slope12 == slope14) {
                            if (points[i].compareTo(points[j]) <= 0 && points[j].compareTo(points[k]) <= 0 && points[k].compareTo(points[l]) <= 0) {
                                System.out.println(points[i].toString() + " -> " + points[j].toString() + " -> "
                                                   + points[k].toString() + " -> " + points[l].toString());
                                points[i].drawTo(points[l]);
                            }
                        }
                    }
                }
            }
        }
        StdDraw.show();
    }
}
