package com.company;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;
import java.lang.Math;

public class Main {

    public static void main(String[] args) {
        int N = 0;
        int n_boxes = 0;

        ////// part a
        // for N = 100000
        double t = 0.0;
        N = 100000;
        StdOut.println("Times and number of boxes for N = 100000");
        for (int i=0; i<3; i++) {
            Stopwatch t1 = new Stopwatch(); // set timer
            //n_boxes = couponCollectorTest(N); // call function
            t += t1.elapsedTime();
            StdOut.println("time:" + t1.elapsedTime() + " -- n_boxes:" + n_boxes);
            //StdOut.println(t);
        }
        StdOut.println("Mean time: " + t/3 + "\n");


        // for N = 1000000
        t = 0.0;
        N = 1000000;
        StdOut.println("Times for N = 1000000");
        for (int i=0; i<3; i++) {
            Stopwatch t1 = new Stopwatch(); // set timer
            //n_boxes = couponCollectorTest(N); // call function
            t += t1.elapsedTime();
            StdOut.println("time:" + t1.elapsedTime() + "- n_boxes:" + n_boxes);
            //StdOut.println(t);
        }
        StdOut.println("Mean time: " + t/3);



        ///// part c
        N = 100000;
        int T[] = {10,100,1000};
        // double[] stats;
        for (int i = 0; i<3; i++){
            StdOut.println("\nT=" + T[i]);
            double[] stats = couponCollectorStats(N, T[i]);
            StdOut.println("mean:" + stats[0] + " -- stddev:" + stats[1]);

        }
    }



    public static int couponCollectorTest(int N){

        // initializing N big vector
        boolean [] bool_arr = new boolean[N];
        Arrays.fill(bool_arr, false);

        //StdOut.println(Arrays.toString(bool_arr));

        int counter = 0;
        boolean all_true = false;
        int i;
        while (!all_true){   // while the all the elements of the array aren't all true
            i = StdRandom.uniform(N);
            bool_arr[i] = true;
            counter++;
            if (N<=counter){
                all_true = if_all_true(bool_arr);  // update boolean all_true
            }
            //StdOut.println(all_true);
            //StdOut.println(i);
            //StdOut.println(Arrays.toString(bool_arr));
        }

        return counter;
    }

    public static boolean if_all_true(boolean arr[]){
    // Goes through the boolean array arr, returns true if arr contains false otherwise true
        int len_arr = arr.length;
        for (int j=0; j<len_arr; j++){
            if (arr[j]==false) {
                return false;
            }
        }
        return true;

    }



    public static double[] couponCollectorStats(int N, int T){
        double t = 0.0;
        int n_boxes;

        double [] time_arr = new double[T];
        Arrays.fill(time_arr, 0);

        for (int i = 0; i<T ; i++){
            Stopwatch t1 = new Stopwatch(); // set timer
            n_boxes = couponCollectorTest(N); // call function
            time_arr[i] = t1.elapsedTime();
            StdOut.println("time:" + time_arr[i] + " -- n_boxes:" + n_boxes);
            //StdOut.println(t);
        }

        StdOut.println(Arrays.toString(time_arr));

        double stats[] = new double[2];
        stats[0] = mean(time_arr, T);
        stats[1] = stddev(time_arr,T,stats[0]);
        StdOut.println(Arrays.toString(stats));
        StdOut.println(StdStats.mean(time_arr) + " -- " + StdStats.stddev(time_arr));

        return stats;
    }

    public static double mean(double arr[],int n_time) {
        double sum_time = 0.0;
        for (int i = 0; i<n_time; i++) {
            sum_time += arr[i];
        }
        return sum_time/n_time;
    }

    public static double stddev(double arr[], int n_time, double mean_time) {
        double sum_inside = 0.0;
        double tmp_val = 0.0;
        for (int i = 0; i<n_time; i++){
            tmp_val = arr[i]-mean_time;
            sum_inside += Math.abs(tmp_val*tmp_val);
        }
        return Math.sqrt(sum_inside/(n_time-1));
    }

}

