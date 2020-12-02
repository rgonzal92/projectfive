package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main is a Java Class that contains a main and an overwritten start method from the 
 * extended Application Class to run the program when completed.
 * 
 * @author Richard Gonzalez
 * @author Shejan Shuza
 * @author Juan-Carlos Rodriguez
 * @author Collin Behunin
 * @author Jacob De Hoyos
 * UTSA CS 3443 - Team Project: YoutubeDL
 * Fall 2020
 */
public class Main extends Application {

    /**
	 * Creates the Stage based of the Main.fxml file's specifications.
	 * This will serve as the main entry point of the JavaFX application.
	 * 
	 * @param	primaryStage	The primary Stage of the project.
	 * @throws	Exception		Will be thrown if an any Exception is encountered.
	 */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("YouTube Downloader GUI");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();
    }

    /**
     * Launches the application.
     * 
     * @param args	A String array of parameter arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
