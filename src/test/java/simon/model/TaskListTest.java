package simon.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import simon.exception.InputFormatException;
import simon.storage.Storage;
import simon.task.Deadline;
import simon.task.Event;
import simon.task.Task;
import simon.task.Todo;


class TaskListTest {

    @Test
    void addDeadline_addsTaskCorrectly() {
        TaskList tasks = new TaskList(new FakeStorageStub());
        Task deadline = new Deadline("Finish homework", "18/02/2026");

        tasks.add(deadline);

        assertTrue(tasks.getTasks().contains(deadline), "TaskList should contain the added deadline");

        String taskStr = tasks.getTasks().get(0).toString();
        assertTrue(taskStr.contains("Finish homework"), "Deadline description should be correct");
    }

    @Test
    void addEvent_addsTaskCorrectly() {
        TaskList tasks = new TaskList(new FakeStorageStub());
        Task event = new Event("Team meeting", "18/02/2026 1400", "18/02/2026 1600");

        tasks.add(event);

        assertTrue(tasks.getTasks().contains(event), "TaskList should contain the added event");

        String taskStr = tasks.getTasks().get(0).toString();
        assertTrue(taskStr.contains("Team meeting"), "Event description should be correct");
    }

    @Test
    void deleteTask_removesTaskCorrectly() throws InputFormatException {
        TaskList tasks = new TaskList(new FakeStorageStub());
        Task task1 = new Todo("Task 1");
        Task task2 = new Todo("Task 2");

        tasks.add(task1);
        tasks.add(task2);

        // remove task1 using the internal list
        tasks.delete(1);

        assertFalse(tasks.getTasks().contains(task1), "Task 1 should be removed");
        assertTrue(tasks.getTasks().contains(task2), "Task 2 should still be present");
    }

    /**
     * Minimal fake Storage to prevent NullPointerException.
     */
    static class FakeStorageStub extends Storage {
        // call Storage constructor with dummy path
        public FakeStorageStub() {
            super("fake_path.txt");
        }

        @Override
        public ArrayList<Task> loadTasks() {
            return new ArrayList<>(); // start with empty list
        }

        @Override
        public void saveTasks(ArrayList<Task> tasks) {
            // do nothing
        }
    }
}
