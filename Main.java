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

        //String s = "";
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                testGrid[i][j] = r.nextInt(high-low) + low;
                //s += " " + testGrid[i][j];
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
        int[] values = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        int[][] grid = matrix;
        int size = grid.length;
        
        // Original grid
        System.out.println("Sum of squares: " + sum(grid, size));
        print(grid, size);

        // Flips
        for (int i = 0; i < 5; i++) {
            values = solve2(grid, size);
            flip(grid, values[0], values[1], values[2]);
            System.out.println("Sum of squares: " + sum(grid, size));
            print(grid, size);

            // Add values to the list
            list.add(new Square(values[0], values[1], values[2]));

            // Print values of the square
            System.out.println("Row: " + list.get(i).getRow());
            System.out.println("Col: " + list.get(i).getCol());
            System.out.println("Size: " + list.get(i).getSize());
        }
        return list;
    }

    private int[] solve2(int[][] matrix, int size) {
        int[] values = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        
        int lowest_value = Integer.MAX_VALUE;
        int subgrid_value = 0;
        for (int subgrid_size = 1; subgrid_size - 1 < size; subgrid_size++) {
            for (int i = 0; i + subgrid_size - 1 < size; i++) {
                for (int j = 0; j + subgrid_size - 1 < size; j ++) {

                    int[][] subgrid = subgrid(matrix, i, j, subgrid_size);
                    subgrid_value = sum(subgrid, subgrid_size);

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

    public int[][] createGrid(int size, int m) {
        int[][] grid = new int[size][size];
        Random r = new Random();
        int low = -m;
        int high = m;

        //String s = "";
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = r.nextInt(high-low) + low;
                //s += " " + grid[i][j];
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

    public List<Square> solved(int[][] matrix, int m) {
        List<Square> list = new ArrayList<Square>();
        int[] values = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        int[][] grid = matrix;
        
        // Original grid
        System.out.println("Sum of squares: " + sum(grid, m));
        print(grid, m);

        // Flips
        for (int i = 0; i < 5; i++) {
            values = solve2(grid, m);
            flip(grid, values[0], values[1], values[2]);
            System.out.println("Sum of squares: " + sum(grid, m));
            print(grid, m);

            // Add values to the list
            list.add(new Square(values[0], values[1], values[2]));
        }

        return list;
    }

    public int[] solve_rec(int[][] matrix, int size) {
        int[] values = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        int highest_value = Integer.MIN_VALUE;
        int sum = 0;
        for (int subgrid_size = 1; subgrid_size - 1 < size; subgrid_size++) {
            for (int i = 0; i + subgrid_size - 1 < size; i++) {
                for (int j = 0; j + subgrid_size - 1 < size; j ++) {
                    sum = solve_rec_pr(matrix, size, i, j, subgrid_size, 5);
                    if (sum > highest_value) {
                        values[0] = i;
                        values[1] = j;
                        values[2] = subgrid_size;
                        highest_value = sum;
                    }
                }
            }
        }
        System.out.println(highest_value);
        return values;
    }

    private int solve_rec_pr(int[][] matrix, int size, int fromRow, int fromCol, int steps, int flip) {
        if (flip == 0) {
            print(matrix, size);
            return sum(matrix, size);
        } else {
            for (int subgrid_size = 1; subgrid_size - 1 < size; subgrid_size++) {
                for (int i = 0; i + subgrid_size - 1 < size; i++) {
                    for (int j = 0; j + subgrid_size - 1 < size; j++) {
                        flip(matrix, i, j, subgrid_size);
                        return solve_rec_pr(matrix, size, i, j, subgrid_size, flip - 1);
                    }
                }
            }
        }
        return 0; // Should not be 0, but don't know what to return here yet
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

    // Hannes kod
    /*
    public int[] solve_rec2(int[][] matrix, int size) { //(int[][] matrix, ) {
        List<Square> bestFlips = new ArrayList<Square>();
        int currBestScore = 0;
        int minFlips = 6;

        for (int sz = 1; sz < size + 1; sz++) {
            int sz_bound = (size - sz) + 1;
            for(int i = 0; i < sz_bound; i++) {
                for(int j = 0; j < sz_bound; j++) {
                    List<Square> currentFlips = new ArrayList<Square>();
                    Square s = new Square(i, j, sz);
                    currentFlips.add(s);

                    List<Square> completeFlips = solve_rec2(currentFlips);
                    int evalScore = evalFlips(completeFlips);

                    
                    if(evalScore == this.maxScore) {
                        return completeFlips;
                    }

                    if(evalScore >= currBestScore && completeFlips.size() < minFlips) {
                        currBestScore = evalScore;
                        bestFlips = completeFlips; //new ArrayList<Square>(completeFlips);
                    }
                }
            }
        }
        return bestFlips;
    }

    private List<Square> solve_rec2(List<Square> tempL) {
        if(tempL.size() == 5 || isMatrixPos(tempL)) {
            return tempL;
        }

        List<Square> bestFlips = new ArrayList<Square>();
        int currBestScore = 0;
        int minFlips = 6;

        for (int sz = 1; sz < matrix_size + 1; sz++) {
            int sz_bound = (matrix_size - sz) + 1;
            for(int i = 0; i < sz_bound; i++) {
                for(int j = 0; j < sz_bound; j++) {
                    List<Square> currentFlips = new ArrayList<Square>(tempL);
                    Square s = new Square(i, j, sz);

                    currentFlips.add(s);

                    List<Square> completeFlips = solve_rec2(currentFlips);
                    
                    int evalScore = evalFlips(completeFlips);

                    
                    if(evalScore == this.maxScore) {
                        return completeFlips; //new ArrayList<Square>(completeFlips);
                    }

                    if(evalScore >= currBestScore && completeFlips.size() < minFlips) {
                        currBestScore = evalScore;
                        bestFlips = completeFlips; //new ArrayList<Square>(completeFlips);
                    }
                }
            }
        }
        return bestFlips;
    }*/

    public static void main(String[] args) {
        Main main = new Main();
        int size = 4;
        int m = 10;
        List<Square> list = new ArrayList<Square>();

        // Grids to test

        int[][] grid = main.createGrid(size, m);
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

        //list = main.solve(test1, 100);

        main.solve(test1, 4);

        /*// Original grid
        System.out.println("Sum of squares: " + main.sum(test1, size));
        main.print(test1, size);

        // Flips
        for (int i = 0; i < 5; i++) {
            values = main.solve2(test1, size);
            main.flip(test1, values[0], values[1], values[2]);
            System.out.println("Sum of squares: " + main.sum(test1, size));
            main.print(test1, size);
        }*/
    }
}
