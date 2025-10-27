package scheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class TaskSchedulerUI extends JFrame {
    private final TaskManager taskManager;

    private JTextField titleField;
    private JComboBox<String> priorityBox;
    private JTextField deadlineField;
    private JTextArea taskArea;

    // New: text field for delete-by-title
    private JTextField deleteTitleField;

    public TaskSchedulerUI() {
        taskManager = new TaskManager();
        setTitle("Smart Task Scheduler");
        setSize(540, 460);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Top panel for input
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 6, 6));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Task"));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Priority:"));
        priorityBox = new JComboBox<>(new String[]{"1 (High)", "2 (Medium)", "3 (Low)"});
        inputPanel.add(priorityBox);

        inputPanel.add(new JLabel("Deadline (yyyy-MM-ddTHH:mm):"));
        deadlineField = new JTextField(LocalDateTime.now().plusDays(1).toString().substring(0,16));
        inputPanel.add(deadlineField);

        JButton addButton = new JButton("Add Task");
        inputPanel.add(addButton);

        JButton showButton = new JButton("Show Tasks");
        inputPanel.add(showButton);

        add(inputPanel, BorderLayout.NORTH);

        // Center: text area to display tasks
        taskArea = new JTextArea();
        taskArea.setEditable(false);
        taskArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(taskArea), BorderLayout.CENTER);

        // Bottom panel: delete controls and status
        JPanel bottom = new JPanel(new BorderLayout(6, 6));

        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete Task"));
        deletePanel.add(new JLabel("Title to delete:"));
        deleteTitleField = new JTextField(18);
        deletePanel.add(deleteTitleField);
        JButton deleteButton = new JButton("Delete by Title");
        deletePanel.add(deleteButton);

        bottom.add(deletePanel, BorderLayout.NORTH);

        // status label
        JLabel statusLabel = new JLabel("Ready");
        bottom.add(statusLabel, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener((ActionEvent e) -> {
            addTask(statusLabel);
        });

        showButton.addActionListener((ActionEvent e) -> {
            showTasks();
            statusLabel.setText("Displayed " + taskManager.size() + " tasks");
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            String titleToDelete = deleteTitleField.getText().trim();
            if (titleToDelete.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a title to delete.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            boolean removed = taskManager.removeTaskByTitle(titleToDelete);
            if (removed) {
                JOptionPane.showMessageDialog(this, "Task deleted: " + titleToDelete);
                statusLabel.setText("Deleted \"" + titleToDelete + "\". Total: " + taskManager.size());
                showTasks();
                deleteTitleField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No task found with title: " + titleToDelete, "Info", JOptionPane.INFORMATION_MESSAGE);
                statusLabel.setText("Delete attempted, not found.");
            }
        });
    }

    private void addTask(JLabel statusLabel) {
        try {
            String title = titleField.getText().trim();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int priority = priorityBox.getSelectedIndex() + 1;
            LocalDateTime deadline = null;
            String dtext = deadlineField.getText().trim();
            if (!dtext.isEmpty()) {
                // parse simple ISO-like input
                deadline = LocalDateTime.parse(dtext);
            }
            taskManager.addTask(new Task(title, priority, deadline));
            JOptionPane.showMessageDialog(this, "Task added successfully!");
            titleField.setText("");
            statusLabel.setText("Task added. Total: " + taskManager.size());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Use format yyyy-MM-ddTHH:mm (example: 2025-10-15T18:00).",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showTasks() {
        StringBuilder sb = new StringBuilder();
        int idx = 1;
        for (Task t : taskManager.getAllTasksSorted()) {
            sb.append(String.format("%2d. %s%n", idx++, t));
        }
        taskArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskSchedulerUI().setVisible(true));
    }
}
