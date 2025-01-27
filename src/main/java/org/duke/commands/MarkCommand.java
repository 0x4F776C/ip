package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.InvalidFormatException;
import org.trashbot.tasks.Task;

import java.io.IOException;
import java.util.List;

public class MarkCommand implements Command {
    private int taskNum;
    private boolean markAsDone;

    public MarkCommand(int taskNum, boolean markAsDone) {
        this.taskNum = taskNum;
        this.markAsDone = markAsDone;
    }

    @Override
    public void execute(List<Task> tasks, DataPersistence storage) throws InvalidFormatException, IOException {
        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidFormatException("Task number must be between 1 and " + tasks.size());
        }

        Task task = tasks.get(taskNum);

        if (markAsDone) {
            task.markAsDone();
            System.out.println(" Nice! I've marked this task as done:\n  " + task);
        } else {
            task.markAsNotDone();
            System.out.println(" Okay, I've marked this task as not done:\n  " + task);
        }

        storage.save(tasks);
    }
}