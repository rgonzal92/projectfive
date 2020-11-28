package application.controller;

import application.model.YTDL;
import com.sapher.youtubedl.YoutubeDLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    YTDL object = YTDL.getInstance();

    @FXML
    private void download() throws YoutubeDLException {
        String videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        object.setExecPath();
        object.getRequest(videoUrl);
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

        AdvancedController controller = loader.getController();
        controller.initialize(object);

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
