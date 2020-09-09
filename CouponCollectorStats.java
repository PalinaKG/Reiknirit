package com.company;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;
import java.lang.Math;

public class CouponCollectorStats {

    public static void main(String[] args) {
        int N = 0;
        int n_boxes = 0;

        ////// part a
        // for N = 100000
        double t_tot = 0.0;  // total time
        N = 100000;
        StdOut.println("a)\nTimes and number of boxes for N = 100000");
        for (int i=0; i<3; i++) {
            Stopwatch t1 = new Stopwatch(); // set timer
            n_boxes = couponCollectorTest(N); // call function
            t_tot += t1.elapsedTime();
            StdOut.println("time:" + t1.elapsedTime() + " -- number of boxes:" + n_boxes);
        }
        StdOut.println("Mean time: " + t_tot/3 + "\n");


        // for N = 1000000
        t_tot = 0.0;
        N = 1000000;
        StdOut.println("Times for N = 1000000");
        for (int i=0; i<3; i++) {
            Stopwatch t1 = new Stopwatch(); // set timer
            n_boxes = couponCollectorTest(N); // call function
            t_tot += t1.elapsedTime();
            StdOut.println("time:" + t1.elapsedTime() + " -- number of boxes:" + n_boxes);
        }
        StdOut.println("Mean time: " + t_tot/3);



        ///// part c
        StdOut.println("\n\nc)");
        N = 100000;
        int T[] = {10,100,1000};
        for (int i = 0; i<3; i++){
            StdOut.println("T=" + T[i]);
            double[] stats = couponCollectorStats(N, T[i]);
            StdOut.println("mean:" + stats[0] + " -- stddev:" + stats[1] + "\n");

        }

    }




    public static int couponCollectorTest(int N){

        // initializing N big vector
        boolean [] bool_arr = new boolean[N];
        Arrays.fill(bool_arr, false);

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

        // initalizing the array for storing the time duration
        double [] time_arr = new double[T];
        Arrays.fill(time_arr, 0);

        for (int i = 0; i<T ; i++){
            Stopwatch t1 = new Stopwatch(); // set timer
            couponCollectorTest(N); // call function
            time_arr[i] = t1.elapsedTime();
            //StdOut.println("time:" + time_arr[i] + " -- n_boxes:" + n_boxes);
        }

        double stats[] = new double[2];
        stats[0] = mean(time_arr, T);
        stats[1] = stddev(time_arr,T,stats[0]);

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

