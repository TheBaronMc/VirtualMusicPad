package com.example.virtualmusicpad;

import java.io.File;
import java.io.FilenameFilter;
import java.net.FileNameMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Soundpack {
    private final String name;
    private final String path;
    private HashMap<Key, Sound> binding;

    /**
     * Create a new soundpack
     * @param name name of the soundpack
     */
    public Soundpack(String name) {
        this.name = name;
        this.path = HelloApplication.soundpacksFolder.getPath() + File.separator + name;
        this.binding = new HashMap<>();
    }

    /**
     * Add a binding to the soundpack
     * If the key value is already associated, it will be replaced
     * @param keyValue
     * @param soundName
     */
    public void addBinding(String keyValue, String soundName) {
        File soundFile = new File(path + File.separator + soundName);
        binding.put(new Key(keyValue), new Sound(soundFile));
    }

    /**
     * Play the sound associated to the given key value
     * If no sound associated, no sound played
     * @param keyValue
     */
    public void play(String keyValue) {
        Sound sound = binding.get(new Key(keyValue));
        System.out.println(sound);

        if (sound != null)
            sound.play();
    }

    /**
     * Get all the sounds available in the soundpack
     * @return sounds name
     */
    public List<String> getAvailableSounds() {
        File folder = new File(path);
        if (!folder.exists() || !folder.isDirectory())
            throw new IllegalArgumentException(folder.getAbsolutePath() + " doesn't exist or not a directory");
        return Arrays.stream(folder.listFiles((d, n) -> n.endsWith(".wav") || n.endsWith(".mp3")))
                .map(File::getName).toList();
    }

    /**
     * Return the name of the Soundpack
     * @return Soundpack's name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the path of the Soundpack
     * @return Soundpack's path
     */
    public String getPath() { return path; }

    private class Key {
        private final String keyValue;

        public Key(String keyValue) {
            this.keyValue = keyValue;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof  Key) &&
                    keyValue.equals(((Key) obj).keyValue);
        }

        @Override
        public int hashCode() {
            return keyValue.hashCode();
        }
    }
}
