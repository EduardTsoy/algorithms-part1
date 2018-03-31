import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] threshold;
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
        threshold = new double[trials];
        long maxRandomAttempts = 1000L * n * n;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            do {
                int row;
                int col;
                long attempt = 0;
                do {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                    attempt++;
                    if (attempt >= maxRandomAttempts) {
                        throw new IllegalStateException("Oops! I failed to randomly find a closed cell.");
                    }
                } while (perc.isOpen(row, col));
                perc.open(row, col);
            } while (!perc.percolates());
            threshold[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);
        lo = mean - 1.96 * stddev / Math.sqrt(trials);
        hi = mean + 1.96 * stddev / Math.sqrt(trials);
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
        Integer n = Integer.valueOf(args[0]);
        Integer t = Integer.valueOf(args[1]);
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
