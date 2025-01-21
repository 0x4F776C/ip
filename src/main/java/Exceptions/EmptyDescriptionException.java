package Exceptions;

public class EmptyDescriptionException extends DukeException {
    public EmptyDescriptionException(String task) {
        super("The task " + task + " can't be empty!");
    }
}
