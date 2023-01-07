package com.example.virtualmusicpad;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class HelloController {
    @FXML
    private AnchorPane mainPane;

    private ArrayList<Soundpack> soundpacks = new ArrayList<>();
    private Soundpack currentSoundpack = null;

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
        }

        // On left click
        if (e.getButton() == MouseButton.PRIMARY) {
            currentSoundpack.play(button.getText());
        }
    }
}