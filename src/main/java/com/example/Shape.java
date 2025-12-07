package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * All of the 7 Major Shapes extend this class
 * 
 * All shapes contain tile positions, and their 4 rotations
 * 
 * This is where major diffrences in c
 * 
 */
public abstract class Shape {

    protected abstract Position[][] getTiles();

    protected abstract Position getStartOffset();

    public abstract int getId();

    private int rotationState; // 1-4 because there is 4 distinct rotations.
    private Position offset;

    // Constructor
    public Shape() {
        Position start = getStartOffset(); // startOffset from child
        this.offset = new Position(start.getRow(), start.getColumn());
    }


    /**
     * Returns grid positions occupied by the block including its current rotation and offset as an ArrayList
     * @return
     */
    public ArrayList<Position> tilePositions() {
        ArrayList<Position> result = new ArrayList<>();

        for (Position p : getTiles()[rotationState]) { // again, this comes from the child.
            result.add(new Position(p.getRow() + offset.getRow(), p.getColumn() + offset.getColumn()));
        }

        return result;
    }

    public void rotateCW() {
        rotationState = (rotationState + 1) % getTiles().length;
    }
    public void rotateCCW(){
        if (rotationState==0){
            rotationState = getTiles().length-1;
        }
        else{
            rotationState--;
        }
    }

    public void move(int rows, int columns) {
        offset.setRow(offset.getRow() + rows);
        offset.setColumn(offset.getColumn() + columns);
    }
    public void reset() {
        rotationState = 0;
        Position start = getStartOffset();
        offset.setRow(start.getRow());
        offset.setColumn(start.getColumn());
    }




}
