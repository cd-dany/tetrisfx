package com.example;

public class OShape extends Shape {

    private static final int ID = 4;
    // Adjust start offset if needed to match your board
    private static final Position START_OFFSET = new Position(0, 4);
    
    private final Position[][] tiles = {
            { new Position(0, 0), new Position(0, 1), new Position(1, 0), new Position(1, 1) },
            { new Position(0, 0), new Position(0, 1), new Position(1, 0), new Position(1, 1) },
            { new Position(0, 0), new Position(0, 1), new Position(1, 0), new Position(1, 1) },
            { new Position(0, 0), new Position(0, 1), new Position(1, 0), new Position(1, 1) }
    };

    @Override
    protected Position[][] getTiles() {
        return tiles;
    }

    @Override
    protected Position getStartOffset() {
        return START_OFFSET;
    }

    @Override
    public int getId() {
        return ID;
    }
}