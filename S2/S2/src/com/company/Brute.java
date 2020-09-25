package com.company;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;



public class Brute {

    public Brute(int N, int[] data) {
        Point a[] = new Point[N];
        int counter=0;
        //Array is made with instances of the Point class
        for (int i=0;i<(2*N);i=i+2) {
            a[counter] = new Point(data[i],data[i+1]);
            counter++;
        }
        Insertion.sort(a);

        //All the points are compared to each other, 4 at a time
        for (int i=0;i<(N-3);i++) {
            for (int j=i+1;j<(N-2);j++) {
                for (int k=j+1;k<(N-1);k++) {
                    for (int l=k+1;l<N;l++) {
                        Point p = a[i];
                        Point q = a[j];
                        Point r = a[k];
                        Point s = a[l];
                        boolean coll = collinear(p, q, r, s); //checking if collinear
                        if (coll) { //If they are collinear, they are printed out
                            printColl(p, q, r, s);
                        }
                    }
                }
            }
        }
    }

    public void printColl(Point p, Point q, Point r, Point s) {
        Point a[] = new Point[4];
        a[0]=p;
        a[1]=q;
        a[2]=r;
        a[3]=s;
        Insertion.sort(a);
        System.out.println(a[0].toString() + " -> " + a[1].toString() + " -> " + a[2].toString() + " -> "  + a[3].toString() );
    }


    public boolean collinear(Point p, Point q, Point r, Point s) {
        double pq_pr = p.SLOPE_ORDER.compare(q,r); //The comparator used to compare the slopes
        double pq_ps = p.SLOPE_ORDER.compare(q,s);
        double pr_ps = p.SLOPE_ORDER.compare(q,s);
        if ((pq_pr==0)&&(pq_ps==0)&&(pr_ps==0)){ //==0 means that the slopes are equal
            return true;
        }
        return false;
    }



    public static void main(String[] args) {
        // write your code here
        In in = new In();
        Out out = new Out();
        int n = in.readInt();
        //int n=150;
        Point[] points = new Point[n];
        int[] data = new int[2*n];
        for (int i = 0; i < 2*n; i++) {
            data[i]=in.readInt();
        }
        Brute brute=new Brute(n,data);
    }

   /* public static void main(String[] args) {   // for monte carlo time test thing
        // write your code here
        Stopwatch t = new Stopwatch();
        In in = new In();
        Out out = new Out();
        //int n = in.readInt();
        int n=800;
        Point[] points = new Point[n];
        int[] data = new int[2*n];
        for (int i = 0; i < 2*n; i++) {
            data[i]=StdRandom.uniform(50);
        }
        Brute brute=new Brute(n,data);

        System.out.println("TIME: " + t.elapsedTime());
    }*/
}
