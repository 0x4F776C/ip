package org.duke.commands;

import org.duke.tasks.Task;
import org.duke.core.DataPersistence;
import org.duke.exceptions.DukeException;

import java.util.List;
import java.io.IOException;

public interface Command {
    void execute(List<Task> tasks, DataPersistence storage) throws DukeException, IOException;
}
