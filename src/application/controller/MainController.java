package application.controller;

import application.model.Config;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import application.model.*;
import com.sapher.youtubedl.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import java.io.IOException;

public class MainController {
    private final YTDL youtube_dl = YTDL.getInstance();

    @FXML
    TextArea InputBox;
    public MainController(){
        InputBox=new TextArea();
    }
    @FXML
    private void download(ActionEvent actionEvent) throws YoutubeDLException {
        /*String videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        youtube_dl.setExecPath();
        youtube_dl.getRequest(videoUrl);*/
        Scanner LineScanner=new Scanner(InputBox.getText());
        while(LineScanner.hasNextLine()){
            String videoUrl =LineScanner.nextLine();
            System.out.println(videoUrl);
            youtube_dl.setExecPath();
            youtube_dl.getRequest(videoUrl);
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
