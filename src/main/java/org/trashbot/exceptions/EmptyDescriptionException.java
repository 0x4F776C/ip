package org.trashbot.exceptions;

/**
 * Represents a custom exception specific to the TrashBot application.
 * This exception extends the {@link DukeException} class, enabling the creation of
 * exception instances with a custom error message.
 */
public class EmptyDescriptionException extends DukeException {
    /**
     * Constructs a new {@code EmptyDescriptionException} with the specified detail message.
     *
     * @param task the detail message, which can be retrieved later by the {@link #getMessage()} method.
     */
    public EmptyDescriptionException(String task) {
        super("The task " + task + " can't be empty!");
    }
}
