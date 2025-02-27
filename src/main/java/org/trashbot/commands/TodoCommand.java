package org.trashbot.commands;

import java.io.IOException;
import java.util.List;

import org.trashbot.exceptions.DukeException;
import org.trashbot.exceptions.EmptyDescriptionException;
import org.trashbot.storage.DataPersistence;
import org.trashbot.tasks.Task;
import org.trashbot.tasks.Todo;

/**
 * Handles the creation of todo tasks in the task management system.
 * This command implementation processes user input to create new todo
 * tasks and adds them to the task list.
 *
 * <p>The command requires a non-empty description for the todo task.
 * The input string must be longer than 4 characters (excluding the
 * "todo" command word) to be considered valid.</p>
 *
 * <p>Example usage:
 * <pre>
 * TodoCommand cmd = new TodoCommand("todo read book");
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 * @see Todo
 */
public class TodoCommand implements Command {
    /**
     * The minimum string length for "Todo" filter
     */
    private static final int MIN_DESCRIPTION_LENGTH = 5;

    /**
     * The raw input string containing the todo task description
     */
    private final String input;

    /**
     * Constructs a new TodoCommand with the specified input string.
     *
     * @param input The raw input string containing the todo task description
     *              (should be in the format "todo description")
     */
    public TodoCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the todo command by creating a new todo task and adding it
     * to the task list. The method validates that the description is not empty
     * before creating the task.
     *
     * <p>After successfully creating the task, it is added to the task list,
     * the list is persisted to storage, and a confirmation message is displayed
     * to the user.</p>
     *
     * @param tasks   The list of tasks to which the new todo task will be added
     * @param storage The data persistence mechanism used to save the updated task list
     * @return String containing the command's output message
     * @throws EmptyDescriptionException if the input description is empty or too short
     *                                   (4 characters or less after trimming)
     * @throws IOException               if there is an error saving the task list to storage
     * @see Todo
     * @see DataPersistence#save(List)
     */
    @Override
    public String execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException {
        if (input.trim().length() < MIN_DESCRIPTION_LENGTH) {
            throw new EmptyDescriptionException("todo");
        }

        Task newTask = new Todo(input);
        tasks.add(newTask);
        storage.save(tasks);

        return String.format(" Got it. I've added this task:\n  %s\n Now you have %d tasks in the list.",
                newTask, tasks.size());
    }
}
