package com.company;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

import java.util.Arrays;

public class Fast2 {

    public Fast2(int N, int[] data){

        String[] results = new String[4];
        int counter_results = 1;

        // Initialize and read in N many data points into org_arr  (original array)
        Point[] org_arr = new Point[N];
        int counter=0;
        for (int i=0;i<(2*N);i=i+2) {
            org_arr[counter] = new Point(data[i],data[i+1]);
            counter++;
        }


        Arrays.sort(org_arr);  // sort array according to the coordinate system


        Point[] arr = new Point[N];
        int origin_index = 0;  // index of origin point in array

        for (int i = 0; i<N; i++){     // for each point in the system -- O(N)

            for (int k = 0 ; k<N; k++){     // copy org_arr to arr -- O(N)
                arr[k] = org_arr[k];
            }

            //sort the array by slope compared to the point at the origin index
            Point origin_point = org_arr[origin_index];
            Arrays.sort(arr, origin_point.SLOPE_ORDER);    //  origin point itself will be -Infinity -- O(N log(N))
            origin_index++;


            Point[] arr_in_line = new Point[8];   // array for Points in line with the origin
            arr_in_line[0] = origin_point;

            int j = 0;
            int j_limit = N-2;               // array length i.e. limit on counter
            while (j < j_limit){             // Go through every point in the slope order -- O(N)
                counter= 2;                  // counting the points have the same slope with the origin

                while ((origin_point.slopeTo(arr[j]) == origin_point.slopeTo(arr[j+1])) ){   // while next three points have the same slope and j not out of range

                    if (counter == 2){  // first in the line

                        // place the two points in the array
                        arr_in_line[1] = arr[j];
                        arr_in_line[2] = arr[j+1];

                    }
                    else{
                        if (counter >= arr_in_line.length){   // if the array isn't big enough then double it's size
                            arr_in_line = resize_point(arr_in_line);
                        }
                        arr_in_line[counter] = arr[j+1];

                    }

                    counter++;
                    j++;

                    if (j==j_limit+1){ // if end of array then break
                        break;
                    }

                }

                if (counter > 3){
                    Arrays.sort(arr_in_line,0,counter);   // sort array inline

                    String result_tmp = arr_to_String(arr_in_line,counter);  // turn to string


                    if (not_in_results(results, counter_results, result_tmp)){   // if not yet found
                        if (counter_results == results.length ) {                 // if the array isn't big enough then double it's size
                            results = resize_string_arr(results);
                        }
                        results[counter_results-1] = result_tmp;
                        counter_results++;

                        System.out.println(result_tmp);

                    }

                    arr_in_line = clear_arr(arr_in_line,8);   // reset array
                    arr_in_line[0] = origin_point;

                }

                j++;

            }
        }
    }



    private Point[] resize_point(Point[]arr){
        // Doubles the capacity of Point[] arr and copies its element to it
        Point[] new_arr = new Point [arr.length*2];  // doubles the array capacity
        for (int i = 0; i<arr.length; i++){
            new_arr[i] = arr[i];
        }
        return new_arr;
    }


    private Point[] clear_arr(Point[]arr,int N){
        // Returns clean new N long Point class array
        Point[] new_arr = new Point [N];
        return new_arr;
    }

    private String[] resize_string_arr(String[]arr){
        // Doubles the capacity of String[] arr and copies its element to it
        String[] new_arr = new String [arr.length*2];  // doubles the array capacity
        for (int i = 0; i<arr.length; i++){
            new_arr[i] = arr[i];
        }
        return new_arr;
    }

    private String arr_to_String(Point[]arr, int count){
        // Return the coordinates of Points classes in arr to desired format "(x1,y1) -> (x2,y2) -> ..."
        String str = "";
        for ( int i = 0; i<count ; i++){
            if (i!=count-1){                // if not last element
                str += arr[i].toString() + " -> ";
            }
            else{
                str += arr[i].toString();
            }

        }
        return str;
    }


    private boolean not_in_results(String[]results_arr, int count, String tmp_str){
        // Returns true if tmp_str is not in results_arr otherwise false
        for( int i = 0; i<count-1; i++){
            if (results_arr[i].equals(tmp_str)){
                return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {
        // read in from input and put them through Fast class
        In in = new In();
        Out out = new Out();
        int n = in.readInt();
        Point[] points = new Point[n];
        int[] data = new int[2*n];
        for (int i = 0; i < 2*n; i++) {
            data[i]=in.readInt();
        }
        Fast2 f = new Fast2(n,data);
    }
}
