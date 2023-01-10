package com.example.virtualmusicpad;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class HelloController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ListView<String> soundpackListView;

    @FXML
    private GridPane patternGridView;

    private ArrayList<Soundpack> soundpacks = new ArrayList<>();
    private Soundpack currentSoundpack = null;

    private ArrayList<ArrayList<PatternViewCell>> patternRows;

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

        currentSoundpack = soundpacks.get(0);

        // Iinit pattern grid
        patternRows = new ArrayList<>();

        for (int irow = 2; irow < 22; irow++) {

            ArrayList<PatternViewCell> patternRow = new ArrayList<>();

            for (int icol = 0; icol < 32; icol++) {
                PatternViewCell cell = new PatternViewCell(120.0, 31.0);
                cell.setFill(Paint.valueOf("WHITE"));
                cell.setStroke(Paint.valueOf("BLACK"));
                cell.setStrokeType(StrokeType.INSIDE);
                patternGridView.add(cell, icol, irow);

                if (icol > 0) {
                    PatternViewCell previousCell = patternRow.get(patternRow.size()-1);
                    previousCell.setRightCell(cell);
                    cell.setLeftCell(previousCell);
                }

                cell.setOnMouseClicked( e -> {
                    cell.setFill(Paint.valueOf("BLUE"));
                });

                patternRow.add(cell);
            }

            patternRows.add(patternRow);
        }

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

    private class PatternViewCell extends Rectangle {

        private PatternViewCell leftCell;
        private PatternViewCell rightCell;

        public PatternViewCell(double width, double height) {
            super(width, height);
        }

        public PatternViewCell getLeftCell() {
            return leftCell;
        }

        public void setLeftCell(PatternViewCell leftCell) {
            this.leftCell = leftCell;
        }

        public PatternViewCell getRightCell() {
            return rightCell;
        }

        public void setRightCell(PatternViewCell rightCell) {
            this.rightCell = rightCell;
        }
    }
}