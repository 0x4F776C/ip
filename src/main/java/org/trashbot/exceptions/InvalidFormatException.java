package org.trashbot.exceptions;

/**
 * Represents a custom exception specific to the TrashBot application.
 * This exception extends the {@link DukeException} class, enabling the creation of
 * exception instances with a custom error message.
 */
public class InvalidFormatException extends DukeException {
    /**
     * Constructs a new {@code InvalidFormatException} with the specified detail message.
     *
     * @param message the detail message, which can be retrieved later by the {@link #getMessage()} method.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
