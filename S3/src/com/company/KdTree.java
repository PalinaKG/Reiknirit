
/*************************************************************************
 *************************************************************************/
package com.company;
import java.util.Arrays;
import java.lang.Math;

import edu.princeton.cs.algs4.*;

public class KdTree {
    // construct an empty set of points
    Node root;
    int size;

    class Node{
        Point2D data,upper_point, lower_point;
        Node r_child;
        Node l_child;
        Node(Point2D data_value, Node leftChild, Node rightChild, Point2D upper,Point2D lower)
        {
            data=data_value;
            l_child=leftChild;
            r_child=rightChild;
            upper_point=upper;
            lower_point=lower;
        }
    }

    public KdTree() {
        root=new Node(null,null,null, null, null);
        size=0;
    }

    // is the set empty?
    public boolean isEmpty()
    {
        if (size==0)
        {
            return true;
        }
        return false;
    }

    // number of points in the set
    public int size() { return size; }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        double[] x_values={0,1};
        double[] y_values={0,1};
        if (size==0)
        {
            root.data=p;
            root.lower_point=new Point2D(root.data.x(),y_values[0]);
            root.upper_point=new Point2D(root.data.x(),y_values[1]);
        }
        else
        {
            int counter=0;
            insert_to_tree(p,root,counter, x_values, y_values,true);
            //StdOut.println(root.l_child.data);
        }
        size++;
    };

    private void insert_to_tree(Point2D insert_p,Node cur_p, int counter, double[] x_values, double[] y_values, boolean ifLeft)
    {
        double cur_point=0;
        double insert_point=0;

        counter++;

        if (counter%2==0)
        {
            cur_point=cur_p.data.y(); //Comaring y values if counter is odd
            insert_point=insert_p.y();
        }
        else
        {
            cur_point=cur_p.data.x(); //Comparing x values if counter is even
            insert_point=insert_p.x();
        }

        //StdOut.println("INSERT_POINT: " + insert_point + " CURRENT_POINT: " + cur_point);

        if (insert_point<=cur_point)
        {
            if (cur_p.l_child!=null)
            {
                insert_to_tree(insert_p, cur_p.l_child,counter,x_values,y_values,true);
            }
            else
            {
                Point2D first_point,second_point;
                if (counter%2==0)
                {
                    y_values[1]=cur_p.data.y();
                    //StdOut.println("y1: " + y_values[1]);
                    first_point=new Point2D(insert_p.x(),y_values[0]);
                    second_point=new Point2D(insert_p.x(),y_values[1]);
                }
                else
                {
                    x_values[1]=cur_p.data.x();
                    //StdOut.println("x1: " + x_values[1]);
                    first_point=new Point2D(x_values[0],insert_p.y());
                    second_point=new Point2D(x_values[1],insert_p.y());
                }
                //StdOut.println("FIRST: " + first_point);
                //StdOut.println("SECOND: " + second_point);
                cur_p.l_child=new Node(insert_p,null,null, first_point, second_point);
                //StdOut.println("PARENT: " + cur_p.data + " LEFT CHILD: " + cur_p.l_child.data + " UPPER: " + cur_p.l_child.upper_point + " LOWER: " + cur_p.l_child.lower_point);

            }

        }
        else
        {
            if (cur_p.r_child!=null)
            {
                insert_to_tree(insert_p, cur_p.r_child,counter,x_values,y_values,false);
            }
            else
            {
                Point2D first_point,second_point;
                if (counter%2==0)
                {

                    y_values[0]=cur_p.data.y();
                    //StdOut.println("y0: " + y_values[0]);
                    first_point=new Point2D(insert_p.x(),y_values[0]);
                    second_point=new Point2D(insert_p.x(),y_values[1]);
                }
                else
                {
                    x_values[0]=cur_p.data.x();
                    //StdOut.println("x0: " + x_values[0]);
                    first_point=new Point2D(x_values[0],insert_p.y());
                    second_point=new Point2D(x_values[1],insert_p.y());
                }
                //StdOut.println("FIRST: " + first_point);
                //StdOut.println("SECOND: " + second_point);
                cur_p.r_child=new Node(insert_p,null,null, first_point,second_point);
                //StdOut.println("PARENT: " + cur_p.data + " RIGHT CHILD: " + cur_p.r_child.data + " UPPER: " + cur_p.r_child.upper_point + " LOWER: " + cur_p.r_child.lower_point);
            }
        }

    }

    // does the set contain the point p?
    public boolean contains(Point2D p)
    {
        if (size==0)
        {
            return false;
        }
        else
        {
            int counter=0;
            return contains_recur(p,root,counter);
        }
    }

    private boolean contains_recur(Point2D p, Node cur_p, int counter)
    {
        double cur_point=0;
        double insert_point=0;
        //double other_cur_point=0;
        //double other_insert_point=0;
        if (counter%2==0)
        {
            cur_point=cur_p.data.x();
            insert_point=p.x();
            //other_cur_point=cur_p.data.y();
            //other_insert_point=p.y();
        }
        else
        {
            cur_point=cur_p.data.y();
            insert_point=p.y();
            //other_cur_point=cur_p.data.x();
            //other_insert_point=p.x();
        }
        counter++;

        if (p.equals(cur_p.data))
        {
            return true;
        }

        else if (insert_point<=cur_point)
        {

            if (cur_p.l_child!=null)
            {
                return contains_recur(p, cur_p.l_child,counter);
            }
            else
            {
                return false;
            }

        }
        else
        {
            if (cur_p.r_child!=null)
            {
                return contains_recur(p, cur_p.r_child,counter);
            }
            else
            {
                return false;
            }
        }
    }

    // draw all of the points to standard draw
    public void draw()
    {
        int counter=0;
        StdDraw.setPenRadius(0.01);
        draw_recur(root,counter);

    }

    private int draw_recur(Node p,int counter)
    {
        StdOut.println(p);

        if (p==null)
        {
            return 0;
        }
        StdOut.println(p.data);
        StdOut.println(p.upper_point + "    " + p.lower_point);
        if (counter%2==0)
        {
            StdDraw.setPenColor(StdDraw.RED);

        }
        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        counter++;
        p.upper_point.drawTo(p.lower_point);

        StdDraw.setPenColor(StdDraw.BLACK);
        p.data.draw();
        draw_recur(p.l_child,counter);
        draw_recur(p.r_child,counter);
        StdDraw.show();
        return 0;
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> setInRange;
        setInRange = new SET<Point2D>();
        int counter=-1;
        setInRange = range_recur(root, setInRange, counter, rect);

        return setInRange;
    }

    private SET<Point2D> range_recur(Node p, SET <Point2D> set, int counter, RectHV rect)
    {
        if (p==null)
        {
            return set;
        }
        counter++;
        double min,max, cur_point;
        if (counter%2==0)
        {
            cur_point=p.data.x(); //Comparing x values if counter is even
            min=rect.xmin();
            max=rect.xmax();
        }
        else
        {
            cur_point=p.data.y(); //Comaring y values if counter is odd
            min=rect.ymin();
            max=rect.ymax();
        }
        if (rect.contains(p.data))
        {
            set.add(p.data);
        }

        if (min < cur_point) {
            range_recur(p.l_child, set, counter, rect);
        }
        if (max > cur_point) {
            range_recur(p.r_child, set, counter, rect);
        }
        return set;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearest_point=root.data;
        if (root.data==null)
        {
            return nearest_point;
        }
        else
        {
            int counter=-1;
            double nearest_len=p.distanceTo(nearest_point);
            nearest_point=nearest_recur(p,root, nearest_point, counter, nearest_len);
            return nearest_point;

        }

    }

    private Point2D nearest_recur(Point2D p,Node cur_p,Point2D nearest_point,int counter, double nearest_len)
    {
       if (cur_p==null)
       {
           return nearest_point;
       }
       counter++;
       double new_len=cur_p.data.distanceTo(p);
       if (new_len<nearest_len)
       {
           nearest_len=new_len;
           nearest_point=cur_p.data;
       }

        double point, cmp_point0, cmp_point1,other_point, calc_len;
        if (counter%2==0)
        {
            point=p.y(); //Comparing x values if counter is even
            calc_len=p.x();
            cmp_point0=cur_p.lower_point.y();
            cmp_point1=cur_p.upper_point.y();
            other_point=cur_p.data.x();
        }
        else
        {
            point=p.x(); //Comaring y values if counter is odd
            calc_len=p.y();
            cmp_point0=cur_p.lower_point.x();
            cmp_point1=cur_p.upper_point.x();
            other_point=cur_p.data.y();
        }
        if ((cmp_point0>point) && (cmp_point1>point)) //Checking if
        {
            new_len=p.distanceTo(cur_p.lower_point);
        }
        else if ((cmp_point0<point) && (cmp_point1<point))
        {
            new_len=p.distanceTo(cur_p.upper_point);
        }
        else
        {
            new_len=Math.abs(calc_len-other_point);
        }
        if (nearest_len>new_len)
        {
            if (calc_len>other_point)
            {
                nearest_point=nearest_recur(p, cur_p.r_child, nearest_point, counter, nearest_len);
            }
            else
            {
                nearest_point=nearest_recur(p, cur_p.l_child, nearest_point, counter, nearest_len);

            }
        }
    return nearest_point;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        /*KdTree kd = new KdTree();
        Point2D p1=new Point2D(0.7,0.2);
        Point2D p2=new Point2D(0.5,0.4);
        Point2D p3=new Point2D(0.2,0.3);
        Point2D p4=new Point2D(0.4,0.7);
        Point2D p5=new Point2D(0.9,0.6);

        kd.insert(p1);
        kd.insert(p2);
        kd.insert(p3);
        kd.insert(p4);
        kd.insert(p5);


        //kd.draw();
        //RectHV rect=new RectHV(0.1,0.1,0.5,0.7);
        //StdOut.println(kd.range(rect));
        StdOut.println(kd.nearest(new Point2D(0.5,0.3)));*/

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
        KdTree set = new KdTree();
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


