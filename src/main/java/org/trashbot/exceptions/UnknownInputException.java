package org.trashbot.exceptions;

public class UnknownInputException extends DukeException {
    public UnknownInputException(String input) {
        super("The input "
                + input
                + " is unknown!\n"
                + " Available input: todo, deadline, event, mark, unmark, list, find, bye");
    }
}
