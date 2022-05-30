package kr.ac.kpu.sgp02.termproject.framework.pool;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.framework.view.GameView;

public class Sound {
    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;

    public static void playMusic(int redId) {
        if(mediaPlayer != null)
            mediaPlayer.stop();

        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), redId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public static void stopMusic() {
        if(mediaPlayer == null)
            return;

        mediaPlayer.stop();
        mediaPlayer = null;
    }

    public static void resumeMusic() {
        if(mediaPlayer == null)
            return;

        mediaPlayer.start();
    }

    public static void pauseMusic() {
        if(mediaPlayer == null)
            return;

        mediaPlayer.pause();
    }

    private static HashMap<Integer, Integer> soundIds = new HashMap<>();

    public static void playSfx(int resId) {
        SoundPool soundPool = getSoundPool();

        int soundId;

        if(soundIds.containsKey(resId)) {
            soundId = soundIds.get(resId);
        }
        else {
            soundId = soundPool.load(GameView.view.getContext(), resId, 1);
            soundIds.put(resId, soundId);
        }

        soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    private static SoundPool getSoundPool() {
        if(soundPool == null) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(3)
                    .build();
        }

        return soundPool;
    }
}
