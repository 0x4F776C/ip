package org.trashbot.exceptions;

/**
 * Represents a custom exception specific to the TrashBot application.
 * This exception extends the {@link Exception} class, enabling the creation of
 * exception instances with a custom error message.
 */
public class DukeException extends Exception {
    /**
     * Constructs a new {@code DukeException} with the specified detail message.
     *
     * @param message the detail message, which can be retrieved later by the {@link #getMessage()} method.
     */
    public DukeException(String message) {
        super(message);
    }
}
