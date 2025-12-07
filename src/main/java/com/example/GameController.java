package com.example;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class GameController {
    private Timeline animation; // regular drop timer
    private final GameModel model;
    private final GameView view;
    MediaPlayer player;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        // //Set up the Theme Musing 
        String ThemeSongfilepath = "src/main/resources/sounds/TetrisTheme.mp3";
        File file = new File (ThemeSongfilepath);
        Media MainTheme = new Media(file.toURI().toString());
        player = new MediaPlayer(MainTheme);
        player.setCycleCount(MediaPlayer.INDEFINITE); //Set this to indefinete because it will play the music just once
        player.play();
    }

    // Called from TetrisFX once the Scene is created
    public void attachScene(Scene scene) {
        scene.setOnKeyPressed(this::handleKeyPressed);
    }

    public void start() {
        // draw initial frame
        view.render(model);
        startAnimationLoop();
    }

    private void startAnimationLoop() {
    if (animation != null) {
        animation.stop();
    }

    // Example: base 500ms, minus 30ms per level, but never below 100ms
    int level = model.getLevel();
    double delay = 500 - (level - 1) * 30;
    if (delay < 100) {
        delay = 100;
    }

    animation = new Timeline(
        new KeyFrame(Duration.millis(delay), e -> {
            if (!model.isGameOver()) {
                model.moveShapeDown();
                view.render(model);
            } else {
                 AudioClip sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-game_over.wav").toURI().toString());
            sound.play();
                animation.stop();
                view.render(model);
            }
        })
    );
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play();
}

    private void handleKeyPressed(KeyEvent event) {
        if (model.isGameOver()) {
            player.stop();//stop the music
            if (event.getCode() == KeyCode.R) {
                model.reset();
                view.render(model);
                player.play();
                startAnimationLoop();
            }
            return;
        }

        KeyCode code = event.getCode();

        AudioClip sound;
        switch (code) {
            
            case LEFT:
                
                sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-move_piece.wav").toURI().toString());
                sound.play();
                model.moveShapeLeft();
                break;
            case RIGHT:
                sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-move_piece.wav").toURI().toString());
                sound.play();
                model.moveShapeRight();
                break;
            case DOWN:
                sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-move_piece.wav").toURI().toString());
                sound.play();
                model.moveShapeDown(); 
                break;
            case UP:
                sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-rotate_piece.wav").toURI().toString());
                sound.play();
                model.rotateShapeCW();
                break;
            case Z:
                sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-rotate_piece.wav").toURI().toString());
                sound.play();
                model.rotateShapeCCW();
                break;
            case SPACE:
                sound = new AudioClip(new File("src/main/resources/sounds/Tetris(GB)-move_piece.wav").toURI().toString());
                sound.play();
                model.hardDrop();
                break;
            case R:
                model.reset();
                player.stop();
                player.play();
                break;
            default:
                return;
        }

        view.render(model);
    }
}
