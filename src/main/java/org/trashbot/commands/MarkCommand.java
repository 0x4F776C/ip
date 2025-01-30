package org.trashbot.commands;

import java.io.IOException;
import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Task;

/**
 * Handles marking tasks as done or not done in the task management system.
 * This command implementation allows toggling the completion status of a
 * specific task identified by its index in the task list.
 *
 * <p>The command operates on a task number (index) which must be within
 * the valid range of existing tasks (0 to size-1) and can set the task
 * as either completed or not completed.</p>
 *
 * <p>Example usage:
 * <pre>
 * // Mark task #3 as done
 * MarkCommand markDone = new MarkCommand(2, true);
 * markDone.execute(taskList, storage);
 *
 * // Mark task #3 as not done
 * MarkCommand markNotDone = new MarkCommand(2, false);
 * markNotDone.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 */
public class MarkCommand implements Command {
    /**
     * The index of the task to be marked (0-based)
     */
    private int taskNum;

    /**
     * Flag indicating whether to mark the task as done (true) or not done (false)
     */
    private boolean markAsDone;

    /**
     * Constructs a new MarkCommand for the specified task number and completion status.
     *
     * @param taskNum    The index of the task to be marked (0-based index)
     * @param markAsDone True to mark the task as done, false to mark as not done
     */
    public MarkCommand(int taskNum, boolean markAsDone) {
        this.taskNum = taskNum;
        this.markAsDone = markAsDone;
    }

    /**
     * Executes the mark command by updating the completion status of the specified task
     * and saving the changes to storage. The method validates that the task number is
     * within the valid range before performing the operation.
     *
     * <p>After successfully marking the task, the updated task list is persisted to storage
     * and a confirmation message is displayed to the user indicating the new status.</p>
     *
     * @param tasks   The list of tasks containing the task to be marked
     * @param storage The data persistence mechanism used to save the updated task list
     * @throws InvalidFormatException if the task number is out of range (less than 0 or
     *                                greater than or equal to the size of the task list)
     * @throws IOException            if there is an error saving the task list to storage
     * @see Task#markAsDone()
     * @see Task#markAsNotDone()
     * @see DataPersistence#save(List)
     */
    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidFormatException("Task number must be between 1 and " + tasks.size());
        }

        Task task = tasks.get(taskNum);

        if (markAsDone) {
            task.markAsDone();
            System.out.println(" Nice! I've marked this task as done:\n  " + task);
        } else {
            task.markAsNotDone();
            System.out.println(" Okay, I've marked this task as not done:\n  " + task);
        }

        storage.save(tasks);
    }
}
