package com.example.virtualmusicpad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

import java.io.IOException;

public class MusicApp extends Application {
    public static HashMap<String, MediaPlayer> sounds = new HashMap<String, MediaPlayer>();
    public static File soundpacksFolder = new File("soundpacks");

    public static HashMap<String, Soundpack> allSoundpacks = new HashMap<>();

    public static HashMap<String, Pattern> patterns = new HashMap<>();

    //public static ArrayList<Soundpack> soundpacks = new ArrayList<>();

    public static Pattern currentPattern;

    public static String selectedPattern;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(soundpacksFolder.exists());

        String path = "C:/Users/adria/Desktop/sounds/";

        System.out.println("All soundpacks length: "+allSoundpacks.size());

        /*sounds.put("X",new MediaPlayer(new Media(new File(path + "kick.wav").toURI().toString())));
        sounds.put("C",new MediaPlayer(new Media(new File(path + "clap.wav").toURI().toString())));
        sounds.put("V",new MediaPlayer(new Media(new File(path + "highhats.wav").toURI().toString())));
        sounds.put("B",new MediaPlayer(new Media(new File(path + "808.wav").toURI().toString())));
        sounds.put("N",new MediaPlayer(new Media(new File(path + "crash.wav").toURI().toString())));
        sounds.put("M",new MediaPlayer(new Media(new File(path + "fx.wav").toURI().toString())));
        sounds.put("COMMA",new MediaPlayer(new Media(new File(path + "openhat.wav").toURI().toString())));
        sounds.put("PERIOD",new MediaPlayer(new Media(new File(path + "percs.wav").toURI().toString())));
        sounds.put("Q",new MediaPlayer(new Media(new File(path + "snap.wav").toURI().toString())));
        sounds.put("W",new MediaPlayer(new Media(new File(path + "snare.wav").toURI().toString())));
        sounds.put("E",new MediaPlayer(new Media(new File(path + "chant1.mp3").toURI().toString())));
        sounds.put("R",new MediaPlayer(new Media(new File(path + "chant2.wav").toURI().toString())));
        sounds.put("T",new MediaPlayer(new Media(new File(path + "chant3.wav").toURI().toString())));
        sounds.put("Y",new MediaPlayer(new Media(new File(path + "chant4.wav").toURI().toString())));
        sounds.put("U",new MediaPlayer(new Media(new File(path + "chant5.wav").toURI().toString())));
        sounds.put("I",new MediaPlayer(new Media(new File(path + "chant6.wav").toURI().toString())));
        sounds.put("O",new MediaPlayer(new Media(new File(path + "chant7.wav").toURI().toString())));
        sounds.put("P",new MediaPlayer(new Media(new File(path + "chant8.wav").toURI().toString())));
        sounds.put("OPEN_BRACKET",new MediaPlayer(new Media(new File(path + "chant9.wav").toURI().toString())));
        sounds.put("CLOSE_BRACKET",new MediaPlayer(new Media(new File(path + "chant10.wav").toURI().toString())));*/

        patterns.put("PATTERN 1", new Pattern());

        FXMLLoader fxmlLoader = new FXMLLoader(MusicApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }

}