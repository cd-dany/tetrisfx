package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This is a remake of Tetris implemented in JavaFX following the MVC model.
 */
public class TetrisFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Window size chosen to match your existing layout / developer grid
        Canvas canvas = new Canvas(945, 1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameView view = new GameView(gc);
        GameModel model = new GameModel();
        GameController controller = new GameController(model, view);

        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);

        // Hook up input handling to the controller
        controller.attachScene(scene);

        stage.setTitle("Tetris");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Start the main game loop
        controller.start();
    }
}
