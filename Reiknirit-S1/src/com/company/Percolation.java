package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;
public class Percolation {
    int size;
    int NOpen = 0;
    int[][] grid;
    int[] flowGrid;

    public Percolation(int N) {
        if (N<=0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        grid = new int[N][N];                        //Blocked = 0, Open = 1, Flow = 2
        flowGrid = new int[size * size];             // pointer array that point to the smallest connected index
        for (int i = 0; i < (size * size); i++) {
            flowGrid[i] = size * size;               // all start by pointing at size*size i.e. bigger than any index
        }
    }

    public void print_grid() {
        // prints out grid an flowGrid

        // if the group index is smaller than size then it is connected to the top
        /*for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (flowGrid[i * size + j] < size) {
                    grid[i][j] = 2;                     // updating grid
                }
            }
        }*/

        // Printing Grid out
        System.out.println("Grid:");
        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(grid[i]));
        }

        // printing flowGrid
        System.out.println("\nflowGrid:");
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                System.out.print(flowGrid[j * size + i]+ " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");


    }

    public void open(int row, int col) {
    // Opens the site at row,col
        if (row > size-1 || col > size-1 || col < 0 || row < 0) {
            throw new java.lang.IndexOutOfBoundsException();
            //System.out.println("HELL");
        }
        else if (grid[row][col] == 0) {   // if blocked
            int number;
            int min = row * size + col;          // minimum index
            flowGrid[row * size + col] = min;    // points at itself in the beginning
            boolean fullOrOpen;


            fullOrOpen = withinBounds(row - 1, col);
            if (fullOrOpen) {
                number = flowGrid[(row - 1) * size + col];   // takes the pointer at the location

                if (min > number) {    // if pointer is lower than at it self
                    min = number;
                } else {
                    flowGrid[number] = min;   // otherwise then the lowest index at current union takes points at the min
                    //checkAll(number, min);     // change every pointer in the union to min
                }
            }


            fullOrOpen = withinBounds(row, col + 1);
            if (fullOrOpen) {
                number = flowGrid[row * size + (col + 1)];

                if (min > number) {
                    min = number;
                } else {
                    flowGrid[number] = min;
                    //checkAll(number, min);
                }
            }

            fullOrOpen = withinBounds(row, col - 1);
            if (fullOrOpen) {
                number = flowGrid[row * size + col - 1];
                if (min > number) {
                    min = number;
                } else {
                    flowGrid[number] = min;
                    //checkAll(number, min);
                }
            }

            fullOrOpen = withinBounds(row + 1, col);
            if (fullOrOpen) {
                number = flowGrid[(row + 1) * size + col];
                if (min > number) {
                    min = number;
                } else {
                    flowGrid[number] = min;
                    //checkAll(number, min);
                }
            }
            NOpen += 1;  // update number of open sites


            flowGrid[row * size + col] = min;  // update new open site to its union lowest index
            checkAll();
            if (min<size) {
                grid[row][col]=2;
            }
            else {
                grid[row][col]=1;
            }
        }
        else {
            System.out.println("This site is already open");
        }
    }

    public boolean withinBounds(int target_row, int target_col) {
    // Returns True if (target_row, target_col) is within grid boundaries and is either open or full, otherwise false

        if (target_row < 0 || target_row > size - 1 || target_col < 0 || target_col > size - 1) {
            return false;
        }
        return isOpen(target_row, target_col) || isFull(target_row, target_col);
    }

    public void checkAll() {
    // Updates the whole union from previous index to current index
        //boolean flow=num<size && prevNum>size;
        /*System.out.println(size);
        System.out.println(num);
        System.out.println(prevNum);
        System.out.println(flowGrid[6]);*/
        for (int i = 0; i < size * size; i++) {
            if (flowGrid[i]!=25) {
                if ((flowGrid[i] != flowGrid[flowGrid[i]]) || i != flowGrid[i]) {
                    flowGrid[i] = flowGrid[flowGrid[i]];
                    if (flowGrid[i] < size) {
                        System.out.println(i / size + " - " + i % size);

                        grid[i / size][i % size] = 2;
                    }
                }
            }
        }

    }

    public boolean isOpen(int row, int col) {
    // Returns true if site at (row,col) is within boundaries and is open
        if (row > size-1 || col > size-1 || col < 0 || row < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (grid[row][col] == 1) {
            return true;
        } else return false;
    }


    public boolean isFull(int row, int col) {
    // Returns true if site at (row,col) is within boundaries and is full
        if (row > size-1 || col > size-1 || col < 0 || row < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        } else if (grid[row][col] == 2) {
            return true;
        } else return false;
    }

    public int numberOfOpenSites() {
    // Returns the number of open/full sites
        return NOpen;
    }

    public boolean percolates() {
    // Return true if the system percolates, otherwise false
        for (int i = 0; i < size; i++) {
            if (flowGrid[size * (size - 1) + i] < size) {   // if any of the bottom row points at any of the index in the top row
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int N = 5;
        Percolation P = new Percolation(N);
        P.print_grid();
        /*P.open(1, 2);
        P.open(3, 4);
        P.open(4, 2);
        P.open(0, 1);
        P.open(1, 1);
        P.open(2, 1);
        P.open(3, 1);
        P.open(3, 2);*/

        P.open(0, 0);
        P.open(1, 1);
        P.open(3, 2);
        P.open(2, 2);
        P.open(1, 2);
        P.open(0, 2);

        P.open(4, 3);
        P.open(3, 4);
        P.open(1, 3);

        P.open(3, 3);
/*
        P.open(4, 0);*/




        P.print_grid();

        System.out.println("Percolates?: " + P.percolates());


    }
}