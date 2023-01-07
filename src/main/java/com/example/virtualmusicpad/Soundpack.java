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
    private List<String> availableSounds;

    /**
     * Create a new soundpack
     * @param name name of the soundpack
     * @param folderPath folder of the soundpack
     */
    public Soundpack(String name, String folderPath) {
        this.name = name;
        this.path = folderPath;
        this.binding = new HashMap<>();

        // Get all the available sounds in the folder
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory())
            throw new IllegalArgumentException(folderPath + " doesn't exist or not a directory");
        availableSounds = Arrays.stream(folder.listFiles((d, n) -> n.endsWith(".wav") || n.endsWith(".mp3")))
                .map(File::getName).toList();
    }

    /**
     * Create a new soundpack
     * The soundpack will take the folder name as name
     * @param folderPath
     */
    public Soundpack(String folderPath) {
        this(new File(folderPath).getName(), folderPath);
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

        if (sound != null)
            sound.play();
    }

    /**
     * Get all the sounds available in the soundpack
     * @return sounds name
     */
    public List<String> getAvailableSounds() {
        return availableSounds;
    }

    /**
     * Return the name of the Soundpack
     * @return Soundpack's name
     */
    public String getName() {
        return name;
    }

    private class Key {
        private final String keyValue;

        public Key(String keyValue) {
            this.keyValue = keyValue;
        }

        @Override
        public boolean equals(Object obj) {
            return keyValue.equals(obj);
        }
    }
}
