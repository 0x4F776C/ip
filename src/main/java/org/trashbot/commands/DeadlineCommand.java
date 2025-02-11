package org.trashbot.commands;

import java.io.IOException;
import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Deadline;
import org.trashbot.tasks.Task;

/**
 * Handles the creation and management of deadline-based tasks in the task management system.
 * This command implementation processes user input to create new deadline tasks with specific
 * due dates and adds them to the task list.
 *
 * <p>The command expects input in the format: "deadline &lt;task&gt; /by &lt;due&gt;"
 * where &lt;task&gt; is the task description and &lt;due&gt; is the deadline date/time.</p>
 *
 * <p>Example usage:
 * <pre>
 * DeadlineCommand cmd = new DeadlineCommand("submit report /by next Monday");
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 * @see Deadline
 */
public class DeadlineCommand implements Command {
    private static final String STRING_BY = "/by";
    private final String input;

    /**
     * Constructs a new DeadlineCommand with the specified input string.
     *
     * @param input The raw input string containing both the task description and deadline
     *              information in the format "task /by deadline"
     */
    public DeadlineCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the deadline command by creating a new deadline task and adding it to the task list.
     * The method also persists the updated task list to storage.
     *
     * <p>The method parses the input string to create a new {@link Deadline} task. The input
     * must contain the "/by" delimiter to separate the task description from the deadline.</p>
     *
     * @param tasks   The list of tasks to which the new deadline task will be added
     * @param storage The data persistence mechanism used to save the updated task list
     * @return String containing the command's output message
     * @throws InvalidFormatException if the input string does not contain the "/by" delimiter
     *                                or is not in the correct format
     * @throws IOException            if there is an error saving the task list to storage
     * @see Deadline
     * @see DataPersistence#save(List)
     */
    @Override
    public String execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (!input.contains(STRING_BY)) {
        assert tasks != null : "List cannot be null";
        assert storage != null : "Storage cannot be null";

        if (!input.contains("/by")) {
            throw new InvalidFormatException("Please use the format: deadline <task> /by <due>");
        }
        Task newTask = new Deadline(input);
        tasks.add(newTask);
        storage.save(tasks);

        return String.format(" Got it. I've added this task:\n  %s\n Now you have %d tasks in the list.",
                newTask, tasks.size());
    }
}
