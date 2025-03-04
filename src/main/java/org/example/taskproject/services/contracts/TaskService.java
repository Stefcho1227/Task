package org.example.taskproject.services.contracts;

import org.example.taskproject.enums.Priority;
import org.example.taskproject.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task create(Task task);
    Optional<Task> getTaskById(Integer id);
    List<Task> getAllTasks(String title, String description, Priority priority, Boolean isCompleted, String sortBy, String direction);
    Task updateTask(Integer id, Task newTask);
    void deleteTask(Integer id);
}
