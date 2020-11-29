package application.controller;

import application.model.YTDL;
import com.sapher.youtubedl.YoutubeDLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private final YTDL youtube_dl = YTDL.getInstance();

    @FXML
    private TextArea urlLinksTextArea;
    @FXML
    private TextArea outputTextArea;

    @FXML
    private void download() throws YoutubeDLException {
        outputTextArea.clear();
        youtube_dl.setExecPath();
        for (String link : urlLinksTextArea.getText().split("\\n")) {
            outputTextArea.appendText(youtube_dl.getRequest(link).replaceAll("\\r", "\n"));
        }
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

        Scene personnelScene = new Scene(root, 600, 500);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(personnelScene);
        primaryStage.show();
    }
}
