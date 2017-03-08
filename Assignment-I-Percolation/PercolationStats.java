import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Manpreet Singh Matharu
 * @date 2017-02-25
 */
public class PercolationStats {
    private double[] thresholds;

    // perform trials independent thresholds on an n-by-n grid
    public PercolationStats(int n, int trials) {
        thresholds = new double[trials];
        int total = n * n;
        int openSites = 0;
        int x, y;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                do {
                    x = getRandom(n);
                    y = getRandom(n);
                }
                while (p.isOpen(x, y) && openSites < total);
                p.open(x, y);
            }
            openSites = p.numberOfOpenSites();

            thresholds[i] = (double) openSites / total;
        }

    }

    private static int getRandom(int max) {
        return StdRandom.uniform(1, max + 1);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    public static void main(String[] args) {
        PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean                     = %f\n", pls.mean());
        System.out.printf("stddev                   = %f\n", pls.stddev());
        System.out.printf("95%% confidence Interval  = %f, %f\n", pls.confidenceLo(), pls.confidenceHi());
    }


}