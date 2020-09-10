package com.company;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;



public class Percolation {
    int size;
    int NOpen = 0;
    int[][] grid;

    private WeightedQuickUnionUF WQF;

    public Percolation(int N) {
        if (N<=0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        grid = new int[N][N];                        //Blocked = 0, Open = 1, Flow = 2

        WQF = new WeightedQuickUnionUF(size*size+1);

    }

    public void print_grid() {
        // prints out grid an flowGrid

        // Printing Grid out
        System.out.println("Grid:");
        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(grid[i]));
        }

        // printing flowGrid
        System.out.println("\nflowGrid:");
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                System.out.print(WQF.find(j * size + i) + " ");

            }
            System.out.print("\n");
        }
        System.out.print("\n");


    }



    public void open(int row, int col) {
    // Opens the site at row,col
        if ((row > (size-1)) || (col > (size-1)) || (col < 0) || (row < 0)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        else if (grid[row][col] == 0) {   // if blocked
            int open_index = row * size + col;   // newly open index
            int adj_index;                       // adjacent index

            boolean fullOrOpen;

            if (row==0){    // if in first row
                WQF.union(open_index,size*size);
            }

            //Checking all sites around, to find the lowest number
            fullOrOpen = withinBounds(row - 1, col);
            if (fullOrOpen) {
                adj_index = (row - 1) * size + col;   // takes the pointer at the location
                WQF.union(open_index, adj_index);
            }
            fullOrOpen = withinBounds(row, col + 1);
            if (fullOrOpen) {
                adj_index = row * size + (col + 1);
                WQF.union(open_index, adj_index);
            }
            fullOrOpen = withinBounds(row, col - 1);
            if (fullOrOpen) {
                adj_index = row * size + col - 1;
                WQF.union(open_index, adj_index);
            }
            fullOrOpen = withinBounds(row + 1, col);
            if (fullOrOpen) {
                adj_index = (row + 1) * size + col;
                WQF.union(open_index, adj_index);
            }

            NOpen += 1;  // update number of open sites

            if (WQF.connected(open_index,size*size)) {
                grid[row][col]=2;
            }
            else{
                grid[row][col]=1;
            }

        }



    }

    public boolean withinBounds(int target_row, int target_col) {
    // Returns True if (target_row, target_col) is within grid boundaries and is either open or full, otherwise false

        if (target_row < 0 || target_row > size - 1 || target_col < 0 || target_col > size - 1) {
            return false;
        }
        return isOpen(target_row, target_col) || isFull(target_row, target_col);
    }



    public boolean isOpen(int row, int col) {
    // Returns true if site at (row,col) is within boundaries and is open
        if ((row > (size-1)) || (col > (size-1)) || (col < 0) || (row < 0)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        else if (grid[row][col] > 0) {
            return true;
        } else return false;
    }


    public boolean isFull(int row, int col) {
    // Returns true if site at (row,col) is within boundaries and is full
        if ((row > (size-1)) || (col > (size-1)) || (col < 0) || (row < 0)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        //else if (grid[row][col] == 2) {
        else if (WQF.connected(row*size+col,size*size)){
            return true;
        } else return false;
    }

    public int numberOfOpenSites() {
    // Returns the number of open/full sites
        return NOpen;
    }

    public boolean percolates() {
        // Return true if the system percolates, otherwise false
        for (int i = size*(size-1); i<size*(size-1)+size; i++){
            if (WQF.connected(i,size*size)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int N = 5;
        Percolation P = new Percolation(N);



    }
}