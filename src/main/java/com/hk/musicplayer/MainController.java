package com.hk.musicplayer;

import com.hk.musicplayer.mp3.Playlist;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MainController {

    private final Playlist playlist = new Playlist(new File("./resources/mp3/"), true);

    private final Timer timer = new Timer();

    @FXML
    private Label current_song;

    @FXML
    private Label time;

    public void observeSongDuration() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> {

                    long currentTime = (long) playlist.getMediaPlayer().getCurrentTime().toMillis();
                    long songDuration = (long) playlist.getMediaPlayer().getTotalDuration().toMillis();

                    if (currentTime == songDuration) {
                        playlist.forward();
                        System.out.println("Song finished, now playing: " + playlist.getCurrentSong());
                        return;
                    }

                    current_song.setText(playlist.getCurrentSong());
                    time.setText(playlist.formatTime(currentTime));

                });

            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);

    }

    public Playlist getPlaylist() {
        return playlist;
    }
}