package com.hk.musicplayer.mp3;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Playlist {

    private final ArrayList<File> songs;

    private final File directory;

    private Media media;
    private MediaPlayer mediaPlayer;

    private String current_song;
    private int index;

    public Playlist(File directory, boolean shuffle) {
        this.directory = directory;
        if (!directory.isDirectory())
            throw new IllegalArgumentException("ERROR! File path is not a directory");

        this.songs = new ArrayList<>();

        this.addAllSongs();

        if (shuffle) {
            this.shuffle();
            System.out.println("Playlist has been shuffled");
        }

        this.printSongs();

        this.media = new Media(songs.get(0).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.current_song = songs.get(0).getName();

        play();

    }

    public void play() {
        if (mediaPlayer == null || media == null) {
            System.err.println("ERROR! Could not play song");
            return;
        }

        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            System.err.println("ERROR! There is already a song playing.");
            return;
        }

        mediaPlayer.play();
        System.out.println("Playing: " + current_song);

    }

    public void stop() {
        if (mediaPlayer == null || media == null) {
            System.err.println("ERROR! Could not stop the song.");
            return;
        }

        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            System.err.println("ERROR! There are no songs currently playing.");
            return;
        }

        mediaPlayer.stop();
        System.out.println("Stopped song: " + current_song);

        media = null;
        mediaPlayer = null;

    }

    public void resume() {
        if (mediaPlayer == null || media == null) {
            System.err.println("ERROR! Could not resume song.");
            return;
        }

        if (mediaPlayer.getStatus() != MediaPlayer.Status.PAUSED) {
            System.err.println("ERROR! The song is currently " + mediaPlayer.getStatus() + " and cannot be resumed.");
            return;
        }

        mediaPlayer.play();

    }

    public void pause() {
        if (mediaPlayer == null || media == null) {
            System.err.println("ERROR! Could not pause the song.");
            return;
        }

        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            System.err.println("ERROR! There are currently no songs playing.");
            return;
        }

        mediaPlayer.pause();
        System.out.println("Pausing " + current_song + ".");

    }

    public void next() {

        stop();

        for (int i = 0; i <  songs.size(); i++) {
            if (songs.get(i).getName().equalsIgnoreCase(current_song)) {
                index = i;
                index++;
                break;
            }
        }

        if (current_song.equalsIgnoreCase(songs.get(songs.size() - 1).getName())) {
            System.out.println("Playlist has finished, playing first song: " + songs.get(0).getName());
            index = 0;
        }

        current_song = songs.get(index).getName();

        Platform.startup(() -> {
            media = new Media(songs.get(index).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            play();
        });

    }

    public String formatTime(long duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration), TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    private void addAllSongs() {
        for (File song : Objects.requireNonNull(directory.listFiles())) {
            if (song == null || !song.getName().endsWith(".mp3") || songs.contains(song))
                continue;
            songs.add(song);
        }
        System.out.println("Initialised " + songs.size() + " mp3 files");
    }

    private void shuffle() {
        if (songs == null || songs.isEmpty()) {
            System.err.println("ERROR! There are no songs initialised.");
            return;
        }
        Collections.shuffle(songs);
    }

    private void printSongs() {
        if (songs.size() >= 20)
            return;
        songs.forEach(song -> System.out.println(song.getName().split(".mp3")[0]));
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
