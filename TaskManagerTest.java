package scheduler;

import java.time.LocalDateTime;

public class TaskManagerTest {
    public static void main(String[] args) {
        TaskManager tm = new TaskManager();
        LocalDateTime now = LocalDateTime.now();

        // Add tasks
        tm.addTask(new Task("Pay bills", 1, now.plusHours(6)));
        tm.addTask(new Task("Buy groceries", 2, now.plusDays(1)));
        tm.addTask(new Task("Read book", 3, now.plusWeeks(1)));
        tm.addTask(new Task("Fix critical bug", 1, now.plusHours(2)));

        System.out.println("All tasks (sorted by priority then deadline):");
        for (Task t : tm.getAllTasksSorted()) {
            System.out.println("  " + t);
        }

        System.out.println("\nPolling tasks in order:");
        Task next;
        while ((next = tm.getNextTask()) != null) {
            System.out.println("  NEXT -> " + next);
        }
    }
}
