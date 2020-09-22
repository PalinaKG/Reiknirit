import com.company.Percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] arr_open;
    private double avg, dev, temp;
    private int rand_row, rand_col;
    private Percolation p;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        double N2 = N*N;
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException();
        }
        arr_open = new double[T];

        for (int i = 0; i < T; i++) {
            p = new Percolation(N);
            while (!p.percolates()) {
                rand_row = StdRandom.uniform(N);
                rand_col = StdRandom.uniform(N);
                p.open(rand_row, rand_col);
            }
            arr_open[i] = p.numberOfOpenSites()/N2;
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        avg = StdStats.mean(arr_open);
        return avg;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        dev = StdStats.stddev(arr_open);
        return dev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        temp = 1.96 * dev / Math.sqrt(arr_open.length);
        return avg - temp;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return avg + temp;
    }

    public static void main(String[] args) {

        PercolationStats Test1 = new PercolationStats(200,100);

        StdOut.println("mean(): " + Test1.mean());

    }
}