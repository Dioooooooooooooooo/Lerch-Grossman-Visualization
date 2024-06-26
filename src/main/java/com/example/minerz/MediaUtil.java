package com.example.minerz;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MediaUtil {

    // SINGLETON Design Pattern

    private static final String MUSIC_FILE_PATH = "src/main/resources/music.mp3";
    private static final String SOUND_EFFECT_FILE_PATH = "src/main/resources/soundeffect.mp3";
    private static final String DIRT_FILE_PATH = "src/main/resources/dirt.mp3";

    private static final String LAND_MUSIC = "src/main/resources/LandMusic.mp3";

    private static MediaUtil instance;
    private MediaPlayer musicPlayer;
    private MediaPlayer soundEffectPlayer;
    private MediaPlayer dirtSoundPlayer;
    private MediaPlayer landMusicPlayer;

    private MediaUtil() {
        Media music = new Media(new File(MUSIC_FILE_PATH).toURI().toString());
        musicPlayer = new MediaPlayer(music);
        musicPlayer.setVolume(0.7);

        Media soundEffect = new Media(new File(SOUND_EFFECT_FILE_PATH).toURI().toString());
        soundEffectPlayer = new MediaPlayer(soundEffect);
        soundEffectPlayer.setStartTime(Duration.seconds(0.3));

        Media dirtSound = new Media(new File(DIRT_FILE_PATH).toURI().toString());
        dirtSoundPlayer = new MediaPlayer(dirtSound);
        dirtSoundPlayer.setStartTime(Duration.seconds(3.3));

        Media landMusic = new Media(new File(LAND_MUSIC).toURI().toString());
        landMusicPlayer = new MediaPlayer(landMusic);
        landMusicPlayer.setVolume(0.7);
    }

    public static MediaUtil getInstance() {
        if (instance == null) {
            synchronized (MediaUtil.class) {
                if (instance == null) {
                    instance = new MediaUtil();
                }
            }
        }
        return instance;
    }

    public void playMusic() {
        musicPlayer.play();
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }

    public void playSoundEffect() {
        soundEffectPlayer.stop();
        soundEffectPlayer.play();
    }

    public void stopSoundEffect() {
        if (soundEffectPlayer != null) {
            soundEffectPlayer.stop();
        }
    }

    public void playDirt() {
        dirtSoundPlayer.stop();
        dirtSoundPlayer.play();
    }

    public void stopDirt() {
        if (dirtSoundPlayer != null) {
            dirtSoundPlayer.stop();
        }
    }

    public void playLandMusic() {
        landMusicPlayer.play();
    }

    public void stopLandMusic() {
        if (landMusicPlayer != null) {
            landMusicPlayer.stop();
        }
    }
}
