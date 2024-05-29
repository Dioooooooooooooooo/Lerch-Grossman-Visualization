    package com.example.minerz;

    import javafx.scene.media.Media;
    import javafx.scene.media.MediaPlayer;
    import javafx.util.Duration;

    import java.io.File;

    public class MediaUtil {

        // SINGLETON

        private static final String MUSIC_FILE_PATH = "src/main/resources/music.mp3";
        private static final String SOUND_EFFECT_FILE_PATH = "src/main/resources/soundeffect.mp3";
        private static MediaUtil instance;
        private MediaPlayer musicPlayer;
        private MediaPlayer soundEffectPlayer;

        private MediaUtil() {
            Media music = new Media(new File(MUSIC_FILE_PATH).toURI().toString());
            musicPlayer = new MediaPlayer(music);
            musicPlayer.setVolume(0.7);

            Media soundEffect = new Media(new File(SOUND_EFFECT_FILE_PATH).toURI().toString());
            soundEffectPlayer = new MediaPlayer(soundEffect);

            soundEffectPlayer.setStartTime(Duration.seconds(0.3));
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
    }
