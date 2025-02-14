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
    /**
     * Starts the JavaFX application by initializing TrashBot, loading the FXML layout,
     * and displaying the main window.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            TrashBot trashBot = new TrashBot("./data/TrashBot.sav");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            if (fxmlLoader.getLocation() == null) {
                System.out.println("Error: Cannot find MainWindow.fxml");
                return;
            }

            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            String cssPath = "/view/style.css";
            String css = getClass().getResource(cssPath).toExternalForm();
            scene.getStylesheets().add(css);

            try {
                stage.getIcons().add(new Image("/images/eyecon.png"));
            } catch (Exception e) {
                System.out.println("Error loading icon: " + e.getMessage());
            }

            stage.setTitle("TrashBot");
            stage.setScene(scene);
            stage.setMinWidth(400);
            stage.setMinHeight(300);


            MainWindow controller = fxmlLoader.getController();
            if (controller == null) {
                System.out.println("Error: Controller is null");
                return;
            }
            controller.setTrashBot(trashBot);


            stage.show();
        } catch (IOException e) {
            System.out.println("Error starting application: " + e.getMessage());
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
