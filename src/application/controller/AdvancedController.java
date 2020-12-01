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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdvancedController implements Initializable {
    private final Config config = Config.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ComboBox<String> videoFormatDropDown, audioFormatDropDown, videoResolutionDropDown;
    @FXML
    private CheckBox audioCheckBox, descriptionCheckBox, thumbnailsCheckBox, infoJsonCheckBox, overwritingCheckBox, playlistCheckBox;
    @FXML
    private TextField directoryTextField;
    @FXML
    private Label Settingssaved;

    public AdvancedController() {
        Settingssaved = new Label();

    }

    private void generateOptions() {
        videoFormatDropDown.getItems().addAll("3gp", "flv", "webm", "mp4");
        videoFormatDropDown.setValue(config.getVideoFormat());
        audioFormatDropDown.getItems().addAll("Disabled", "mp3", "wav", "acc", "m4a", "flac");
        audioFormatDropDown.setValue(config.getAudioFormat());
        videoResolutionDropDown.getItems().addAll("480", "720", "1080", "1440", "2160");
        videoResolutionDropDown.setValue(config.getVideoResolution());

        if (config.isAudioOnly()) audioCheckBox.setSelected(true);
        if (config.isWriteDescription()) descriptionCheckBox.setSelected(true);
        if (config.isWriteThumbnails()) thumbnailsCheckBox.setSelected(true);
        if (config.isWriteInfoJsons()) infoJsonCheckBox.setSelected(true);
        if (config.isNoOverWriting()) overwritingCheckBox.setSelected(true);
        if (config.isAcceptPlayLists()) playlistCheckBox.setSelected(true);

        directoryTextField.setPromptText(config.getSaveDirectory());
    }

    @FXML
    private void save() throws IOException {
        config.setVideoFormat(videoFormatDropDown.getSelectionModel().getSelectedItem());
        config.setAudioFormat(audioFormatDropDown.getSelectionModel().getSelectedItem());
        config.setVideoResolution(videoResolutionDropDown.getSelectionModel().getSelectedItem());
        config.setAudioOnly(audioCheckBox.isSelected());
        config.setWriteDescription(descriptionCheckBox.isSelected());
        config.setWriteThumbnails(thumbnailsCheckBox.isSelected());
        config.setWriteInfoJsons(thumbnailsCheckBox.isSelected());
        config.setAcceptPlayLists(playlistCheckBox.isSelected());
        config.setNoOverWriting(overwritingCheckBox.isSelected());
        if (config.getSaveDirectory().isEmpty() && directoryTextField.getText().isEmpty())
            config.setSaveDirectory(System.getProperty("user.bin"));
        else if (directoryTextField.getText().isEmpty())
            config.setSaveDirectory(config.getSaveDirectory());
//            config.setSaveDirectory(""); -- causing a blank path after saving twice? please confirm
        else
            config.setSaveDirectory(directoryTextField.getText());
        try {
            config.writeConfig();
            Settingssaved.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void browseDirectory() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        if (file != null)
            directoryTextField.setText(file.getAbsolutePath());
    }

    @FXML
    private void switchToMainScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
        Parent root = loader.load();
        Scene zoneScene = new Scene(root, 700, 600);
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
        generateOptions();
    }
}
