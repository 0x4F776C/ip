package org.duke.commands;

import org.duke.tasks.Task;
import org.duke.tasks.Todo;
import org.duke.core.DataPersistence;
import org.duke.exceptions.EmptyDescriptionException;

import java.io.IOException;
import java.util.List;

public class TodoCommand implements Command {
    private String input;

    public TodoCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws EmptyDescriptionException, IOException {
        if (input.trim().length() <= 4) {
            throw new EmptyDescriptionException("todo");
        }

        Task newTask = new Todo(input);
        tasks.add(newTask);
        storage.save(tasks);
        System.out.println(" Got it. I've added this task:\n " + newTask + "\n Now you have " + tasks.size() + " tasks in the list.");
    }
}
