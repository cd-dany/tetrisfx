package com.example;

/**
 * Mainly used by the Shape class
 */
public class Position {
    private int row;
    private int column;

    Position(int row, int column){
        this.row = row;
        this.column = column;
    }
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
