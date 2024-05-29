package com.example.minerz;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaUtil {
    private static final String MUSIC_FILE_PATH = "src/main/resources/music.mp3";
    private static MediaPlayer mediaPlayer;

    static {
        Media sound = new Media(new File(MUSIC_FILE_PATH).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.7);
    }

    public static void playMusic() {
        mediaPlayer.play();
    }

    public static void stopMusic() {
        mediaPlayer.stop();
    }
}
