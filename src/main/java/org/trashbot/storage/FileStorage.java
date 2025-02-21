package org.trashbot.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.trashbot.exceptions.DukeException;
import org.trashbot.tasks.Deadline;
import org.trashbot.tasks.Event;
import org.trashbot.tasks.Task;
import org.trashbot.tasks.Todo;

/**
 * Handles file operations for saving and loading tasks to/from disk.
 */
public class FileStorage implements DataPersistence {
    private final String filePath;
    private final Path directory;

    /**
     * Creates a TrashBotFile instance for file operations.
     *
     * @param filePath path to the file for storing tasks
     */
    public FileStorage(String filePath) {
        this.filePath = filePath;
        this.directory = Paths.get(filePath).getParent();
    }

    /**
     * Saves a list of tasks to the specified file.
     * Creates directories if they don't exist.
     *
     * @param tasks list of tasks to save
     * @throws IOException if an I/O error occurs during file operations
     */
    public void save(List<Task> tasks) throws IOException, DukeException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        try (FileWriter fw = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Task task : tasks) {
                bw.write(convertTaskToString(task));
                bw.newLine();
            }
        }
    }

    /**
     * Loads tasks from the specified file.
     * Creates file and directories if they don't exist.
     *
     * @return list of tasks read from the file
     * @throws IOException if an I/O error occurs during file operations
     */
    public List<Task> load() throws IOException {
        List<Task> tasks = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Files.createFile(path);
            return tasks;
        }

        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                Task task = convertStringToTask(line.trim());
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Converts a task object to its string representation for storage.
     * Format: Type | Done Status | Description [| Additional Fields]
     *
     * @param task task to convert
     * @return string representation of the task
     */
    protected String convertTaskToString(Task task) throws DukeException {
        StringBuilder sb = new StringBuilder();

        if (task instanceof Todo) {
            sb.append("T");
        } else if (task instanceof Deadline) {
            sb.append("D");
        } else if (task instanceof Event) {
            sb.append("E");
        }

        sb.append(" | ").append(task.isDone()
                ? "1"
                : "0")
                .append(" | ")
                .append(task.getDescription());

        if (task instanceof Deadline) {
            sb.append(" | ")
                    .append(((Deadline) task)
                            .getDateTime());
        } else if (task instanceof Event) {
            sb.append(" | ")
                    .append(((Event) task)
                            .getFrom())
                    .append(" | ")
                    .append(((Event) task)
                            .getTo());
        }

        return sb.toString();
    }

    /**
     * Converts a string representation back to a Task object.
     * Handles Todo, Deadline, and Event task types.
     *
     * @param line string representation of task
     * @return task object or null if conversion fails
     */
    protected Task convertStringToTask(String line) {
        try {
            String[] parts = line.split(" \\| ", -1);

            if (parts.length < 3) {
                return null;
            }

            return getTaskTypeReturn(parts);

        } catch (Exception e) {
            return null;
        }
    }

    private Task getTaskTypeReturn(String[] parts) throws DukeException {
        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task = null;

        switch (taskType) {
        case "T":
            task = new Todo("todo "
                    + description);
            break;
        case "D":
            if (parts.length < 4) {
                return task;
            }
            task = new Deadline("deadline "
                    + description
                    + " /by "
                    + parts[3]);
            break;
        case "E":
            if (parts.length < 5) {
                return task;
            }
            task = new Event("event "
                    + description
                    + " /from "
                    + parts[3]
                    + " /to "
                    + parts[4]);
            break;
        default:
            return task;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
