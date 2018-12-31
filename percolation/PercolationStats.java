import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

//import java.util.ArrayList;

public class PercolationStats {
//    private Percolation percolationObject;
    private int trials;
    private int gridSize;
    private int n;
    private int trialsRan;
    private double[] openSiteFractions;

    public static void main(String[] args) {
        if (args.length <= 1) {
            System.out.println("Please provide 2 integer arguments");
            System.out.println("Example: PercolationStats 200 100");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

//        System.out.println(args[1]);

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

//    private ArrayList<Integer> blockedSites;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.gridSize = n * n;
        this.n = n;
        this.trials = trials;
        this.openSiteFractions = new double[trials];
        this.trialsRan = 0;

        for(int i = 0; i < trials; i++)
            runTrial();
    }

//    private int index(int row, int col) {
//        return row * ((int) Math.sqrt(gridSize)) + col;
//    }

    private int deIndexCol(int index) {
        return index % n;
    }

    private int deIndexRow(int index) {
        return (index - deIndexCol(index)) / n;
    }

    private void runTrial() {
        Percolation percolationObject = new Percolation(n);

        int timesOpened = 0;
//        int trialsRan = 0;

        while (!percolationObject.percolates()) {
            int randomIndex = StdRandom.uniform(0, gridSize);

            int row = deIndexRow(randomIndex) + 1;
            int col = deIndexCol(randomIndex) + 1;

            if(percolationObject.isOpen(row, col)) {
                continue;
            }

            percolationObject.open(row, col);
            timesOpened++;
        }

        openSiteFractions[trialsRan++] = ((double) timesOpened) / gridSize;
    }

//    private double[] getOpenSiteFractions() {
//        double[] doubleArray = new double[openSiteFractions.size()];
//        int i = 0;
//
//        for(double fraction : openSiteFractions)
//            doubleArray[i++] = fraction;
//
//        return doubleArray;
//    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSiteFractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSiteFractions);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96d * stddev() / trials;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96d * stddev() / trials;
    }
}
