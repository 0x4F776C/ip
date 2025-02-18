package org.trashbot.ui;

import javafx.application.Application;

/**
 * The {@code Launcher} class serves as the entry point for the application.
 * It is responsible for starting the JavaFX application by invoking the
 * {@link Application#launch(Class, String...)} method.
 * This class contains the {@code main} method, which is the starting point of the application.
 */
public class Launcher {

    /**
     * The main method that launches the JavaFX application.
     * This method is the entry point for the application and
     * is called by the JVM when the program starts.
     * It delegates the execution to the JavaFX runtime by calling
     * {@link Application#launch(Class, String...)} with the
     * {@link Main} class and the provided command-line arguments.
     *
     * @param args Command-line arguments passed to the application.
     *     These arguments can be used to configure the application at startup.
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
