package org.trashbot.tasks;

/**
 * Represents a basic todo task in the task management system.
 * This class extends {@link Task} to implement the simplest form of task
 * that contains only a description without any additional time constraints.
 *
 * <p>Todo tasks are created from input strings in the format "todo &lt;description&gt;".
 * The class strips the "todo " prefix from the input to extract the task description.</p>
 *
 * <p>Example usage:
 * <pre>
 * Todo task = new Todo("todo Read chapter 5");
 * System.out.println(task); // Outputs: [T][✗] Read chapter 5
 * </pre>
 * </p>
 *
 * @see Task
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task by parsing the input string to extract the description.
     *
     * @param input The raw input string in the format "todo &lt;description&gt;"
     */
    public Todo(String input) {
        super(input.substring(5));
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
