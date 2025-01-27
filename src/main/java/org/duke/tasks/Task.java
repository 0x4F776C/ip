package org.duke.tasks;

/**
 * Base class representing a task with description and completion status.
 */
public class Task {
    protected String description;
    protected boolean completed;

    /**
     * Creates a new task with the given description.
     * @param description Task description
     */
    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    /**
     * Checks if the task is completed.
     * @return true if task is done, false otherwise
     */
    public boolean isDone() {
        return completed;
    }

    /**
     * Gets the task description.
     * @return Task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the status icon for display purposes.
     * @return "X" if task is done, " " otherwise
     */
    public String getStatusIcon() {
        return (completed ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        completed = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markAsNotDone() {
        completed = false;
    }

    /**
     * Returns string representation of task.
     * Format: [Status] Description
     * @return Formatted task string
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}