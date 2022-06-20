package com.hk.musicplayer;

import com.hk.musicplayer.mp3.Playlist;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;

public class MainController {

    @FXML
    private Label current_song;

    @FXML
    private Label time;

    public void init() {

        Playlist playlist = new Playlist(new File("./resources/mp3/"), Math.random() * 100 > 50);

    }

}