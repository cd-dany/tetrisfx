package com.example;

public class Position {
    private int row;
    private int column;

    Position(int row, int column){
        this.row = row;
        this.column = column;
    }
    /* Getters and Setters for row and Column */
    public int getRow(){
        return this.row;
    }
    public void setRow(int row){
        this.row = row;
    }
    public int getColumn(){
        return this.column;
    }
    public void setColumn(int column){
        this.column = column;
    }
    
}
