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
 * The {@code Main} class serves as the entry point for the TrashBot application.
 * It extends {@link javafx.application.Application} and is responsible for initializing the application,
 * loading the user interface from an FXML file, and setting up the primary stage (main window).
 * This class also initializes the {@link TrashBot} instance, which is the core component of the application.
 */
public class Main extends Application {
    /**
     * Starts the JavaFX application by performing the following steps:
     * <ol>
     *     <li>Initializes a new {@link TrashBot} instance with the specified save file path.</li>
     *     <li>Loads the FXML layout for the main window from the resource file.</li>
     *     <li>Sets up the scene with the loaded layout and applies the CSS stylesheet.</li>
     *     <li>Configures the primary stage (window) with a title, icon, minimum dimensions, and scene.</li>
     *     <li>Sets the {@link TrashBot} instance in the controller for the main window.</li>
     *     <li>Displays the primary stage.</li>
     * </ol>
     *
     * @param stage The primary stage (window) for the application, provided by the JavaFX runtime.
     * @throws IOException If an error occurs while loading the FXML file or other resources.
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
            throw new RuntimeException(e);
        }
    }

    /**
     * The main method that launches the JavaFX application.
     * This method is the entry point for the application and is called by the JVM when the program starts.
     * It delegates the execution to the JavaFX runtime by calling {@link Application#launch(String...)}.
     *
     * @param args Command-line arguments passed to the application.
     *     These arguments can be used to configure the application at startup.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
