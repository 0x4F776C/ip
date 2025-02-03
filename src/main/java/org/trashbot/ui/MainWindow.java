package org.trashbot.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.trashbot.core.TrashBot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * The MainWindow class controls the user interface of the TrashBot application.
 * It handles user input, displays output, and interacts with the TrashBot logic.
 */
public class MainWindow {

    @FXML
    private TextArea outputArea;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private TrashBot trashBot;
    private ByteArrayOutputStream outputStream;

    /**
     * Initializes the MainWindow by setting up an output stream to capture TrashBot output.
     */
    @FXML
    public void initialize() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Sets the TrashBot instance and displays a welcome message.
     *
     * @param tb The TrashBot instance to be used.
     */
    public void setTrashBot(TrashBot tb) {
        trashBot = tb;
        displayOutput(" Hello! I'm TrashBot\n What can I do you for?\n");
    }

    /**
     * Handles user input when the send button is clicked or Enter is pressed.
     * Captures and processes the command, then displays the response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (!input.isEmpty()) {
            displayOutput("\n> " + input);
            outputStream.reset();
            trashBot.processCommand(input);
            displayOutput(outputStream.toString());
            userInput.clear();
        }
    }

    /**
     * Appends output to the TextArea and scrolls to the bottom.
     *
     * @param output The text to be displayed in the output area.
     */
    private void displayOutput(String output) {
        outputArea.appendText(output);
        outputArea.setScrollTop(Double.MAX_VALUE);
    }
}
