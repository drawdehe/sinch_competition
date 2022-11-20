import java.util.List;
import java.util.ArrayList;

public class Solution implements Solver {

    @Override
    public void setup(int size, int m) {
        // Not used
    }

    @Override
    public List<Square> solve(int[][] matrix, int m) {
        List<Square> list_1 = new ArrayList<Square>();
        List<Square> list_2 = new ArrayList<Square>();
        int[] values_1 = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        int[] values_2 = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        int[][] grid_1 = copy(matrix);
        int[][] grid_2 = copy(matrix);
        int size = matrix.length;
        
        // 4 flips
        for (int flip = 1; flip < 5; flip++) {
            values_1 = solve_max(grid_1, size);
            flip(grid_1, values_1[0], values_1[1], values_1[2]);
            list_1.add(new Square(values_1[0], values_1[1], values_1[2]));

            values_2 = solve_min(grid_2, size);
            flip(grid_2, values_2[0], values_2[1], values_2[2]);
            list_2.add(new Square(values_2[0], values_2[1], values_2[2]));
        }

        // Last flip
        values_1 = solve_max(grid_1, size);
        values_2 = solve_max(grid_2, size);
        flip(grid_1, values_1[0], values_1[1], values_1[2]);
        flip(grid_2, values_2[0], values_2[1], values_2[2]);

        if (sum(grid_1, size) >= sum(grid_2, size)) {
            list_1.add(new Square(values_1[0], values_1[1], values_1[2]));
            return list_1;
        } else {
            list_2.add(new Square(values_2[0], values_2[1], values_2[2]));
            return list_2;
        }
    }

    private int[] solve_max(int[][] matrix, int size) {
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

    private int[] solve_min(int[][] matrix, int size) {
        int[] values = new int[3]; // element 0 = fromRow, element 1 = fromCol, element 3 = steps.
        
        int highest_value = Integer.MIN_VALUE;
        int subgrid_value = 0;
        for (int subgrid_size = 1; subgrid_size - 1 < size; subgrid_size++) {
            for (int i = 0; i + subgrid_size - 1 < size; i++) {
                for (int j = 0; j + subgrid_size - 1 < size; j ++) {

                    int[][] subgrid = subgrid(matrix, i, j, subgrid_size);
                    subgrid_value = sum(subgrid, subgrid_size);

                    if (subgrid_value > highest_value) {
                        values[0] = i;
                        values[1] = j;
                        values[2] = subgrid_size;
                        highest_value = subgrid_value;
                    }
                }
            }
        }
        return values;
    }

    private int[][] copy(int[][] src) {
        if (src == null) {
            return null;
        }
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = new int[src[i].length];
            System.arraycopy(src[i], 0, copy[i], 0, src[i].length);
        }
        return copy;
    }

    private void flip(int[][] grid, int fromRow, int fromCol, int steps) {
        for (int i = fromRow; i < fromRow + steps; i++) {
            for (int j = fromCol; j < fromCol + steps; j++) {
                grid[i][j] = -grid[i][j];
            }
        }
    }

    private int sum(int[][] matrix, int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sum += matrix[i][j];
            }
        }
        return sum;
    }

    private int[][] subgrid(int[][] matrix, int fromRow, int fromCol, int size) {
        int[][] subgrid = new int[size][size];
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < size; j++) {
                subgrid[i][j] = matrix[fromRow + i][fromCol + j];
            }
        }
        return subgrid;
    }
}
