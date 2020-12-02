package application.controller;

import application.model.Config;
import application.model.YTDL;
import com.sapher.youtubedl.YoutubeDLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final YTDL youtube_dl = YTDL.getInstance();
    private final Config config = Config.getInstance();

    @FXML
    private TextArea urlTextArea;
    @FXML
    private TextArea outputTextArea;
/*
    @FXML
    private void download() {
        //try {
            if (urlTextArea.getText().isEmpty())
                outputTextArea.setText("Must provide at least one link.");
            else
                for (String url : urlTextArea.getText().split("\n")) {
                    outputTextArea.clear();
                    youtube_dl.OutputText = outputTextArea;
                    //outputTextArea.appendText(youtube_dl.getRequest(url, config.getSaveDirectory()).replaceAll("\\r", "\n")); // format error?
                    youtube_dl.getRequest(url, config.getSaveDirectory());
                }

    }*/
@FXML
private void download() {
    //try {
    if (urlTextArea.getText().isEmpty())
        outputTextArea.setText("Must provide at least one link.");
    else
        youtube_dl.OutputText = outputTextArea;
        youtube_dl.getRequest(urlTextArea.getText().split(("\n")), config.getSaveDirectory());
        /*for (String url : urlTextArea.getText().split("\n")) {
            outputTextArea.clear();
            youtube_dl.OutputText = outputTextArea;
            //outputTextArea.appendText(youtube_dl.getRequest(url, config.getSaveDirectory()).replaceAll("\\r", "\n")); // format error?
            youtube_dl.getRequest(url, config.getSaveDirectory());
        }*/

}

    @FXML
    private void switchToCreditsScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String scene = "../view/credits.fxml";
        loadScene(actionEvent, loader, scene);
    }

    @FXML
    private void switchToAdvancedScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String scene = "../view/advanced.fxml";
        loadScene(actionEvent, loader, scene);
    }

    private void loadScene(ActionEvent actionEvent, FXMLLoader loader, String scene) throws IOException {
        loader.setLocation(getClass().getResource(scene));
        Parent root = loader.load();
        Scene personnelScene = new Scene(root, 700, 600);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(personnelScene);
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
        youtube_dl.setExecPath();
        try {
            config.loadConfig();
            // fixes nullpointerexception if config
            // doesn't contain output template string
            if (config.getOutputTemplate() == null)
                config.setOutputTemplate("%(title)s.%(ext)s");
        } catch (IOException ignored) {
            System.err.println("IOException thrown in MainController.java");
        }
    }
}
