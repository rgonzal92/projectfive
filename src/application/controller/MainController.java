package application.controller;

import application.model.Config;
import application.model.YTDL;
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

/**
 * Handles interactions with the main.fxml file.
 * Variables: youtube_dl(YTDL), config(Config), urlTextArea(TextArea), outputTextArea(TextArea).
 * 
 * @author Richard Gonzalez
 * @author Shejan Shuza
 * @author Juan-Carlos Rodriguez
 * @author Collin Behunin
 * @author Jacob De Hoyos
 */
public class MainController implements Initializable {
    private final YTDL youtube_dl = YTDL.getInstance();
    private final Config config = Config.getInstance();

    @FXML
    private TextArea urlTextArea;
    @FXML
    private TextArea outputTextArea;

    /**
     * Initiates a download when the Download button is pressed.
     */
    @FXML
    private void download() {
        if (urlTextArea.getText().isEmpty())
            outputTextArea.setText("Must provide at least one link.");
        else
            youtube_dl.OutputText = outputTextArea;
            youtube_dl.getRequest(urlTextArea.getText().split(("\n")), config.getSaveDirectory());
    }

    /**
	 * Switches to the credits.fxml file when the Home button is pressed.
	 * 
     * @param actionEvent	an ActionEvent object (button press).
     * @throws IOException	if the file cannot be found.
	 */
    @FXML
    private void switchToCreditsScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String scene = "../view/credits.fxml";
        loadScene(actionEvent, loader, scene);
    }

    /**
	 * Switches to the advanced.fxml file when the Home button is pressed.
	 * 
     * @param actionEvent	an ActionEvent object (button press).
     * @throws IOException	if the file cannot be found.
	 */
    @FXML
    private void switchToAdvancedScene(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String scene = "../view/advanced.fxml";
        loadScene(actionEvent, loader, scene);
    }
    
    /**
     * Loads the specified fxml file onto the stage.
     * 
     * @param actionEvent	an ActionEvent object (button press).	
     * @param loader		an FXMLLoader object.		
     * @param scene			a String representing the fxml file to be loaded.
     * @throws IOException	if the file could not be found.
     */
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
     * @param location
     * The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or <tt>null</tt> if
     * the root object was not localized.
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
