package com.company;

import edu.princeton.cs.algs4.QuickFindUF;

import java.util.Arrays;



public class Percolation {
    int size;
    int NOpen = 0;
    int[][] grid;

    private QuickFindUF QF;

    public Percolation(int N) {
        if (N<=0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        grid = new int[N][N];                        //Blocked = 0, Open = 1, Flow = 2

        QF = new QuickFindUF(size*size+1);

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
                System.out.print(QF.find(j * size + i) + " ");

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
                QF.union(open_index,size*size);
            }

            //Checking all sites around, to find the lowest number
            fullOrOpen = withinBounds(row - 1, col);
            if (fullOrOpen) {
                adj_index = (row - 1) * size + col;   // takes the pointer at the location
                minimum_union(open_index, adj_index);
            }
            fullOrOpen = withinBounds(row, col + 1);
            if (fullOrOpen) {
                adj_index = row * size + (col + 1);
                minimum_union(open_index, adj_index);
            }
            fullOrOpen = withinBounds(row, col - 1);
            if (fullOrOpen) {
                adj_index = row * size + col - 1;
                minimum_union(open_index, adj_index);
            }
            fullOrOpen = withinBounds(row + 1, col);
            if (fullOrOpen) {
                adj_index = (row + 1) * size + col;
                minimum_union(open_index, adj_index);
            }

            NOpen += 1;  // update number of open sites

            if (QF.find(open_index)==size*size) {
                grid[row][col]=2;
            }
            else{
                grid[row][col]=1;
            }

        }



    }

    public void minimum_union(int p, int q){
    // connects the greater number to the lower number in union
        int QF_p = QF.find(p);
        int QF_q = QF.find(q);

        if (QF_p == size*size){  // if point at the end
            QF.union(q, p);
        }
        else if (QF_q == size*size){
            QF.union(p, q);
        }

        else if (QF_p>QF_q) {
            QF.union(p, q);
        }
        else if (QF_p<QF_q) {
            QF.union(q,p);
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
        else if (QF.find(row*size+col)==size*size){
            return true;
        } else return false;
    }

    public int numberOfOpenSites() {
    // Returns the number of open/full sites
        return NOpen;
    }

    public boolean percolates() {
    // Return true if the system percolates, otherwise false
        for (int i = 0; i<size; i++){
            if (grid[size-1][i] == 2){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int N = 5;
        Percolation P = new Percolation(N);

        QuickFindUF arr = new QuickFindUF(N);

        System.out.println((arr.find(1)));



    }
}