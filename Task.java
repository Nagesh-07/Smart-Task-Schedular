package scheduler;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {
	
    private String title;
    private int priority;   // 1 = High, 2 = Medium, 3 = Low
    private LocalDateTime deadline; // can be null

    // Constructor
    public Task(String title, int priority, LocalDateTime deadline) {
    	
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
        
    }

    // Getters
    public String getTitle()
    
    { return title; }
    
    public int getPriority()
    
    { return priority; }
    
    public LocalDateTime getDeadline()
    
    { return deadline; }

    // compareTo for PriorityQueue
    @Override
    public int compareTo(Task other) {
        if (other == null) 
        	
        	{
        	return -1;
        	}
        
        int prioCompare = Integer.compare(this.priority, other.priority);
        
        if (prioCompare != 0) 
        	
        	{
        	return prioCompare;
        	}

        if (this.deadline == null && other.deadline == null)
        	
        	{
        	return 0;
        	}
        
        if (this.deadline == null)
        	
        	{
        	return 1;
        	}
        
        if (other.deadline == null)
        	
        	{
        	return -1;
        	}
        
        return this.deadline.compareTo(other.deadline);
    }

    // Safe equals method
    @Override
    public boolean equals(Object o) {
        if (this == o)
        	{
        	
        	return true;      // same object
        	
        	}
        
        if (!(o instanceof Task)) 
        	{
        	
        	return false;         // different type
        	
        	}
        
        Task task = (Task) o;                           // cast to Task
        return priority == task.priority &&
               Objects.equals(title, task.title) &&
               Objects.equals(deadline, task.deadline);
        
    }

    // hashCode using Objects.hash
    @Override
    public int hashCode() {
    	
        return Objects.hash(title, priority, deadline);
    }

    @Override
    public String toString() {
    	
        return String.format("%s (priority=%d, due=%s)",
                title,
                priority,
                deadline == null ? "none" : deadline.toString());
    }
}
