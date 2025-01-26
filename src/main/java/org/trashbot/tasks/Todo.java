package org.trashbot.tasks;

public class Todo extends Task {
    public Todo(String input) {
        super(input.substring(5)); // get rid of "todo "
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
