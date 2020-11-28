package application.controller;

import application.model.YTDL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class AdvancedController {
    @FXML
    private ComboBox<String> videoQualityMenu;

    @FXML
    private void switchToMainScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
        Parent root = loader.load();

        Scene zoneScene = new Scene(root, 600, 500);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(zoneScene);
        primaryStage.show();
    }

    private HashMap<String, String> getVideoQualitySettings() {
        HashMap<String, String> sample = new HashMap<>();
        switch (videoQualityMenu.getSelectionModel().getSelectedItem()) {
            case "Medium":
                sample.put("format", "best");
                break;
            case "Low":
                sample.put("format", "worstvideo+worstaudio");
                break;
            default:
                sample.put("format", "bestvideo+bestaudio");
                break;
        }
        return sample;
    }

    @FXML
    private void save(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main.fxml"));
//        Parent root = loader.load();
//
//        Scene zoneScene = new Scene(root, 600, 500);
//        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        primaryStage.setScene(zoneScene);
//        primaryStage.show();
    }

    @FXML
    public void initialize(YTDL ytdl) {
        System.out.println(ytdl);
    }
}
