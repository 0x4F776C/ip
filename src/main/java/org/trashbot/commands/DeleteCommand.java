package org.trashbot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.trashbot.exceptions.DukeException;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.storage.DataPersistence;
import org.trashbot.tasks.Task;

/**
 * Handles the deletion of multiple tasks from the task management system.
 * This command implementation removes tasks at the specified indices
 * from the task list and updates the storage.
 *
 * <p>The command operates based on task IDs (indices) which must be
 * within the valid range of existing tasks (0 to size-1).</p>
 *
 * <p>Example usage:
 * <pre>
 * DeleteCommand cmd = new DeleteCommand(1, 2, 4); // Delete tasks at indices 1, 2, and 4
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 */
public class DeleteCommand implements Command {
    /**
     * The index of tasks to be deleted
     * that starts from 0
     */
    private final int[] taskIds;

    /**
     * Constructs a new DeleteCommand for n tasks
     *
     * @param taskIds n number of tasks to delete
     */
    public DeleteCommand(int... taskIds) {
        this.taskIds = taskIds;
        Arrays.sort(this.taskIds);
    }

    /**
     * Executes the delete command by removing the specified tasks from the task list
     * and updating the storage. The method validates that all task IDs are within
     * the valid range before performing any deletions.
     *
     * <p>After successful deletion, the updated task list is persisted to storage
     * and a confirmation message is displayed to the user.</p>
     *
     * @param tasks   The list of tasks from which tasks will be deleted
     * @param storage The data persistence mechanism used to save the updated task list
     * @return String containing the command's output message
     * @throws InvalidFormatException if any task ID is out of range (less than 0 or
     *                               greater than or equal to the size of the task list)
     * @throws IOException           if there is an error saving the task list to storage
     * @see DataPersistence#save(List)
     */
    @Override
    public String execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException {
        assert tasks != null : "List cannot be null";
        assert storage != null : "Storage cannot be null";
        assert !tasks.isEmpty() : "Cannot delete from empty list";

        if (Arrays.stream(taskIds)
                .anyMatch(id -> id < 0 || id >= tasks.size())) {
            throw new InvalidFormatException("All task numbers must be between 1 and "
                    + tasks.size());
        }

        List<Task> removedTasks = new ArrayList<>();

        Arrays.stream(taskIds)
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(id -> removedTasks.add(tasks.remove((int) id)));

        storage.save(tasks);

        String removedTasksMessage = removedTasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining("\n"));

        return String.format(" Got it. I've removed these tasks:\n  %s\n Now you have %d tasks in the list.",
                removedTasksMessage, tasks.size());
    }
}
