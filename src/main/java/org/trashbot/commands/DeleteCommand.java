package org.trashbot.commands;

import java.io.IOException;
import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Task;

/**
 * Handles the deletion of tasks from the task management system.
 * This command implementation removes a task at the specified index
 * from the task list and updates the storage.
 *
 * <p>The command operates based on a task ID (index) which must be
 * within the valid range of existing tasks (0 to size-1).</p>
 *
 * <p>Example usage:
 * <pre>
 * DeleteCommand cmd = new DeleteCommand(2); // Delete the third task
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 */
public class DeleteCommand implements Command {
    /**
     * The index of the task to be deleted (0-based)
     */
    private final int taskId;

    /**
     * Constructs a new DeleteCommand for the specified task ID.
     *
     * @param taskId The index of the task to be deleted (0-based index)
     */
    public DeleteCommand(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Executes the delete command by removing the specified task from the task list
     * and updating the storage. The method validates that the task ID is within
     * the valid range before performing the deletion.
     *
     * <p>After successful deletion, the updated task list is persisted to storage
     * and a confirmation message is displayed to the user.</p>
     *
     * @param tasks   The list of tasks from which a task will be deleted
     * @param storage The data persistence mechanism used to save the updated task list
     * @return String containing the command's output message
     * @throws InvalidFormatException if the task ID is out of range (less than 0 or
     *                                greater than or equal to the size of the task list)
     * @throws IOException            if there is an error saving the task list to storage
     * @see DataPersistence#save(List)
     */
    @Override
    public String execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new InvalidFormatException("Task number must be between 1 and " + tasks.size());
        }

        Task removeTask = tasks.remove(taskId);
        storage.save(tasks);

        return String.format(" Got it. I've removed this task: \n %s\n Now you have %d tasks in the list.",
                removeTask, tasks.size());
    }
}
