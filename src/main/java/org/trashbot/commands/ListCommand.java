package org.trashbot.commands;

import java.util.List;

import org.trashbot.core.DataPersistence;
import org.trashbot.tasks.Task;

/**
 * Displays all tasks currently stored in the task management system.
 * This command implementation provides a formatted view of all tasks
 * in the task list, with each task numbered sequentially.
 *
 * <p>The output is formatted with decorative borders and includes:
 * - A header message
 * - Numbered list of all tasks
 * - Empty list notification if no tasks exist
 * </p>
 *
 * <p>Example usage:
 * <pre>
 * ListCommand cmd = new ListCommand();
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * <p>Example output:
 * <pre>
 * ____________________________________________________________
 * Here are the tasks in your list:
 * 1. [T][] read book
 * 2. [D][] submit report /by Monday
 * ____________________________________________________________
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 */
public class ListCommand implements Command {
    /**
     * Executes the list command by displaying all tasks in the task list.
     * The method formats the output with sequential numbering and decorative borders.
     * If the task list is empty, a special message is displayed instead.
     *
     * <p>The storage parameter is not used in this implementation as the command
     * only reads and displays the current state of the task list without modification.</p>
     *
     * @param tasks   The list of tasks to be displayed
     * @param storage The data persistence mechanism (unused in this implementation)
     */
    @Override
    public void execute(List<Task> tasks, DataPersistence storage) {
        if (tasks.isEmpty()) {
            System.out.println("List is empty!");
            return;
        }
        StringBuilder output = new StringBuilder(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            output.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        System.out.println("____________________________________________________________");
        System.out.println(output.toString().trim());
        System.out.println("____________________________________________________________");
    }
}
