package com.hk.musicplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launch extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MainController controller = fxmlLoader.getController();

        controller.observeSongDuration();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case P -> controller.getPlaylist().pause();
                case R -> controller.getPlaylist().resume();
                case S -> controller.getPlaylist().forward();
                case B -> controller.getPlaylist().backward();
                default -> System.err.println("Unhandled keycode: " + e.getCode());
            }
        });

        stage.setTitle("Music player");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}