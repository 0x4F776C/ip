package org.trashbot.ui;

import java.io.IOException;

import org.trashbot.core.TrashBot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the TrashBot application.
 * It initializes the TrashBot instance, loads the user interface, and sets up the primary stage.
 */
public class Main extends Application {
    private TrashBot trashBot;

    /**
     * Starts the JavaFX application by initializing TrashBot, loading the FXML layout,
     * and displaying the main window.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            // Initialize TrashBot
            trashBot = new TrashBot("./data/TrashBot.sav");

            // Load FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Setup stage
            stage.getIcons().add(new Image("file:icon.png"));
            stage.setTitle("TrashBot Task Manager");
            stage.setScene(scene);
            stage.setMinWidth(400);
            stage.setMinHeight(300);

            // Inject TrashBot instance into controller
            MainWindow controller = fxmlLoader.getController();
            controller.setTrashBot(trashBot);

            // Show the window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
