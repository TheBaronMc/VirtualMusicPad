package com.example.virtualmusicpad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<String> soundpackListView;

    private ArrayList<Soundpack> soundpacks = new ArrayList<>();
    private Soundpack currentSoundpack = null;

    @FXML
    public void initialize() {
        // Load all the soundpacks
        for (File file : HelloApplication.soundpacksFolder.listFiles()) {
            if (file.exists() && file.isDirectory())
                soundpacks.add(new Soundpack(file.getName()));
        }

        // update soundpacks list view
        ObservableList<String> soundpackList = FXCollections.observableArrayList();
        soundpackList.addAll(soundpacks.stream().map(Soundpack::getName).toList());
        soundpackListView.setItems(soundpackList);
    }

    @FXML
    public void onPlaySoundBtnPressed(MouseEvent e) {
        if (currentSoundpack == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No soundpack selected!");
            alert.setContentText("You need to select a soundpack.");
            alert.showAndWait();
            return;
        }

        Button button = (Button) e.getSource();

        // On right click
        if (e.getButton() == MouseButton.SECONDARY) {
            FileChooser browser = new FileChooser();
            browser.setTitle("Choose audio file");
            browser.setSelectedExtensionFilter(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3")
            );
            File file = browser.showOpenDialog(mainPane.getScene().getWindow());

            // Check sound path
            File soundFile;
            if (!file.getPath().startsWith(currentSoundpack.getPath())) {
                soundFile = new File(currentSoundpack.getPath() + File.separator + file.getName());
                try {
                    Files.copy(file.toPath(), soundFile.toPath());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                soundFile = file;
            }

            // bind sound to key
            currentSoundpack.addBinding(button.getText(), soundFile.getName());
        }

        // On left click
        if (e.getButton() == MouseButton.PRIMARY) {
            currentSoundpack.play(button.getText());
        }
    }
}