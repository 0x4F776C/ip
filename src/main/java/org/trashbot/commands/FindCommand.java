package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.tasks.Task;

import java.util.List;

/**
 * Handles searching for tasks containing specific keywords in the task management system.
 * This command implementation filters tasks based on whether their descriptions contain
 * the specified search term (case-insensitive).
 *
 * <p>The command expects input in the format: "find <keyword>"
 * where <keyword> is the search term to match against task descriptions.</p>
 *
 * <p>Example usage:
 * <pre>
 * FindCommand cmd = new FindCommand("cry");
 * cmd.execute(taskList, storage);
 * </pre>
 * </p>
 *
 * @see Command
 * @see Task
 */
public class FindCommand implements Command {
    private String term;

    /**
     * Constructs a new FindCommand with the specified search term.
     *
     * @param term The raw input string containing both the command and search term
     *             in the format "find searchTerm"
     */
    public FindCommand(String term) {
        this.term = term.substring(5).trim().toLowerCase();
    }

    /**
     * Executes the find command by searching through all tasks and displaying those
     * whose descriptions contain the search term (case-insensitive match).
     *
     * <p>The method filters the task list and displays matching tasks with their
     * indices. If no matching tasks are found, an appropriate message is shown.</p>
     *
     * @param tasks   The list of tasks to search through
     * @param storage The data persistence mechanism (unused in this implementation)
     */
    @Override
    public void execute(List<Task> tasks, DataPersistence storage) {
        /** Replaced for-loop with Stream for readability */
        List<Task> matchingTasks = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(term))
                .toList();

        if (matchingTasks.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" No matching tasks found!");
            System.out.println("____________________________________________________________");
        }

        StringBuilder output = new StringBuilder(" Here are the matching tasks in the list:\n");
        for (Task task : matchingTasks) {
            output.append(" ").append(tasks.indexOf(task) + 1).append(".").append(task).append("\n");
        }

        System.out.println("____________________________________________________________");
        System.out.println(output.toString().trim());
        System.out.println("____________________________________________________________");
    }
}
