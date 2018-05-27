import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96d;
    private final double mean;
    private final double stddev;
    private final double lo;
    private final double hi;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n,
                            int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("T must be greater than 0.");
        }
        double[] threshold = new double[trials];
        int row, col;
        Percolation perc;
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(n);
            do {
                do {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                } while (perc.isOpen(row, col));
                perc.open(row, col);
            } while (!perc.percolates());
            threshold[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);
        double halfDeltaConfidence95 = CONFIDENCE_95 * stddev / Math.sqrt(trials);
        lo = mean - halfDeltaConfidence95;
        hi = mean + halfDeltaConfidence95;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return hi;
    }

    // test client (described below)
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java-algs4 PercolationStats <size_of_lattice> <number_of_trials>");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        System.out.println();
        double mean = stats.mean();
        double stddev = stats.stddev();
        double lo = stats.confidenceLo();
        double hi = stats.confidenceHi();
        System.out.printf("mean                    = %f%n", mean);
        System.out.printf("stddev                  = %f%n", stddev);
        System.out.printf("95%% confidence interval = [%f, %f]%n", lo, hi);
    }

}
