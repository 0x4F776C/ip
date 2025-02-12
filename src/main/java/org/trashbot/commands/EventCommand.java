package org.trashbot.commands;

import java.io.IOException;
import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Event;
import org.trashbot.tasks.Task;

/**
 * Handles the creation of event tasks.
 * This command implementation processes user input to create new event tasks with specific
 * starting and ending date/time, and add them to the task list.
 *
 * <p>The command expects input in the format: "event &lt;task&gt; /from &lt;start&gt; /to &lt;end&gt;"
 * where &lt;task&gt; is the task description, &lt;start&gt; is the start date/time, and &lt;end&gt;
 * is the end date/time</p>
 *
 * <p>Example usage:
 * <pre>
 * EventCommand cmd = new EventCommand("cook dinner /from 1600 /to 1800");
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 * @see Event
 */
public class EventCommand implements Command {
    private static final String STRING_FROM = "/from";
    private static final String STRING_TO = "/to";
    private final String input;

    /**
     * Constructs a new EventCommand with the specified input string.
     *
     * @param input The raw input string containing both the task description and event
     *              information in the format "task /from date/time /to date/time"
     */
    public EventCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the event command by creating a new event task and adding it to the task list.
     * The method also persists the updated task list to storage.
     *
     * <p>The method parses the input string to create a new {@link Event} task. The input
     * must contain the "/from" and "/to" delimiter to separate the task description from the event.</p>
     *
     * @param tasks   The list of tasks to which the new event task will be added
     * @param storage The data persistence mechanism used to save the updated task list
     * @return String containing the command's output message
     * @throws InvalidFormatException if the input string does not contain the "/from" and "/to" delimiter
     *                                or is not in the correct format
     * @throws IOException            if there is an error saving the task list to storage
     * @see Event
     * @see DataPersistence#save(List)
     */
    @Override
    public String execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (!input.contains(STRING_FROM) || !input.contains(STRING_TO)) {
            throw new InvalidFormatException("Please use the format: event /from <start> /to <end>");
        }
        Task newTask = new Event(input);
        tasks.add(newTask);
        storage.save(tasks);

        return String.format(" Got it. I've added this task:\n  %s\n Now you have %d tasks in the list.",
                newTask, tasks.size());
    }
}
