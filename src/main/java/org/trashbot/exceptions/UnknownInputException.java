package org.trashbot.exceptions;

/**
 * Represents a custom exception specific to the TrashBot application.
 * This exception extends the {@link DukeException} class, enabling the creation of
 * exception instances with a custom error message.
 */
public class UnknownInputException extends DukeException {
    /**
     * Constructs a new {@code UnknownInputException} with the specified detail message.
     *
     * @param input the detail message, which can be retrieved later by the {@link #getMessage()} method.
     */
    public UnknownInputException(String input) {
        super("The input "
                + input
                + " is unknown!\n"
                + " Available input: todo, deadline, delete, event, mark, unmark, list, find, bye");
    }
}
