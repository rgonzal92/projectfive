package application.controller;

import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private void download() throws YoutubeDLException {
        String videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
        String directory = System.getProperty("user.home" + File.separator + "Downloads");

        YoutubeDL.setExecutablePath("lib" + File.separator + "youtube-dl.exe");

        YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);
        request.setOption("format", "bestvideo");
        request.setOption("output", "%(title)s.%(ext)s");

        YoutubeDLResponse response = YoutubeDL.execute(request);

        String stdOut = response.getOut();
        System.out.println(stdOut);
    }

    @FXML
    private void switchToCreditsScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/credits.fxml"));
        Parent root = loader.load();

        // AdvancedController advancedController = loader.getController();

        Scene zoneScene = new Scene(root, 600, 500);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(zoneScene);
        primaryStage.show();
    }

    @FXML
    private void switchToAdvancedScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/advanced.fxml"));
        Parent root = loader.load();

        // AdvancedController advancedController = loader.getController();

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
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
