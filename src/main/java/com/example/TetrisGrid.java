package com.example;

/**
 * Consult with this source on how the grid works for better understanding of how the grid works:https://www.youtube.com/watch?v=jcUctrLC-7M
 */
public class TetrisGrid {
    private int rows;
    private int columns;
    private int[][] grid; // make getter for grid

    TetrisGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new int[rows][columns];
    }

    /* Getters and Setters for rows and columns */

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Grid Line Methods
     *
     * These Methods Check the status of the rows/columns in the grid.
     */

    /**
     * This method returns true if the given row AND column are inside the grid.
     */
    public boolean isInside(int row, int column) {
        return (row >= 0) && (row < this.rows) && (column >= 0) && (column < this.columns);
    }

    /**
     * This method returns true if a specific cell is empty (0)
     */
    public boolean isEmpty(int row, int column) {
        if (this.isInside(row, column)) {
            return this.grid[row][column] == 0;
        } else {
            return false;
        }
    }

    /**
     * This method returns true if ALL cells within a row are 0, false otherwise.
     */
    public boolean isRowEmpty(int row) {
        for (int c = 0; c < this.columns; c++) {
            if (!this.isEmpty(row, c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method returns true if a cell within a row is NOT 0, false otherwise
     */
    public boolean isRowFull(int row) {
        for (int c = 0; c < this.columns; c++) {
            if (this.isEmpty(row, c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clearing Row Methods
     */

    private void clearRow(int row) {
        for (int c = 0; c < this.columns; c++) {
            this.grid[row][c] = 0;
        }
    }

    private void moveRowDown(int row, int numRows) {
        for (int c = 0; c < this.columns; c++) {
            this.grid[row + numRows][c] = this.grid[row][c];
            this.grid[row][c] = 0;
        }
    }

    public int ClearFullRows() {
        int cleared = 0;
        for (int r = this.rows - 1; r >= 0; r--) {
            if (isRowFull(r)) {
                clearRow(r);
                cleared++;
            } else if (cleared > 0) {
                moveRowDown(r, cleared);
            }
        }
        return cleared;
    }

    public int[][] getTetrisGrid() {
        return this.grid;
    }
}
