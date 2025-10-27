package scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class TaskManager {
    private final PriorityQueue<Task> taskQueue;

    public TaskManager() {
        taskQueue = new PriorityQueue<>(); // tasks sorted automatically by compareTo
    }

    // Add a task
    public void addTask(Task task) {
        taskQueue.offer(task);
    }

    // Get the next highest-priority task
    public Task getNextTask() {
        return taskQueue.poll(); // removes and returns
    }

    // Peek at next task without removing
    public Task peekNextTask() {
        return taskQueue.peek();
    }

    // Get all tasks in priority order
    public List<Task> getAllTasksSorted() {
        PriorityQueue<Task> copy = new PriorityQueue<>(taskQueue);
        List<Task> sorted = new ArrayList<>();
        while (!copy.isEmpty()) {
            sorted.add(copy.poll());
        }
        return sorted;
    }

    // Remove a task
    public boolean removeTask(Task task) {
        return taskQueue.remove(task);
    }

    // Remove by title
    public boolean removeTaskByTitle(String title) {
        Iterator<Task> it = taskQueue.iterator();
        while (it.hasNext()) {
            Task t = it.next();
            if (t.getTitle().equals(title)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    public int size() {
        return taskQueue.size();
    }

    public void clear() {
        taskQueue.clear();
    }
}
