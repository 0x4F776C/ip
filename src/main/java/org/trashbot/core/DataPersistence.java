package org.trashbot.core;

import org.trashbot.tasks.Task;

import java.util.List;
import java.io.IOException;

public interface DataPersistence {
    void save(List<Task> tasks) throws IOException;
    List<Task> load() throws IOException;
}
