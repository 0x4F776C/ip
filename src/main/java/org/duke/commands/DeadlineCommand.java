package org.duke.commands;

import org.duke.core.DataPersistence;
import org.duke.exceptions.InvalidFormatException;
import org.duke.tasks.Deadline;
import org.duke.tasks.Task;

import java.io.IOException;
import java.util.List;

public class DeadlineCommand implements Command {
    private String input;

    public DeadlineCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (!input.contains("/by")) {
            throw new InvalidFormatException("Please use the format: deadline <task> /by <due>");
        }
        Task newTask = new Deadline(input);
        tasks.add(newTask);
        storage.save(tasks);
        System.out.println(" Got it. I've added this task:\n  " + newTask +
                "\n Now you have " + tasks.size() + " tasks in the list.");
    }
}