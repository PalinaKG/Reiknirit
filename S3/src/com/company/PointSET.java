package com.company;

/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:
 *  Dependencies:
 *  Author:
 *  Date:
 *
 *  Data structure for maintaining a set of 2-D points,
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/

import java.awt.*;
import java.util.Arrays;
import edu.princeton.cs.algs4.*;

public class PointSET {
    // construct an empty set of points
    private SET<Point2D> set;
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    // number of points in the set
    public int size()
    {
        return set.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        set.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p)
    {
        return set.contains(p);
    }

    // draw all of the points to standard draw
    public void draw()
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point : set) //Iterating through each point in set
        {
            point.draw(); //Drawing each point in set
        }
        StdDraw.show();
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        SET<Point2D> setInRange;
        setInRange = new SET<Point2D>(); //saving all the points in a set
        for (Point2D point: set)
        {
            if (rect.contains(point))
            {
                setInRange.add(point); //Adding the point to the set if it is within the rectangle
            }
        }
        return setInRange;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p)
    {
        if (set.size()==0)
        {
            return null;
        }
        int counter=0;
        Point2D min_point=new Point2D(0,0);
        for (Point2D point:set)
        {
            if (counter==0)
            {
                if (point!=p)
                {
                    min_point=point; //Adding the first point, that is not the point being compared to, as the min point
                    counter++;
                }

            }
            else
            {
                if ((point.distanceTo(p)<min_point.distanceTo(p) && (point!=p) )) //Comparing the distance with all the nodes and saving the min
                {
                    min_point=point;
                }
                counter++;
            }


        }
        return min_point;
    }

    public static void main(String[] args) {
        /*In in = new In();
        PointSET set = new PointSET();
        for (int i = 0; i<10; i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));

        }

        set.draw();
        for (Point2D p:set.set)
        {
            Point2D nearest_point = set.nearest(p);
            StdOut.println("POINT: "+p);
            StdOut.println(nearest_point);
            StdDraw.setPenColor(StdDraw.RED);
            nearest_point.draw();
            StdDraw.setPenColor(StdDraw.BLUE);
            p.draw();
            break;
        }*/





        In in = new In();
        Out out = new Out();
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        PointSET set = new PointSET();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();

    }

}
