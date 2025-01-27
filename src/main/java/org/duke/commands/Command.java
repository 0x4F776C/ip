package org.trashbot.commands;

import org.trashbot.tasks.Task;
import org.trashbot.core.DataPersistence;
import org.trashbot.exceptions.DukeException;

import java.util.List;
import java.io.IOException;

public interface Command {
    void execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException;
}
