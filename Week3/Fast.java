import java.util.Arrays;
public class Fast {
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
        SET<String> set = new SET<String>();

        for (int i = 0; i < N; i++) {
            // copy the array
            Point[] tmp = new Point[N];
            for (int j = 0; j < N; j++) {
                tmp[j] = points[j];
            }

            // sort the array
            Arrays.sort(tmp, points[i].SLOPE_ORDER);

            // construct the slop array
            double[] slopes = new double[N];
            for (int j = 0; j < N; j++) {
                slopes[j] = points[i].slopeTo(tmp[j]);
            }

            // skip the NEGATIVE_INFINITY item
            int header = 0;
            while (++header < N && slopes[header] == Double.NEGATIVE_INFINITY);

            // find the longest adjacent points
            while (header < N) {
                int tail = header;
                while (++tail < N && slopes[tail] == slopes[header]);
                int len = tail - header;
                if (len >= 3) {
                    Point [] selection = new Point[len + 1];
                    selection[0] = points[i];
                    for (int j = header; j < tail; j++) {
                        selection[j - header + 1] = tmp[j];
                    }

                    //sort and print
                    Arrays.sort(selection);

                    // if already print
                    String sb = selection[0].toString() + selection[len].toString();

                    if (!set.contains(sb)) {

                        for (int j = 0; j < len; j++) {
                            System.out.print(selection[j].toString() + " -> ");
                        }
                        System.out.println(selection[len]);
                        selection[0].drawTo(selection[len]);
                        set.add(sb);
                    }
                }
                header = tail;
            }
        }
        StdDraw.show();
    }
}
