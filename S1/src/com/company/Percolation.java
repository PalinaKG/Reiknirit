package com.company;

import java.util.Arrays;

public class Percolation {
    int size;
    int NOpen=0;
    int[][] grid;
    int[] flowGrid;
    public Percolation(int N){
        size=N;
        grid = new int[N][N]; //Blocked = 0, Open = 1, Flow = 2
        flowGrid = new int[size*size];
        for (int i=0;i<(size*size);i++) {
            flowGrid[i]=size*size;
        }
    }

    public void print_grid(){
        // prints out array
         for (int i=0;i<size;i++) {
             for (int j = 0; j < size; j++) {
                 if (flowGrid[i * size + j] < size) {
                     grid[i][j] = 2;
                 }
             }
         }

        for (int i = 0; i<size; i++){
            System.out.println(Arrays.toString(grid[i]));
        }
        System.out.println("\n");
        for (int j=0;j<size;j++)
        {   for (int i=0;i<size;i++) {
                System.out.println(flowGrid[j*size+i]);
        }
        }



    }

    public void open(int row, int col){
        if (row>size || col>size || col<0 || row<0){
            throw new java.lang.IndexOutOfBoundsException();
        }
        else if (grid[row][col]==0)   {
            grid[row][col]=1;
             int min=row*size+col;
             int number;
             flowGrid[row*size+col]=row*size+col;
             boolean fullOrOpen=false;


            fullOrOpen=withinBounds(row-1,col);

            if (fullOrOpen==true) {
                number = flowGrid[(row-1)*size+col];

                if (min>number)    {
                    min=number;
                }
                else {
                    flowGrid[number]=min;
                    checkAll(number,min);
                }

            }



             fullOrOpen=withinBounds(row,col+1);
            if (fullOrOpen==true) {
                number=flowGrid[row*size+(col+1)];
                //grid[row][col]=2;
                if (min>number)    {
                    min=number;
                }
                else {
                    flowGrid[number]=min;
                    checkAll(number,min);
                }
            }
            fullOrOpen=withinBounds(row,col-1);
            if (fullOrOpen==true) {
                number=flowGrid[row*size+col-1];
                if (min>number)    {
                    min=number;
                }
                else {
                    flowGrid[number]=min;
                    checkAll(number,min);
                }
            }
            fullOrOpen=withinBounds(row+1,col);
            if (fullOrOpen==true) {
               number=flowGrid[(row+1)*size+col];
                if (min>number)    {
                    min=number;
                }
                else {
                    flowGrid[number]=min;
                    checkAll(number,min);
                }
            }
            NOpen+=1;



        }
        else {
            System.out.println("The block is already open");
        }
    }

    public boolean withinBounds(int target_row, int target_col) {
        System.out.println(target_col);
        System.out.println(target_row);

        if (target_row<0 || target_row>size-1 || target_col<0 || target_col>size-1){
            return false;
        }
        return isOpen(target_row,target_col) || isFull(target_row,target_col);
    }

    public void checkAll(int prevNum, int num) {
        for (int i=0;i<size*size;i++) {
            if (flowGrid[i]==prevNum){
                flowGrid[i]=num;
            }
        }
    }

    public boolean isOpen(int row, int col) {
         if (row>size || col>size || col<0 || row<0) {
             throw new java.lang.IndexOutOfBoundsException();
         }
         else if (grid[row][col]==1) {
              return true;
         }
         else return false;
         }


    public boolean isFull(int row, int col) {


          if (row>size || col>size || col<0 || row<0) {
              throw new java.lang.IndexOutOfBoundsException();
          }
          else if (grid[row][col]==2) {
               return true;
          }
          else return false;
          }

    public int numberOfOpenSites() {
        return NOpen;
    }

    public boolean percolates() {

        for (int i=0;i<size;i++) {
            /*for (int j=0;j<size;j++) {
                if (flowGrid[i*size+j]<size) {
                    grid[i][j]=2;
                }
            }  */
            if (flowGrid[size*(size-1)+i]<size)
            {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //int N = 10;
        //int[][] arr = new int[N][N];

        int N = 5;
        Percolation P = new Percolation(N);
        //Percolation P;
        P.print_grid();
        P.open(1,2);
        P.open(3,4);
        P.open(4,2);
        P.open(0,1);
        P.open(1,1);
        P.open(2,1);
        P.open(3,1);
        P.open(3,2);

       P.print_grid();
       System.out.println(P.percolates());
       System.out.println(P.isOpen(0,2));
       System.out.println(P.isOpen(1,2));







        //System.out.println(Arrays.deepToString(arr));

    }



}
