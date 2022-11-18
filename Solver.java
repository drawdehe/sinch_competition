//package com.arkad.competition.solve;

import java.util.List;

public interface Solver {
    public void setup(int size, int m);
    public List<Square> solve(int[][] matrix, int m);

    public class Square {
        private int row, col, size;

        public Square(int row, int col, int size) {
            this.row = row;
            this.col = col;
            this.size = size;
        }
    }
}
