package com.example;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        Canvas canvas = new Canvas(945, 1080);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameView view = new GameView(gc);
        GameModel model = new GameModel();
        GameController controller = new GameController(model, view);

        StackPane root = new StackPane();
        ImageView backgroundView = new ImageView(new Image(new File("src/main/resources/img/background.jpg").toURI().toString()));
        backgroundView.setFitWidth(1920);   
        backgroundView.setFitHeight(1080);
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());

        root.getChildren().addAll( backgroundView,canvas); 

        Scene scene = new Scene(root);
        /* This is the easiest way i found to set the controls */
        controller.attachScene(scene);

        stage.setTitle("Tetris");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(945);
        stage.setMinHeight(1080);
        stage.show();

        // Start the main game loop
        controller.start();
    }
}
