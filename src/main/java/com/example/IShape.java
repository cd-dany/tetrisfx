package com.example;

public class IShape extends Shape {
    
    private final Position[][]tiles = {
        {new Position(1, 0),new Position(1, 1),new Position(1, 2),new Position(1, 3)},
        {new Position(0, 2),new Position(1, 2),new Position(2, 2),new Position(3, 2)},
        {new Position(2, 0),new Position(2, 1),new Position(2, 2),new Position(2, 3)},
        {new Position(0, 1),new Position(1, 1),new Position(2, 1),new Position(3, 1)}
    };
    private static final int ID = 1; 
    private static final Position startOffset = new Position(-1, 3); 

    IShape(){

    }
    @Override
    protected Position[][] getTiles() {
        return this.tiles;
    }
    @Override
    protected Position getStartOffset() {
        return this.startOffset;
    }
    @Override
    public int getId() {
        return this.ID;
    }
    
}
