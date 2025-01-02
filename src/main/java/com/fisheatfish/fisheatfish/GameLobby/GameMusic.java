package com.fisheatfish.fisheatfish.GameLobby;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameMusic {
    private Map<String, Clip> musicMap;  // Store different music tracks by key

    public GameMusic() {
        musicMap = new HashMap<>();  // Initialize the map to store multiple tracks
        // Add music files with unique keys for background music
        addMusic("gamePlay", "C:/Users/A S U S/OneDrive/Desktop/study/Degree(UM)/Sem 1/WIX1002/FishEatFish/FIshEatFish/src/main/java/com/fisheatfish/fisheatfish/Asset/Music/EvolvingDepths_ext_v1.mp3");
        addMusic("gameOver", "C:/Users/A S U S/OneDrive/Desktop/study/Degree(UM)/Sem 1/WIX1002/FishEatFish/FIshEatFish/src/main/java/com/fisheatfish/fisheatfish/Asset/Music/game_over.wav");  // Example: Add other music track
        addMusic("buttonEffect", "C:/Users/A S U S/OneDrive/Desktop/study/Degree(UM)/Sem 1/WIX1002/FishEatFish/FIshEatFish/src/main/java/com/fisheatfish/fisheatfish/Asset/Music/buttonEffect.wav");  // Example: Add other music track        
    }

    // Method to add background music to the Clip
    private void addMusic(String key, String filePath) {
        try {
            File musicFile = new File(filePath);  // The MP3 file path
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Convert the MP3 file to PCM format
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            // Open the clip and load the audio data
            Clip newClip = AudioSystem.getClip();
            newClip.open(decodedStream);
            musicMap.put(key, newClip);  // Store the clip in the map with the key
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading music file for " + key + ": " + e.getMessage());
        }
    }

    // Play background music by key
    public void playMusic(String key) {
        if (musicMap.containsKey(key)) {
            Clip clip = musicMap.get(key);  // Get the new music clip
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the music
            clip.start();  // Start playback
        } else {
            System.out.println("Music for key " + key + " not loaded.");
        }
    }

    // Stop background music by key
    public void stopMusic(String key) {
        if (musicMap.containsKey(key)) {
            Clip clip = musicMap.get(key);  // Get the music clip for the given key
            clip.stop();  // Stop playback
        } else {
            System.out.println("Music for key " + key + " not loaded.");
        }
    }

    // Pause background music by key
    public void pauseMusic(String key) {
        if (musicMap.containsKey(key)) {
            Clip clipToPause = musicMap.get(key);
            clipToPause.stop();  // Stop the music by key
        } else {
            System.out.println("Music for key " + key + " not loaded.");
        }
    }


    // Set volume for music by key
    public void setVolume(String key, float volume) {
        if (musicMap.containsKey(key)) {
            Clip clip = musicMap.get(key);  // Get the music clip for the given key
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);  // Convert volume to decibels
            volumeControl.setValue(dB);  // Set the volume
        } else {
            System.out.println("Music for key " + key + " not loaded.");
        }
    }
    
    public void playEffect(String key) {
        if (musicMap.containsKey(key)) {
            Clip clip = musicMap.get(key);  // Get the new music clip
            clip.loop(1);  // Loop the music
            clip.start();  // Start playback
        } else {
            System.out.println("Music for key " + key + " not loaded.");
        }
    }
}
