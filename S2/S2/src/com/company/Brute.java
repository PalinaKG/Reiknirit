package com.company;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Out;



public class Brute {

    public Brute(int N, int[] data) {
        Point a[] = new Point[N];
        int counter=0;
        for (int i=0;i<(2*N);i=i+2) {
            a[counter] = new Point(data[i],data[i+1]);
            counter++;
        }
        sort(a,0,N-1);
        //for (int i=0;i<N;i++) {
         //   System.out.println(data[i]);
        //}

        for (int i=0;i<(N-3);i++) {
            for (int j=i+1;j<(N-2);j++) {
                for (int k=j+1;k<(N-1);k++) {
                    for (int l=k+1;l<N;l++) {
                        //System.out.println("i: "+ i + " " + data[i] + " " + data[i+1]);
                        //System.out.println("j: "+ j + " " + data[j] + " " + data[j+1]);
                        //System.out.println("k: "+ k + " " + data[k] + " " + data[k+1]);
                        //System.out.println("l: "+ l + " " + data[l] + " " + data[l+1]);
                        Point p = a[i];
                        Point q = a[j];
                        Point r = a[k];
                        Point s = a[l];

                        boolean coll = collinear(p, q, r, s);
                        if (coll) {
                            printColl(p, q, r, s);
                        }
                    }
                }
            }
        }
    }

    public void printColl(Point p, Point q, Point r, Point s) {
        System.out.println(p.toString() + "->" + q.toString() + "->" + r.toString() + "->"  + s.toString() );
    }


    public boolean collinear(Point p, Point q, Point r, Point s) {

        double pq = p.slopeTo(q);
        double pr = p.slopeTo(r);
        double ps = p.slopeTo(s);
        if ((pq==pr)&&(pq==ps)&&(ps==pr)){
            return true;
        }
        return false;
    }

    public static int partition(Point[] a, int lo, int hi)
    {
        boolean bool=true;
        int com=0;
        int i = lo, j = hi+1;
        while (true)
        {
            while (bool)
                System.out.println(a[++i]);
                System.out.println(a[lo]);
                com=a[++i].compareTo(a[lo]);

                if (com==1)
                {
                    bool=true;
                }
                
                if (i == hi) break;

            while (bool)
                com=a[lo].compareTo(a[--j]);
                if (com==1)
                {
                    bool=true;
                }
                if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void exch(Point[] a, int i, int j) {
        Point swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    public static void sort(Point[]a, int lo, int hi)
    {
        int cutoff=10;
        if (hi <= lo + cutoff - 1)
        {
            Insertion.sort(a, lo, hi);
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a,j+1, hi);
    }

    public static void main(String[] args) {
	// write your code here
        In in = new In();
        Out out = new Out();
        int n = in.readInt();
        Point[] points = new Point[n];
        int[] data = new int[2*n];
        for (int i = 0; i < 2*n; i++) {
            data[i]=in.readInt();
        }
        Brute brute=new Brute(n,data);
    }
}
