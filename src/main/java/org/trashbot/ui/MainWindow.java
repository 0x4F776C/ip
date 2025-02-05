package org.trashbot.ui;

import org.trashbot.core.TrashBot;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * The MainWindow class controls the user interface of the TrashBot application.
 * It handles user input, displays output, and interacts with the TrashBot logic.
 */
public class MainWindow {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    private TrashBot trashBot;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/man.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/bot.png"));

    /**
     * Initializes the MainWindow by setting up an output stream to capture TrashBot output.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the TrashBot instance and displays a welcome message.
     *
     * @param tb The TrashBot instance to be used.
     */
    public void setTrashBot(TrashBot tb) {
        trashBot = tb;
        addBotMessage("Hello! I'm TrashBot\nWhat can I do for you?");
    }

    /**
     * Handles user input when the send button is clicked or Enter is pressed.
     * Captures and processes the command, then displays the response.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }
        addUserMessage(input);

        try {
            trashBot.processCommand(input);

            String response = trashBot.getResponse(input);
            if (response != null && !response.trim().isEmpty()) {
                if (response.equals("END_PROGRAM")) {
                    System.exit(0);
                } else {
                    addBotMessage(response);
                }
            }
        } catch (Exception e) {
            addBotMessage("Error: " + e.getMessage());
        }

        userInput.clear();
    }

    private void addUserMessage(String message) {
        DialogBox dialogBox = DialogBox.getUserDialog(message, userImage);
        dialogContainer.getChildren().add(dialogBox);
    }

    private void addBotMessage(String message) {
        DialogBox dialogBox = DialogBox.getDukeDialog(message, dukeImage);
        dialogContainer.getChildren().add(dialogBox);
    }
}
