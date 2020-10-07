
/*************************************************************************
 *************************************************************************/
package com.company;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.*;

public class KdTree {
    // construct an empty set of points
    Node root;
    int size;
    Point2D nearest_point;
    double nearest_len;


    class Node {
        Point2D data, upper_point, lower_point;
        Node r_child;
        Node l_child;

        Node(Point2D data_value, Node leftChild, Node rightChild, Point2D upper, Point2D lower) {
            data = data_value;           // point2D
            l_child = leftChild;
            r_child = rightChild;
            upper_point = upper;         // upper limit of this nodes line
            lower_point = lower;         // lower    --  //   --
        }
    }

    public KdTree() {
        // Initializing
        root = new Node(null, null, null, null, null);
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        double[] x_values = {0, 1};
        double[] y_values = {0, 1};
        if (size == 0) {            // if the set is empty
            root.data = p;
            root.lower_point = new Point2D(root.data.x(), y_values[0]);
            root.upper_point = new Point2D(root.data.x(), y_values[1]);
            size=1;
        }
        else {
            if (!contains(p)) {     // if it doesn't contain the point already
                int counter = 0;
                insert_to_tree(p, root, counter, x_values, y_values);
                size++;
            }


        }
    }

    ;

    private void insert_to_tree(Point2D insert_p, Node cur_p, int counter, double[] x_values, double[] y_values) {

        counter++;

        double cur_point = 0.0;             // relevant coordinate from cur_p to to its position (vertical or horizontal)
        double insert_point = 0.0;          //   --     //     --       insert_p     --  //   --

        if (counter % 2 == 0) {       // if the node is vertical i.e. red then we look at the y coordinates
            cur_point = cur_p.data.y();
            insert_point = insert_p.y();

        } else {                      // otherwise horizontal and we look at the x coordinates
            cur_point = cur_p.data.x();
            insert_point = insert_p.x();
        }

        Point2D first_point, second_point;  // upper and lower limit of the line of the node

        if (insert_point < cur_point) {     // if on the left side
            if (counter % 2 == 0) {         // node line is vertical
                y_values[1] = cur_p.data.y();   // update upper limit on y

                // create both upper and lower limit
                first_point = new Point2D(insert_p.x(), y_values[0]);
                second_point = new Point2D(insert_p.x(), y_values[1]);

            } else {   // node line is horizontal
                x_values[1] = cur_p.data.x();  // update upper limit on x
                first_point = new Point2D(x_values[0], insert_p.y());
                second_point = new Point2D(x_values[1], insert_p.y());
            }

            if (cur_p.l_child != null) {     // if there is already a node on the left side of the cur_p
                insert_to_tree(insert_p, cur_p.l_child, counter, x_values, y_values);   // recurr

            } else {     // otherwise create new node for point insert_p on the left side of cur_p
                cur_p.l_child = new Node(insert_p, null, null, first_point, second_point);
            }

        } else {        // otherwise on right side
            if (counter % 2 == 0) {   // node line is vertical
                y_values[0] = cur_p.data.y();     // update lower limit on y
                first_point = new Point2D(insert_p.x(), y_values[0]);
                second_point = new Point2D(insert_p.x(), y_values[1]);

            } else {       // node line is horizontal
                x_values[0] = cur_p.data.x();   // update lower limit on x
                first_point = new Point2D(x_values[0], insert_p.y());
                second_point = new Point2D(x_values[1], insert_p.y());
            }

            if (cur_p.r_child != null) {   // if already a node on the right child of cur_p
                insert_to_tree(insert_p, cur_p.r_child, counter, x_values, y_values);

            } else {        // otherwise create node with point insert_p on the right side of cur_p
                cur_p.r_child = new Node(insert_p, null, null, first_point, second_point);
            }
        }

    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (size == 0) {
            return false;
        } else {
            int counter = 0;
            return contains_recur(p, root, counter);
        }
    }

    private boolean contains_recur(Point2D p, Node cur_p, int counter) {
        double cur_point = 0;
        double insert_point = 0;

        if (counter % 2 == 0) {             // if the node is vertical i.e. red then we look at the x coordinates
            cur_point = cur_p.data.x();
            insert_point = p.x();
        } else {                            // otherwise horizontal and we look at the y coordinates
            cur_point = cur_p.data.y();
            insert_point = p.y();
        }
        counter++;

        if (p.equals(cur_p.data)) {         // if it found the point
            return true;
        } else if (insert_point < cur_point) {      // else if the point has smaller value than the current point

            if (cur_p.l_child != null) {
                return contains_recur(p, cur_p.l_child, counter); // go to left if is non-null
            } else {
                return false;               // if null then it was unsuccessful
            }

        } else {                                    // otherwise it is greater or equal
            if (cur_p.r_child != null) {
                return contains_recur(p, cur_p.r_child, counter);  // go right if non-null
            } else {
                return false;
            }
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        int counter = 0;
        StdDraw.setPenRadius(0.01);
        draw_recur(root, counter);

    }

    private int draw_recur(Node p, int counter) {

        if (p == null) {
            return 0;
        }
        if (counter % 2 == 0) {                 // red if it is vertical
            StdDraw.setPenColor(StdDraw.RED);

        } else {                                // otherwise blue
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        counter++;
        p.upper_point.drawTo(p.lower_point);    // draw between the points

        // Draw the point itself
        StdDraw.setPenColor(StdDraw.BLACK);
        p.data.draw();

        // recurr to both sides
        draw_recur(p.l_child, counter);
        draw_recur(p.r_child, counter);

        StdDraw.show(); // display results
        return 0;
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> setInRange;
        setInRange = new SET<Point2D>();
        int counter = -1;
        setInRange = range_recur(root, setInRange, counter, rect);

        return setInRange;
    }

    private SET<Point2D> range_recur(Node p, SET<Point2D> set, int counter, RectHV rect) {
        if (p == null) {
            return set;
        }
        counter++;
        double min, max, cur_point;
        if (counter % 2 == 0) {     // if the node is vertical i.e. red then we look at the x coordinates
            cur_point = p.data.x();
            min = rect.xmin();
            max = rect.xmax();
        } else {                    // other wise it is horizontal then we look at the y coordinates
            cur_point = p.data.y();
            min = rect.ymin();
            max = rect.ymax();
        }

        if (rect.contains(p.data)) {   // add the point to the set if it is in the rectangle range
            set.add(p.data);
        }

        if (min < cur_point) {          // if left / lower side of the rectangle is at the left side of the point
            range_recur(p.l_child, set, counter, rect); // check left side
        }
        if (max >= cur_point) {         // if right / upper side of the rectangle is on or at the right side of the point
            range_recur(p.r_child, set, counter, rect); // check right side
        }
        return set;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {

        nearest_point = root.data;  // is null if there is no data in the tree
        if (root.data != null) {   // if there is data in the tree
            int counter = -1;
            nearest_len = p.distanceSquaredTo(nearest_point);    // initializing nearest length
            nearest_recur(p, root, counter);
        }

        return nearest_point;
    }

    private void nearest_recur(Point2D p, Node cur_p, int counter) {
        // Finding the closest point in the tree to query point p recursively

        if (cur_p != null) {        // as long as there is a current point

            counter++;
            double new_len = cur_p.data.distanceSquaredTo(p);  // find length from current point to query point

            if (new_len < nearest_len) {                        // if current point is closer to query point
                nearest_len = new_len;                          // than the nearest_point then update
                nearest_point = cur_p.data;                     // nearest length and nearest point
            }
            double q_coord,q_coord_other; ;         // relative query coordinates for this nodes rotation (vertical or horizontal)
            double cmp_point0, cmp_point1;          // upper and lower limit of current point relative to its position
            double coord;                           // relative coordinate from current point to its position
            if (counter % 2 == 0) {                             // if the node has a vertical line i.e. red
                q_coord = p.y();
                q_coord_other = p.x();
                coord = cur_p.data.x();
                cmp_point0 = cur_p.lower_point.y();
                cmp_point1 = cur_p.upper_point.y();
            }
            else {                                              // if the node has a horizontal line i.e. blue
                q_coord = p.x();
                coord = cur_p.data.y();
                q_coord_other = p.y();
                cmp_point0 = cur_p.lower_point.x();
                cmp_point1 = cur_p.upper_point.x();
            }


            // Calculating distance to the box created by the current node sectioning for the check if it is
            // worth it to check on the other side of it
            if ((cmp_point0 > q_coord) && (cmp_point1 > q_coord)) // if the closest point from the query point to the
            {                                                     // box is on its upper / right corner
                new_len = p.distanceSquaredTo(cur_p.upper_point);

            } else if ((cmp_point0 < q_coord) && (cmp_point1 < q_coord)) {  // if it is the lower / left corner
                new_len = p.distanceSquaredTo(cur_p.lower_point);

            } else {                                                        // else it is the side of it
                new_len = q_coord_other - coord;
                new_len *= new_len;
            }

            // Assigning near and far side
            Node near_child, far_child;
            if (q_coord_other >= coord) {     // if q_coord_other is on the up / right side of the current position
                near_child = cur_p.r_child;   // then the right child of current position is near than the left one
                far_child = cur_p.l_child;
            } else {                          // otherwise it is the other way around
                near_child = cur_p.l_child;
                far_child = cur_p.r_child;
            }


            // Recursion down the tree
            nearest_recur(p, near_child, counter);    // Check near side first since it is more likely to contain nearest neighbour

            if (nearest_len > new_len) {              // Check far side if the box is closer than the current nearest length
                nearest_recur(p, far_child, counter);
            }

        }


    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/

    /**************    A      *************/
    /*public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int N = in.readInt(), C = in.readInt(), T = 20;
        KdTree tree = new KdTree();
        Point2D [] points = new Point2D[C];
        out.printf("Inserting %d points into tree\n", N);
        for (int i = 0; i < N; i++) {
            tree.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        out.printf("tree.size(): %d\n", tree.size());
        out.printf("Testing contains method, querying %d points\n", C);
        for (int i = 0; i < C; i++) {
            points[i] = new Point2D(in.readDouble(), in.readDouble());
            out.printf("%s: %s\n", points[i], tree.contains(points[i]));
        }
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < C; j++) {
                tree.contains(points[j]);
            }
        }
    }*/



    /**************    B      *************/
    /*public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int N = in.readInt(), C = in.readInt(), T = 50;
        Point2D[] queries = new Point2D[C];
        KdTree tree = new KdTree();
        out.printf("Inserting %d points into tree\n", N);
        for (int i = 0; i < N; i++) {
            tree.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        out.printf("tree.size(): %d\n", tree.size());
        out.printf("Testing `nearest` method, querying %d points\n", C);

        for (int i = 0; i < C; i++) {
            queries[i] = new Point2D(in.readDouble(), in.readDouble());
            out.printf("%s: %s\n", queries[i], tree.nearest(queries[i]));
        }
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < C; j++) {
                tree.nearest(queries[j]);
            }
        }
    }*/


    /**************    C      *************/
    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int N = in.readInt(), R = in.readInt(), T = 800;
        RectHV[] rectangles = new RectHV[R];
        KdTree tree = new KdTree();
        out.printf("Inserting %d points into tree\n", N);
        for (int i = 0; i < N; i++) {
            tree.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        out.printf("tree.size(): %d\n", tree.size());
        out.printf("Testing `range` method, querying %d rectangles\n", R);
        ArrayList<Point2D> range = new ArrayList<Point2D>();
        for (int i = 0; i < R; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
            out.printf("Points inside rectangle %s\n", rectangles[i]);
            for (Point2D point : tree.range(rectangles[i])) {
                range.add(point);
            }
            Collections.sort(range);
            for (Point2D point : range) {
                out.printf("%s\n", point);
            }
            range.clear();
        }
        for (int i = 0; i < T; i++) {
            for (int j = 0; j < rectangles.length; j++) {
                tree.range(rectangles[j]);
            }
        }
    }

}


