package com.example.virtualmusicpad;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;

public class HelloController {
    @FXML
    private Label welcomeText;

    private ArrayList<Soundpack> soundpacks = new ArrayList<>();
    private Soundpack currentSoundpack = null;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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
            ChoiceDialog<String> dialog = new ChoiceDialog<>("b", currentSoundpack.getAvailableSounds());
            dialog.setTitle("Binding Dialog" + button.getText());
            dialog.setHeaderText("Binding for key " + button.getText());
            dialog.setContentText("Choose a sound:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                currentSoundpack.addBinding(button.getText(), result.get());
            }
        }

        // On left click
        if (e.getButton() == MouseButton.PRIMARY) {
            currentSoundpack.play(button.getText());
        }
    }
}