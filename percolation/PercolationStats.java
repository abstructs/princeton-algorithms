import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;

public class PercolationStats {
    private Percolation percolationObject;
    private int trials;
    private int gridSize;
    private ArrayList<Double> openSiteFractions;

    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("Please provide 2 integer arguments");
            System.out.println("Example: PercolationStats 200 100");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);

        System.out.print("mean: ");
        System.out.println(percolationStats.mean());

        System.out.print("stddev: ");
        System.out.println(percolationStats.stddev());

        System.out.print("95% confidence interval: ");
        System.out.print("[");
        System.out.print(percolationStats.confidenceLo());
        System.out.print(", ");
        System.out.print(percolationStats.confidenceHi());
        System.out.println("]");
    }

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.gridSize = n;
        this.trials = trials;
        this.openSiteFractions = new ArrayList<>();
        this.percolationObject = new Percolation(n);

        runTrials();
    }

    private void runTrials() {
        int i, j;

        while(!percolationObject.percolates() && trials > percolationObject.numberOfOpenSites()) {
            i = StdRandom.uniform(gridSize) + 1;
            j = StdRandom.uniform(gridSize) + 1;

            if(!percolationObject.isOpen(i, j)) {
                percolationObject.open(i, j);
                openSiteFractions.add((double) percolationObject.numberOfOpenSites() / trials);
            }
        }
    }

    private double[] getOpenSiteFractions() {
        double[] doubleArray = new double[openSiteFractions.size()];
        int i = 0;

        for(double fraction : openSiteFractions)
            doubleArray[i++] = fraction;

        return doubleArray;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(getOpenSiteFractions());
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(getOpenSiteFractions());
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96d * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96d * stddev() / Math.sqrt(trials);
    }
}
