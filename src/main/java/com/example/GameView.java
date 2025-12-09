package com.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameView {
    private final GraphicsContext gc;

    /**
     * Resolution constants
     *
     * Window has a 7:8 Aspect Ratio.
     * There is a 21 x 24 Grid overlaid on top of the canvas.
     * A cell is 45px x 45px
     */
    public static final int BOARD__CEll_WIDTH = 10;
    public static final int BOARD_CELL_HEIGHT = 20;
    public static final int TILE_SIZE = 45;

    GameView(GraphicsContext gc) {
        this.gc = gc;
        drawBackground();
        drawBoard();
        drawDeveloperGrid();
        drawTitle();
        drawScore(0);
        drawLevel(0);
        drawCleared(0);
    }

    /**
     * Render is the method that the controller will call.
     * It takes in the GameModel to draw grid + current shape + UI.
     */
    public void render(GameModel model) {
        // 1. Clear and draw static UI elements
        drawBackground();
        drawBoard();
        
        drawTitle();

        // 2. Draw locked blocks and current falling shape
        drawLockedBlocks(model);
        drawCurrentShape(model);

        // 3. Draw score / level / cleared lines UI
        drawScore(model.getScore());
        drawLevel(model.getLevel());
        drawCleared(model.getLinesCleared());
        if (model.getDrawGrid() == true)
            drawGridLines();
    }

    /**
     * This should be the first thing called when the view is rendered, everything sits on top of this.
     */
    private void drawBackground() {
        this.gc.setFill(Color.rgb(50, 50, 50));
        this.gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    /**
     * Draw all locked blocks already placed on the grid.
     */
    private void drawLockedBlocks(GameModel model) {
        int[][] grid = model.getTetrisGrid().getTetrisGrid();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                int id = grid[r][c];
                if (id != 0) {
                    drawBlockAtBoardCell(r, c, id);
                }
            }
        }
    }

    /**
     * Draw the currently falling tetromino.
     */
    private void drawCurrentShape(GameModel model) {
        Shape shape = model.getCurrentBlock();
        for (Position p : shape.tilePositions()) 
            drawBlockAtBoardCell(p.getRow(), p.getColumn(), shape.getId());
    }

    /**
     * Draw a single block at the given logical grid cell.
     * The TetrisGrid uses 22 rows where the top 2 are hidden;
     * we only render rows 2..21 inside the 20-row visible board.
     */
    private void drawBlockAtBoardCell(int row, int col, int id) {
        // Skip cells above the visible playfield
        int visibleRow = row - 2;
        if (visibleRow < 0 || visibleRow >= BOARD_CELL_HEIGHT) {
            return;
        }
        if (col < 0 || col >= BOARD__CEll_WIDTH) {
            return;
        }

        double x = TILE_SIZE * 2 + col * TILE_SIZE;
        double y = TILE_SIZE * 2 + visibleRow * TILE_SIZE;

        gc.setFill(colorForId(id));
        gc.fillRoundRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2, 8, 8);
    }

    /**
     * Simple color mapping for shape IDs.
     */
    private Color colorForId(int id) {
        switch (id) {
            case 1: return Color.CYAN;      // I
            case 2: return Color.BLUE;      // J
            case 3: return Color.ORANGE;    // L
            case 4: return Color.YELLOW;    // O
            case 5: return Color.LIME;      // S
            case 6: return Color.MAGENTA;   // T
            case 7: return Color.RED;       // Z
            default: return Color.GRAY;
        }
    }

    /**
     * Draws the Tetromino Board
     */
    private void drawBoard() {
        // board border
        gc.setFill(Color.DARKGRAY);
        gc.fillRoundRect((TILE_SIZE * 2) - 8, (TILE_SIZE * 2) - 8,
                (TILE_SIZE * BOARD__CEll_WIDTH) + 16, (TILE_SIZE * BOARD_CELL_HEIGHT) + 16, 20, 20);

        // board background
        gc.setFill(Color.BLACK);
        gc.fillRect(TILE_SIZE * 2, TILE_SIZE * 2, TILE_SIZE * BOARD__CEll_WIDTH, TILE_SIZE * BOARD_CELL_HEIGHT);
    }

    /**
     * For developer use, it better highlights the grid for better component placement
     */
     private void drawDeveloperGrid() {
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1.0);

        // Vertical lines (columns)
        for (int col = 0; col <= 21; col++) {
            double x = col * 45;
            gc.strokeLine(x, 0, x, 24 * 45);
        }

        // Horizontal lines (rows)
        for (int row = 0; row <= 24; row++) {
            double y = row * 45;
            gc.strokeLine(0, y, 21 * 45, y);
        }
    }
    
    private void drawGridLines(){
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1.0);

        // Vertical lines (columns)
        for (int col = 2; col <= 12; col++) {
            double x = col * 45;
            gc.strokeLine(x, 90, x, 22 * 45);
        }

        // Horizontal lines (rows)
        for (int row = 2; row <= 22; row++) {
            double y = row * 45;
            gc.strokeLine(90, y, 12 * 45, y);
        }
    }

    private void drawTitle() {
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Ethnocentric-Regular.otf"), 28);
        this.gc.setFont(font);
        this.gc.setFill(Color.WHITE);

        String text = "TETRIS";
        Text layoutText = new Text(text);
        layoutText.setFont(font);
        double textWidth = layoutText.getLayoutBounds().getWidth();

        double startX = (gc.getCanvas().getWidth() - textWidth) / 2;
        double y = TILE_SIZE * 1.5;

        gc.fillText(text, startX, y);
    }

    /**
     * Score is not a static component, it changes depending on the user score.
     */
    private void drawScore(int score) {
        // board border
        gc.setFill(Color.DARKGRAY);
        gc.fillRoundRect((TILE_SIZE * 14) - 8, (TILE_SIZE * 3) - 8,
                (TILE_SIZE * 6) + 16, (TILE_SIZE * 2) + 16, 20, 20);

        // board background
        gc.setFill(Color.BLACK);
        gc.fillRect(TILE_SIZE * 14, TILE_SIZE * 3, TILE_SIZE * 6, TILE_SIZE * 2);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Ethnocentric-Regular.otf"), 34);
        this.gc.setFont(font);
        gc.setFill(Color.WHITE);

        gc.fillText("Score", (TILE_SIZE * 15) + TILE_SIZE / 4, (TILE_SIZE * 2) + TILE_SIZE / 2);
        gc.fillText("" + score, (TILE_SIZE * 14) + TILE_SIZE / 1.5, (TILE_SIZE * 4) + TILE_SIZE / 4);
    }

    private void drawLevel(int level) {
        // board border
        gc.setFill(Color.DARKGRAY);
        gc.fillRoundRect((TILE_SIZE * 14) - 8, (TILE_SIZE * 7) - 8,
                (TILE_SIZE * 6) + 16, (TILE_SIZE * 2) + 16, 20, 20);

        // board background
        gc.setFill(Color.BLACK);
        gc.fillRect(TILE_SIZE * 14, TILE_SIZE * 7, TILE_SIZE * 6, TILE_SIZE * 2);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Ethnocentric-Regular.otf"), 34);
        this.gc.setFont(font);
        gc.setFill(Color.WHITE);

        gc.fillText("Level", (TILE_SIZE * 14) + TILE_SIZE / 2, (TILE_SIZE * 6) + TILE_SIZE / 2);
        gc.fillText("" + level, (TILE_SIZE * 14) + TILE_SIZE / 1.5, (TILE_SIZE * 8) + TILE_SIZE / 4);
    }

    private void drawCleared(int cleared) {
        // board border
        gc.setFill(Color.DARKGRAY);
        gc.fillRoundRect((TILE_SIZE * 14) - 8, (TILE_SIZE * 11) - 8,
                (TILE_SIZE * 6) + 16, (TILE_SIZE * 2) + 16, 20, 20);

        // board background
        gc.setFill(Color.BLACK);
        gc.fillRect(TILE_SIZE * 14, TILE_SIZE * 11, TILE_SIZE * 6, TILE_SIZE * 2);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/Ethnocentric-Regular.otf"), 34);
        this.gc.setFont(font);
        gc.setFill(Color.WHITE);

        gc.fillText("Cleared", (TILE_SIZE * 14) + TILE_SIZE / 2, (TILE_SIZE * 10) + TILE_SIZE / 2);
        gc.fillText("" + cleared, (TILE_SIZE * 14) + TILE_SIZE / 1.5, (TILE_SIZE * 12) + TILE_SIZE / 4);
    }

}
