package org.trashbot.commands;

import org.trashbot.core.DataPersistence;
import org.trashbot.tasks.Task;

import java.util.List;

public class ListCommand implements Command {
    @Override
    public void execute(List<Task> tasks, DataPersistence storage) {
        if (tasks.isEmpty()) {
            System.out.println("List is empty!");
            return;
        }
        StringBuilder output = new StringBuilder(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            output.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        System.out.println("____________________________________________________________");
        System.out.println(output.toString().trim());
        System.out.println("____________________________________________________________");
    }
}