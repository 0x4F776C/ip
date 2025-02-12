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

/**
 * The TrashBot class represents the core of the TrashBot application.
 * It processes user commands, manages tasks, and handles file storage.
 * <p>
 * This class serves as the main controller for the TrashBot application, providing:
 * <ul>
 *   <li>Command processing and execution</li>
 *   <li>Task management and persistence</li>
 *   <li>Response handling and formatting</li>
 * </ul>
 * </p>
 *
 */
public class TrashBot {
    private static final String DEFAULT_RESPONSE = "I've processed your command.";
    private final List<Task> tasks;
    private final FileStorage storage;
    private final StringBuilder currentResponse;

    /**
     * Constructs a new TrashBot instance and initializes it with a storage file.
     * It loads the tasks from the specified storage file into memory.
     *
     * @param storageFilePath the path to the file where tasks are stored
     * @throws IOException if there is an issue reading from or writing to the storage file
     */
    public TrashBot(String storageFilePath) throws IOException {
        this.storage = new FileStorage(storageFilePath);
        this.tasks = storage.load();
        this.currentResponse = new StringBuilder();
    }

    /**
     * Retrieves and clears the current response buffer.
     * If no response has been set, returns a default response message.
     *
     * @return the current response string, or the default response if empty
     */
    public String getResponse() {
        String response = currentResponse.toString();
        currentResponse.setLength(0);
        return response.isEmpty() ? DEFAULT_RESPONSE : response;
    }

    /**
     * Processes the user's input command and generates an appropriate response.
     * The method handles command parsing, execution, and error handling.
     * <p>
     * If an error occurs during processing, it will be caught and converted into
     * an appropriate error message in the response.
     * </p>
     *
     * @param input the command string from the user
     */
    public void processCommand(String input) {
        assert input != null : "Input cannot be null";
        assert !input.trim().isEmpty() : "Input cannot be empty";

        try {
            executeCommand(input);
        } catch (DukeException | IOException e) {
            handleCommandError(e);
        }
    }

    /**
     * Executes the parsed command and stores the output in the response buffer.
     *
     * @param input the raw command string to execute
     * @throws DukeException if there is an error in command execution
     * @throws IOException if there is an error in file operations
     */
    private void executeCommand(String input) throws DukeException, IOException {
        Command command = parseCommand(input);
        String output = command.execute(tasks, storage);
        currentResponse.append(output);
    }

    /**
     * Handles exceptions that occur during command execution by formatting
     * them into appropriate error messages.
     *
     * @param e the exception that occurred during command execution
     */
    private void handleCommandError(Exception e) {
        String errorPrefix = e instanceof IOException ? "Error: " : "";
        currentResponse.append(errorPrefix).append(e.getMessage());
    }

    /**
     * Parses the user's input command string into a Command object.
     * <p>
     * This method performs the following steps:
     * <ol>
     *   <li>Validates the input is not null or empty</li>
     *   <li>Determines the command type</li>
     *   <li>Creates and returns the appropriate Command object</li>
     * </ol>
     * </p>
     *
     * @param input the command string to parse
     * @return a Command object corresponding to the input command
     * @throws UnknownInputException if the command type is not recognized or input is invalid
     */
    private Command parseCommand(String input) throws UnknownInputException {
        checkInput(input);
        CommandType type = getCommandType(input);

        return generateCommand(type, input);
    }

    /**
     * Extracts and validates the command type from the input string.
     *
     * @param input the raw command string
     * @return the CommandType enum value corresponding to the command
     * @throws UnknownInputException if the command type is not recognized
     */
    private CommandType getCommandType(String input) throws UnknownInputException {
        String command = input.split(" ", 2)[0].toLowerCase();

        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownInputException(command);
        }
    }

    /**
     * Validates that the input string is not null or empty.
     *
     * @param input the command string to validate
     * @throws UnknownInputException if the input is null or empty
     */
    private void checkInput(String input) throws UnknownInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new UnknownInputException("Command cannot be empty");
        }
    }

    /**
     * Creates the appropriate Command object based on the command type and input.
     * <p>
     * This method handles the creation of specific command objects for each supported
     * command type, including parsing any necessary parameters from the input string.
     * </p>
     *
     * @param type the type of command to create
     * @param input the raw command string containing any parameters
     * @return the created Command object
     * @throws UnknownInputException if the command parameters are invalid
     */
    private Command generateCommand(CommandType type, String input) throws UnknownInputException {
        return switch (type) {
        case TODO -> new TodoCommand(input);
        case DEADLINE -> new DeadlineCommand(input);
        case EVENT -> new EventCommand(input);
        case LIST -> new ListCommand();
        case DELETE -> generateDeleteCommand(input);
        case MARK -> generateMarkCommand(input, true);
        case UNMARK -> generateMarkCommand(input, false);
        case FIND -> new FindCommand(input);
        case BYE -> new ByeCommand();
        };
    }

    /**
     * Creates a DeleteCommand object from the input string.
     * <p>
     * Extracts and validates the task ID from the input string, adjusting it
     * to be zero-based for internal use.
     * </p>
     *
     * @param input the command string containing the task ID to delete
     * @return a DeleteCommand object configured with the specified task ID
     * @throws UnknownInputException if the task ID is invalid or missing
     */
    private DeleteCommand generateDeleteCommand(String input) throws UnknownInputException {
        try {
            int taskId = getTaskId(input);

            return new DeleteCommand(taskId - 1);
        } catch (NumberFormatException e) {
            throw new UnknownInputException("Task number does not exist");
        }
    }

    /**
     * Creates a MarkCommand object from the input string.
     * <p>
     * Extracts and validates the task ID from the input string, adjusting it
     * to be zero-based for internal use. The isDone parameter determines whether
     * the task should be marked as complete or incomplete.
     * </p>
     *
     * @param input the command string containing the task ID to mark
     * @param isDone true to mark the task as done, false to mark it as not done
     * @return a MarkCommand object configured with the specified task ID and status
     * @throws UnknownInputException if the task ID is invalid or missing
     */
    private MarkCommand generateMarkCommand(String input, boolean isDone) throws UnknownInputException {
        try {
            int Id = getTaskId(input);

            return new MarkCommand(Id - 1, isDone);
        } catch (NumberFormatException e) {
            throw new UnknownInputException("Task number does not exist");
        }
    }

    /**
     * Extracts the task ID from the input string.
     * <p>
     * Splits the input string and parses the second part as an integer task ID.
     * </p>
     *
     * @param input the command string containing the task ID
     * @return the parsed task ID as an integer
     * @throws NumberFormatException if the task ID is not a valid integer
     */
    private int getTaskId(String input) {
        String[] split = input.split(" ", 2);

        return Integer.parseInt(split[1]);
    }
}
