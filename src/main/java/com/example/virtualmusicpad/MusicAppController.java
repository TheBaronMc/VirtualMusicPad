package com.example.virtualmusicpad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class MusicAppController {
    public HashMap<String, Button> keys;

    @FXML
    private TableView<Sample> patternTable;

    private Soundpack currentSoundpack = null;

    @FXML
    private AnchorPane mainPane;

    //private ArrayList<Soundpack> soundpacks = new ArrayList<>();

    @FXML
    private ListView<String> soundpackListView;

    @FXML
    public void initialize(){
        this.keys = new HashMap<String, Button>();
        keys.put("E",E);
        keys.put("R",R);
        keys.put("T",T);
        keys.put("Y",Y);
        keys.put("U",U);
        keys.put("I",I);
        keys.put("O",O);
        keys.put("P",P);
        keys.put("OPEN_BRACKET",openBracket); keys.put("[",openBracket);
        keys.put("CLOSE_BRACKET",closeBracket); keys.put("]",closeBracket);
        keys.put("X",X);
        keys.put("C",C);
        keys.put("V",V);
        keys.put("B",B);
        keys.put("N",N);
        keys.put("M",M);
        keys.put("COMMA",comma); keys.put(",",comma);
        keys.put("PERIOD",period); keys.put(".",period);
        keys.put("Q",Q);
        keys.put("W",W);

        //
        // Load all the soundpacks
        for (File file : MusicApp.soundpacksFolder.listFiles()) {
            if (file.exists() && file.isDirectory())
                MusicApp.allSoundpacks.put(file.getName(), new Soundpack(file.getName()));
        }

        // update soundpacks list view
        ObservableList<String> soundpackList = FXCollections.observableArrayList();
        soundpackList.addAll(MusicApp.allSoundpacks.values().stream().map(Soundpack::getName).toList());
        soundpackListView.setItems(soundpackList);

        updateCbPattern();
        updateCbSoundpack();
    }

    @FXML
    private Button B;

    @FXML
    private Button C;

    @FXML
    private Button E;

    @FXML
    private Button I;

    @FXML
    private Button M;

    @FXML
    private Button N;

    @FXML
    private Button O;

    @FXML
    private Button P;

    @FXML
    private Button Q;

    @FXML
    private Button R;

    @FXML
    private Button T;

    @FXML
    private Button U;

    @FXML
    private Button V;

    @FXML
    private Button W;

    @FXML
    private Button X;

    @FXML
    private Button Y;

    @FXML
    private Button comma;

    @FXML
    private Button openBracket;

    @FXML
    private Button period;

    @FXML
    private Button closeBracket;

    @FXML
    private Tab trackTab;

    @FXML
    void show(KeyEvent event) {
        System.out.println("Any key pressed - out 2");
    }

    @FXML
    void clicked(MouseEvent event) {
        System.out.println("Click pressed - out");
    }

    @FXML
    void pressed(KeyEvent event) {
        System.out.println(event.getCode().toString());
        if(MusicApp.sounds.containsKey(event.getCode().toString()))
        {
            for (String sound : MusicApp.sounds.keySet())
            {
                System.out.print(sound+" ");
            }
            MusicApp.sounds.get(event.getCode().toString()).play();

            keys.get(event.getCode().toString()).setStyle("-fx-background-color: #ff5e66; -fx-text-fill: #ffffff");

            System.out.println(event.getCode().toString());
        }
        else {
            // alert message; -> key doesnt contain any sound
        }
    }

    @FXML
    void released(KeyEvent event) {
        if(MusicApp.sounds.containsKey(event.getCode().toString()))
        {
            MusicApp.sounds.get(event.getCode().toString()).stop();

            keys.get(event.getCode().toString()).setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #000000");
        }
    }

    @FXML
    public void onPlaySoundBtnPressed(MouseEvent e) {
        if(getSelectedSoundpackName().equals("empty")==false)
        {
            Button button = (Button) e.getSource();

            // On left click
            if (e.getButton() == MouseButton.PRIMARY) {
                {
                    String buttonText = button.getText();

                    switch(button.getText())
                    {
                        case ".":
                            buttonText = "PERIOD";
                            break;
                        case ",":
                            buttonText = "COMMA";
                            break;
                        case "[":
                            buttonText = "OPEN_BRACKET";
                            break;
                        case "]":
                            buttonText = "CLOSE_BRACKET";
                            break;
                    }

                    if(MusicApp.sounds.containsKey(buttonText))
                    {
                        MusicApp.sounds.get(buttonText).play();
                        keys.get(buttonText).setStyle("-fx-background-color: #ff5e66; -fx-text-fill: #ffffff");
                    }
                }
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No soundpack selected!");
            alert.setContentText("You need to select a soundpack.");
            alert.showAndWait();
        }

    }

    @FXML
    void onPlaySoundBtnReleased(MouseEvent e) {
        Button button = (Button) e.getSource();

        String buttonText = button.getText();

        switch(button.getText())
        {
            case ".":
                buttonText = "PERIOD";
                break;
            case ",":
                buttonText = "COMMA";
                break;
            case "[":
                buttonText = "OPEN_BRACKET";
                break;
            case "]":
                buttonText = "CLOSE_BRACKET";
                break;
        }

        if(MusicApp.sounds.containsKey(buttonText))
        {
            MusicApp.sounds.get(buttonText).stop();
            keys.get(buttonText).setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #000000");
        }
    }

    /*@FXML
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
    }*/

        @FXML
    private ComboBox<String> cbPattern;

    @FXML
    private ComboBox<String> cbSoundpack;

    public void updateCbSoundpack(){
        cbSoundpack.getItems().removeAll(cbSoundpack.getItems());

        for(String soundpackName : MusicApp.patterns.get(getSelectedPatternName()).soundpacks.keySet())
            cbSoundpack.getItems().add(soundpackName);

        if(cbSoundpack.getItems().size()>0)
            cbSoundpack.getSelectionModel().select(0);
    }

    String getSelectedPatternName(){
        return cbPattern.getSelectionModel().getSelectedItem().toString();
    }

    String getSelectedSoundpackName(){
        if(cbSoundpack.getItems().isEmpty())
        {
            return "empty";
        }
        return cbSoundpack.getSelectionModel().getSelectedItem().toString();
    }

    String getSelectedItem(){
        return soundpackListView.getSelectionModel().getSelectedItem().toString();
    }

    @FXML
    void btnAddSoundpackToPattern(MouseEvent event) {

        // conditie daca un item e selectat

        String selectedPatternName = getSelectedPatternName();
        String selectedSoundpackName = getSelectedItem();

        System.out.println("Selected Pattern: "+ selectedPatternName+"\nSelected Soundpack item: "+selectedSoundpackName);

        if(MusicApp.patterns.get(selectedPatternName).soundpacks.containsKey(selectedSoundpackName))
        {
            // Display error message: "selectedPatternName already contains selectedSoundpackName Soundpack!"
        }
        else
        {
            MusicApp.patterns.get(selectedPatternName).soundpacks.put(selectedSoundpackName, MusicApp.allSoundpacks.get(selectedSoundpackName));

            cbSoundpack.getItems().add(selectedSoundpackName);
            cbSoundpack.getSelectionModel().select(selectedSoundpackName);

            MusicApp.sounds.clear();
            Soundpack selectedSoundpack = MusicApp.allSoundpacks.get(selectedSoundpackName);

            updateKeyBindings(selectedSoundpack);

            soundpackListView.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private Button btnNewPattern;

    @FXML
    void btnNewPattern(MouseEvent event) {
        Pattern pattern = new Pattern();
        MusicApp.patterns.put(pattern.name, pattern);
        cbPattern.getItems().add(pattern.name);
        cbPattern.getSelectionModel().select(pattern.name);
    }

    @FXML
    void cbSoundpackAction(ActionEvent event) {
        String selectedSoundpackName = getSelectedSoundpackName();

        if(selectedSoundpackName.equals("empty"))
        {
            MusicApp.sounds.clear();
        }
        else
        {
            Soundpack selectedSoundpack = MusicApp.allSoundpacks.get(selectedSoundpackName);
            updateKeyBindings(selectedSoundpack);
        }
    }

    private void updateKeyBindings(Soundpack soundpack){
        for(String key : soundpack.binding.keySet())
        {
            MusicApp.sounds.put(key, new MediaPlayer(new Media(new File(MusicApp.soundpacksFolder.getAbsolutePath()+File.separator+soundpack.getName()+File.separator+soundpack.binding.get(key)).toURI().toString())));
        }
    }

    public void updateCbPattern(){
        cbPattern.getItems().removeAll(cbPattern.getItems());

        for(String patternName : MusicApp.patterns.keySet())
            cbPattern.getItems().add(patternName);

        if(cbPattern.getItems().size()>0)
            cbPattern.getSelectionModel().select(0);
    }

    @FXML
    void cbPatternAction(ActionEvent event) {
        updateCbSoundpack();
    }
}