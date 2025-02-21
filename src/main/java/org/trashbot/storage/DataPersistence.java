package org.trashbot.storage;

import java.io.IOException;
import java.util.List;

import org.trashbot.exceptions.DukeException;
import org.trashbot.tasks.Task;

/**
 * Defines the contract for persisting and retrieving task data in the task management system.
 * This interface provides methods for saving tasks to and loading tasks from a persistent
 * storage medium.
 *
 * <p>Implementations of this interface should handle all the necessary data serialization,
 * deserialization, and storage operations. They should also manage any required resources
 * (like file handles or database connections) appropriately.</p>
 *
 * <p>Example usage with a hypothetical implementation:
 * <pre>
 * DataPersistence storage = new FileDataPersistence("tasks.txt");
 *
 * // Saving tasks
 * List&lt;Task&gt; tasks = new ArrayList<>();
 * tasks.add(new Todo("Read book"));
 * storage.save(tasks);
 *
 * // Loading tasks
 * List&lt;Task&gt; loadedTasks = storage.load();
 * </pre>
 * </p>
 *
 * @see Task
 */
public interface DataPersistence {
    /**
     * Saves a list of tasks to persistent storage.
     *
     * <p>This method should handle the serialization of task objects and write them
     * to the underlying storage medium. The entire operation should be atomic -
     * either all tasks are saved successfully, or none are saved and an exception
     * is thrown.</p>
     *
     * @param tasks The list of tasks to be saved
     * @throws IOException if there is an error writing to the storage medium,
     *                     such as file system errors or network issues
     */
    void save(List<Task> tasks) throws IOException, DukeException;

    /**
     * Loads all tasks from persistent storage.
     *
     * <p>This method should handle reading and deserializing task objects from
     * the underlying storage medium. If the storage is empty or has not been
     * initialized, an empty list should be returned.</p>
     *
     * @return A list containing all stored tasks, or an empty list if no tasks
     *     are stored
     * @throws IOException if there is an error reading from the storage medium,
     *                     such as file system errors or network issues
     */
    List<Task> load() throws IOException;
}
