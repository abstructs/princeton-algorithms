import java.util.Random;

public class PercolationStats {
    private Percolation percolationObject;
    private int trials;
    private int timesSiteWasOpened;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        percolationObject = new Percolation(n);
        Random randObj = new Random();
        this.trials = trials;

        int i, j;

        while(!percolationObject.percolates() || timesSiteWasOpened < trials) {
            timesSiteWasOpened++;

            i = randObj.nextInt(n);
            j = randObj.nextInt(n);

            percolationObject.open(i, j);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.timesSiteWasOpened / this.trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    public static void main(String[] args) {

    }
}
