package application.controller;

import application.model.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdvancedController implements Initializable {
    private final Config config = Config.getInstance();

    @FXML
    private ComboBox<String> videoFormatDropDown;
    @FXML
    private ComboBox<String> audioFormatDropDown;
    @FXML
    private ComboBox<String> videoResolutionDropDown;
    @FXML
    CheckBox audio,description,thumbnails,infojson,overwriting,playlist;

    public AdvancedController(){
        audio=new CheckBox();
        description=new CheckBox();
        thumbnails=new CheckBox();
        infojson=new CheckBox();
        overwriting=new CheckBox();
        playlist=new CheckBox();
    }
    private void getTexts() {
        videoFormatDropDown.getItems().addAll("3gp", "flv", "webm", "mp4");
        videoFormatDropDown.setValue(config.getVideoFormat());
        audioFormatDropDown.getItems().addAll("mp3", "wav", "acc", "m4a", "flac");
        audioFormatDropDown.setValue(config.getAudioFormat());
        videoResolutionDropDown.getItems().addAll("480", "720", "1080", "1440", "2160p");
        videoResolutionDropDown.setValue(config.getVideoResolution());
    }

    @FXML
    private void save() throws IOException {
        config.setVideoFormat(videoFormatDropDown.getSelectionModel().getSelectedItem());
        config.setAudioFormat(audioFormatDropDown.getSelectionModel().getSelectedItem());
        config.setVideoResolution(videoResolutionDropDown.getSelectionModel().getSelectedItem());
        config.setAudioOnly(audio.isSelected());
        config.setWriteDescription(description.isSelected());
        config.setWriteThumbnails(thumbnails.isSelected());
        config.setWriteinfoJsons(thumbnails.isSelected());
        config.setAcceptPlayLists(playlist.isSelected());
        config.setNoOverWriting(overwriting.isSelected());
        config.writeConfig();
    }

    @FXML
    private void switchToMainScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
        Parent root = loader.load();

        Scene zoneScene = new Scene(root, 600, 500);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(zoneScene);
        primaryStage.show();
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getTexts();

    }
}
