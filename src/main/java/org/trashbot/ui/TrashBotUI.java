package org.trashbot.ui;

import java.util.Scanner;

import org.trashbot.core.TrashBot;

/**
 * Provides the user interface for the TrashBot task management system.
 * This class handles user input/output and manages the interaction between
 * the user and the core TrashBot functionality.
 *
 * <p>The UI implements a simple command-line interface that:
 * <ul>
 *   <li>Displays a welcome message on startup</li>
 *   <li>Continuously reads user input</li>
 *   <li>Passes commands to the TrashBot core for processing</li>
 *   <li>Handles any errors that occur during operation</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:
 * <pre>
 * TrashBot bot = new TrashBot("data.txt");
 * TrashBotUI ui = new TrashBotUI(bot);
 * ui.run();
 * </pre>
 * </p>
 */
public class TrashBotUI {
    private TrashBot trashBot;

    /**
     * Constructs a new UI instance for the specified TrashBot.
     *
     * @param trashBot The TrashBot instance to be controlled through this UI
     */
    public TrashBotUI(TrashBot trashBot) {
        this.trashBot = trashBot;
    }

    /**
     * Starts the UI and begins processing user input.
     * This method runs continuously until the program is terminated,
     * reading commands from standard input and passing them to TrashBot
     * for processing.
     *
     * <p>The method handles empty input by ignoring it and continues
     * to the next command. Any exceptions that occur during processing
     * are caught and result in program termination with an error message.</p>
     */
    public void run() {
        try {
            drawBorder(" Hello! I'm TrashBot\n What can I do you for?");
            Scanner scanInput = new Scanner(System.in);

            while (true) {
                String userInput = scanInput.nextLine();
                if (userInput.isEmpty()) {
                    continue;
                }
                trashBot.processCommand(userInput);
            }
        } catch (Exception e) {
            System.out.println("Can't access file: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Draws a decorative border around the specified content.
     * Used to improve the visual presentation of messages in the UI.
     *
     * @param content The text to be surrounded with borders
     */
    private void drawBorder(String content) {
        System.out.println("____________________________________________________________");
        System.out.println(content);
        System.out.println("____________________________________________________________");
    }
}
