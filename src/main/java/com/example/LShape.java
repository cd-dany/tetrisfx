package com.example;

public class LShape extends Shape {

    private static final int ID = 3;
    private static final Position START_OFFSET = new Position(0, 3);

    private final Position[][] tiles = {
            { new Position(0, 2), new Position(1, 0), new Position(1, 1), new Position(1, 2) },
            { new Position(0, 1), new Position(1, 1), new Position(2, 1), new Position(2, 2) },
            { new Position(1, 0), new Position(1, 1), new Position(1, 2), new Position(2, 0) },
            { new Position(0, 0), new Position(0, 1), new Position(1, 1), new Position(2, 1) }
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