package com.example;

import java.io.File;

import javafx.scene.media.AudioClip;

public class GameModel {
    private static final int ROWS = 22; // 20 visible + 2 hidden top rows
    private static final int COLUMNS = 10;

    private TetrisGrid grid;
    private ShapeQueue shapeQueue;
    private Shape currentShape;
    private boolean gameOver;

    private int score;
    private int level;
    private int linesCleared;

    public GameModel() {
        reset();
    }

    public TetrisGrid getTetrisGrid() {
        return this.grid;
    }

    public ShapeQueue getShapeQueue() {
        return this.shapeQueue;
    }

    public Shape getCurrentBlock() {
        return this.currentShape;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public int getScore() {
        return this.score;
    }

    public int getLevel() {
        return this.level;
    }

    public int getLinesCleared() {
        return this.linesCleared;
    }

    /* This is the life cycle */
    public void reset() {
        this.grid = new TetrisGrid(ROWS, COLUMNS);
        this.shapeQueue = new ShapeQueue();
        this.score = 0;
        this.level = 1;
        this.linesCleared = 0;
        this.gameOver = false;
        spawnNextShape();
    }

    private void setCurrentBlock(Shape value) {
        this.currentShape = value;
        if (this.currentShape != null) {
            this.currentShape.reset();
        }
    }

    private void spawnNextShape() {
        setCurrentBlock(shapeQueue.getRandomShape());
        if (!shapeFits()) {
            // New shape cannot fit -> game over immediately because its all the way at the top.
           
            this.gameOver = true;
        }
    }

    private boolean shapeFits() {
        for (Position cell : currentShape.tilePositions()) {
            int row = cell.getRow();
            int col = cell.getColumn();

            // Outside horizontal bounds or below the bottom
            if (col < 0 || col >= COLUMNS || row >= ROWS) {
                return false;
            }

            // Allow above the visible board (negative rows) while the piece is entering
            if (row < 0) {
                continue;
            }

            if (!grid.isEmpty(row, col)) {
                return false;
            }
        }

        return true;
    }

    public void rotateShapeCW() {
        if (currentShape == null || gameOver) {
            return;
        }
        currentShape.rotateCW();
        if (!shapeFits()) {
            currentShape.rotateCCW();
        }
    }

    public void rotateShapeCCW() {
        if (currentShape == null || gameOver) {
            return;
        }
        currentShape.rotateCCW();
        if (!shapeFits()) {
            currentShape.rotateCW();
        }
    }

    public void moveShapeLeft() {
        if (currentShape == null || gameOver) {
            return;
        }
        currentShape.move(0, -1);
        if (!shapeFits()) {
            currentShape.move(0, 1);
        }
    }

    public void moveShapeRight() {
        if (currentShape == null || gameOver) {
            return;
        }
        currentShape.move(0, 1);
        if (!shapeFits()) {
            currentShape.move(0, -1);
        }
    }

    public void moveShapeDown() {
        if (currentShape == null || gameOver) {
            return;
        }
        currentShape.move(1, 0);
        if (!shapeFits()) {
            currentShape.move(-1, 0);
            placeBlock();
        }
    }

    public void hardDrop() {
        if (currentShape == null || gameOver) {
            return;
        }
        do {
            currentShape.move(1, 0);
        } while (shapeFits());

        currentShape.move(-1, 0);
        placeBlock();
    }

    private void placeBlock() {
        for (Position cell : currentShape.tilePositions()) {
            int row = cell.getRow();
            int col = cell.getColumn();

            if (row < 0) {
                this.gameOver = true;
                continue;
            }

            if (row >= 0 && row < ROWS && col >= 0 && col < COLUMNS) {
                this.grid.getTetrisGrid()[row][col] = currentShape.getId();
            }
        }

        int cleared = this.grid.ClearFullRows();
        if (cleared > 0) {
            AudioClip sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-line_clear.wav").toURI().toString());
            sound.play();
            this.linesCleared += cleared;
            this.score += scoreForClearedRows(cleared);
            this.level = 1 + (this.linesCleared / 10); // every 10 lines, increase level
        }

        if (!this.gameOver) {
            spawnNextShape();
        }
    }

    private int scoreForClearedRows(int cleared) {
        switch (cleared) {
            case 1:
                return 40 * level;
            case 2:
                return 100 * level;
            case 3:
                return 300 * level;
            case 4:
                return 1200 * level;
            default:
                return 0;
        }
    }
}
