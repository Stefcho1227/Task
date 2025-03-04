package org.example.taskproject.services;

import org.example.taskproject.enums.Priority;
import org.example.taskproject.models.Task;
import org.example.taskproject.repositories.TaskRepository;
import org.example.taskproject.services.contracts.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getAllTasks(String title, String description, Priority priority, Boolean isCompleted) {
        return taskRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if(title != null && !title.isEmpty()){
                predicate = cb.and(predicate, cb.like(root.get("title"), "%" + title + "%"));
            }
            if (description != null && !description.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(root.get("description"), "%" + description + "%"));
            }
            if (priority != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("priority"), priority));
            }
            if (isCompleted != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("isCompleted"), isCompleted));
            }
            return predicate;
        });
    }
    @Transactional
    @Override
    public Task updateTask(Integer id, Task newTask) {
        Task task = getTaskById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setDueDate(newTask.getDueDate());
        task.setCritical(newTask.isCritical());
        task.setCompleted(newTask.isCompleted());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
    }
}
