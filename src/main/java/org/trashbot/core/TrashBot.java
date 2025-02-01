package org.trashbot.core;

import java.io.IOException;
import java.util.List;

import org.trashbot.commands.ByeCommand;
import org.trashbot.commands.Command;
import org.trashbot.commands.DeadlineCommand;
import org.trashbot.commands.DeleteCommand;
import org.trashbot.commands.EventCommand;
import org.trashbot.commands.FindCommand;
import org.trashbot.commands.ListCommand;
import org.trashbot.commands.MarkCommand;
import org.trashbot.commands.TodoCommand;
import org.trashbot.exceptions.DukeException;
import org.trashbot.exceptions.UnknownInputException;
import org.trashbot.storage.FileStorage;
import org.trashbot.tasks.Task;
import org.trashbot.ui.TrashBotUI;

/**
 * The TrashBot class represents the core of the TrashBot application.
 * It processes user commands, manages tasks, and handles file storage.
 * <p>
 * The TrashBot interacts with several command classes to manipulate and manage a list of tasks,
 * and it also handles loading and saving tasks from/to a file.
 * </p>
 */
public class TrashBot {
    private List<Task> tasks;
    private FileStorage storage;

    /**
     * Constructs a new TrashBot instance and initializes it with a storage file.
     * It loads the tasks from the specified storage file.
     *
     * @param storageFilePath the path to the file where tasks are stored
     * @throws IOException if there is an issue reading the storage file
     */
    public TrashBot(String storageFilePath) throws IOException {
        this.storage = new FileStorage(storageFilePath);
        this.tasks = storage.load();
    }

    /**
     * Processes the user's input command.
     * The input is parsed to determine the appropriate command, which is then executed.
     * <p>
     * If the command is invalid or there is an error, an exception is caught and an error message is printed.
     * </p>
     *
     * @param input the command string from the user
     */
    public void processCommand(String input) {
        try {
            Command command = parseCommand(input);
            command.execute(tasks, storage);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses the user's input command string and returns the corresponding command object.
     * <p>
     * The input string is split into command type and arguments, which are then mapped to
     * the corresponding command classes (e.g., TodoCommand, DeadlineCommand, etc.).
     * </p>
     *
     * @param input the command string to parse
     * @return a Command object corresponding to the input command
     * @throws UnknownInputException if the command type is not recognized
     */
    private Command parseCommand(String input) throws UnknownInputException {
        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();

        switch (commandType) {
        case "todo":
            return new TodoCommand(input);
        case "deadline":
            return new DeadlineCommand(input);
        case "event":
            return new EventCommand(input);
        case "list":
            return new ListCommand();
        case "delete":
            return new DeleteCommand(Integer.parseInt(parts[1]) - 1);
        case "mark":
            return new MarkCommand(Integer.parseInt(parts[1]) - 1, true);
        case "unmark":
            return new MarkCommand(Integer.parseInt(parts[1]) - 1, false);
        case "find":
            return new FindCommand(input);
        case "bye":
            return new ByeCommand();
        default:
            throw new UnknownInputException(commandType);
        }
    }

    /**
     * The main entry point of the TrashBot application.
     * Initializes the TrashBot instance and launches the user interface.
     * <p>
     * If the initialization fails, an error message is displayed and the program exits.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            TrashBot trashBot = new TrashBot("./data/TrashBot.sav");
            TrashBotUI ui = new TrashBotUI(trashBot);
            ui.run();
        } catch (IOException e) {
            System.out.println("Initialization failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
