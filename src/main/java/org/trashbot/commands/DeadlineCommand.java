package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Deadline;
import org.trashbot.tasks.Task;

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
        System.out.println(" Got it. I've added this task:\n  "
                + newTask
                + "\n Now you have "
                + tasks.size()
                + " tasks in the list.");
    }
}
