package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.EmptyDescriptionException;
import org.trashbot.tasks.Task;
import org.trashbot.tasks.Todo;

import java.io.IOException;
import java.util.List;

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
    /** The raw input string containing the todo task description */
    private final String input;

    /** The minimum string length for "Todo" filter */
    private static final int MIN_DESCRIPTION_LENGTH = 5;

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
     * @param tasks The list of tasks to which the new todo task will be added
     * @param storage The data persistence mechanism used to save the updated task list
     * @throws EmptyDescriptionException if the input description is empty or too short
     *                                  (4 characters or less after trimming)
     * @throws IOException if there is an error saving the task list to storage
     *
     * @see Todo
     * @see DataPersistence#save(List)
     */
    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws EmptyDescriptionException, IOException {
        if (input.trim().length() < MIN_DESCRIPTION_LENGTH) {
            throw new EmptyDescriptionException("todo");
        }

        Task newTask = new Todo(input);
        tasks.add(newTask);
        storage.save(tasks);
        System.out.println(" Got it. I've added this task:\n "
                + newTask
                + "\n Now you have "
                + tasks.size()
                + " tasks in the list.");
    }
}
