//package com.arkad.competition.solve;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Main implements Solver {
    //private int[][] grid;

    @Override
    public void setup(int size, int m) {
        int[][] testGrid = new int[size][size];
        Random r = new Random();
        int low = -m;
        int high = m;

        String s = "";
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                testGrid[i][j] = r.nextInt(high-low) + low;
                s += " " + testGrid[i][j];
            }
            //System.out.println(s);
            //s = "";
        }
        //grid = testGrid;
        //solve(grid, m);
    }

    @Override
    public List<Square> solve(int[][] matrix, int m) {
        List<Square> list = new ArrayList<Square>();
        return list;
    }

    public int[][] createGrid(int size, int m) {
        int[][] grid = new int[size][size];
        Random r = new Random();
        int low = -m;
        int high = m;

        String s = "";
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = r.nextInt(high-low) + low;
                s += " " + grid[i][j];
            }
            //System.out.println(s);
            //s = "";
        }
        //solve(grid, m);
        return grid;
    }

    public void flip(int[][] grid, int fromRow, int fromCol, int steps) {
        for (int i = fromRow; i < fromRow + steps; i++) {
            for (int j = fromCol; j < fromCol + steps; j++) {
                grid[i][j] = -grid[i][j];
            }
        }
    }

    public void print(int[][] matrix, int size) {
        String s = "";
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                s += "     " + matrix[i][j];
            }
            System.out.println(s);
            s = "";
        }
        System.out.println("");
    }

    public int[] values(int[][] matrix, int size) {
        int[] values = new int[2]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        values[0] = 1;
        values[1] = 2;
        values[2] = 3;
        return values;
    }

    public int[] solve2(int[][] matrix, int size) {
        int[] values = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.

        // Check all subgrids recursively
        // Add all the numbers in the subgrid
        // Check which subgrid has the lowest summation
        // Store the fromRow, fromCol and steps in values
        // Return the values (then flip it)
        
        int lowest_value = Integer.MAX_VALUE;
        int subgrid_value = 0;
        for (int subgrid_size = 1; subgrid_size - 1 < size; subgrid_size++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j ++) {
                    // Need to check if out of bounds
                    // subgrid_value = matrix[i][j];

                    // Messy if statements, just to test
                    
                    /*if (subgrid_size == 1) {
                        subgrid_value = matrix[i][j];
                    }
                    if (subgrid_size == 2) {
                        if (i + subgrid_size + 1 < size && j + subgrid_size + 1 < size) {
                            subgrid_value = matrix[i][j] + matrix[i + 1][j] + matrix[i][j + 1] + matrix[i + 1][j + 1];
                        }
                    }*/

                    // Make a subgrid method that creates a subgrid from input of row and col start and steps
                    // Then summarise that subgrid with the sum method
                    // Then compare the subgrid_value to the lowest_value

                    // Check this tomorrow
                    if (i + subgrid_size - 1 < size && j + subgrid_size - 1 < size) {
                        int[][] subgrid = subgrid(matrix, i, j, subgrid_size);
                        subgrid_value = sum(subgrid, subgrid_size);
                    }

                    if (subgrid_value < lowest_value) {
                        values[0] = i;
                        values[1] = j;
                        values[2] = subgrid_size;
                        lowest_value = subgrid_value;
                    }
                }
            }
        }
        return values;
    }

    public int sum(int[][] matrix, int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sum += matrix[i][j];
            }
        }
        return sum;
    }

    public int[][] subgrid(int[][] matrix, int fromRow, int fromCol, int size) {
        int[][] subgrid = new int[size][size];
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                subgrid[i][j] = matrix[fromRow + i][fromCol + j];
            }
        }
        return subgrid;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int size = 25;
        int m = 10;
        int[][] grid = main.createGrid(size, m);
        int[] values = new int[3];

        int [][] test1 = {
            { 6, 2 , -1 , 5} ,
            { 4 , -5 , -7 , 3} ,
            { -3 , 0, 2 , -4} ,
            { -1 , 3 ,10 , -6}
            };

        int [][] test2 = {
            { -4 , -4 , -8 , -7 , -3} ,
            { -3 , 4, 7, 3 , -8} ,
            { -8 , 0 , -1 , 5 , -3} ,
            { -5 , 3 ,10 , 3 , -5} ,
            { -1 , -3 , -7 , -9 , -2}
            };

        // Original grid
        System.out.println("Sum of squares: " + main.sum(grid, size));
        main.print(grid, size);

        // Flips
        for (int i = 0; i < 5; i++) {
            values = main.solve2(grid, size);
            main.flip(grid, values[0], values[1], values[2]);
            System.out.println("Sum of squares: " + main.sum(grid, size));
            main.print(grid, size);
        }
    }
}
