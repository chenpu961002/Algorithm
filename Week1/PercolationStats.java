
public class PercolationStats {
    private double[]thresholds;
    private double mean = -1;
    private double stddev = -1;
    private double delta = -1;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        thresholds = new double[T];
        // StdRandom.setSeed(100);
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            int counter = 0;
            while (!p.percolates()) {
                int x = StdRandom.uniform(N) + 1;
                int y = StdRandom.uniform(N) + 1;
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    counter += 1;
                }
            }
            this.thresholds[i] = ((double) counter) / (N * N);
        }
        // calc mean
        double total = 0;
        for (int i = 0; i < T; i++) {
            total += thresholds[i];
        }
        this.mean = total / T;
        // calc stddev
        double stddevTmp = 0;
        for (int i = 0; i < T; i++) {
            stddevTmp += (thresholds[i] - this.mean) * (thresholds[i] - this.mean);
        }
        stddevTmp = stddevTmp / (T - 1);
        this.stddev = Math.sqrt(stddevTmp);
        this.delta = 1.96 * this.stddev / Math.sqrt(T);
        //printinfo
        // printInfo();
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public double confidenceLo() {
        return this.mean - this.delta;
    }

    public double confidenceHi() {
        return this.mean + this.delta;
    }

    private void printInfo() {
        System.out.println(mean());
        System.out.println(stddev());
        System.out.println(confidenceHi());
        System.out.println(confidenceLo());
    }

    public static void main(String[]args) {
        if (2 != args.length) {
            System.out.println("usage wrong");
        }
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        new PercolationStats(x, y);
    }
}
