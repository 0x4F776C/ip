package org.trashbot.tasks;

/**
 * Base class representing a task with description and completion status.
 */
public class Task {
    protected final String description;
    protected boolean isCompleted;

    /**
     * Creates a new task with the given description.
     *
     * @param description Task description
     */
    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    /**
     * Checks if the task is completed.
     *
     * @return true if task is done, false otherwise
     */
    public boolean isDone() {
        return isCompleted;
    }

    /**
     * Gets the task description.
     *
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the status icon for display purposes.
     *
     * @return "X" if task is done, " " otherwise
     */
    public String getStatusIcon() {
        return (isCompleted ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        isCompleted = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markAsNotDone() {
        isCompleted = false;
    }

    /**
     * Returns string representation of task.
     * Format: [Status] Description
     *
     * @return Formatted task string
     */
    public String toString() {
        return "["
                + getStatusIcon()
                + "] "
                + description;
    }
}
