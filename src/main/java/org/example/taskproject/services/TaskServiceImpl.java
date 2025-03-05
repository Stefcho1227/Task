package org.example.taskproject.services;

import org.example.taskproject.enums.Priority;
import org.example.taskproject.models.Task;
import org.example.taskproject.repositories.TaskRepository;
import org.example.taskproject.services.contracts.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
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
        Priority priority = computePriority(task);
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> getAllTasks( String title,
                                   String description,
                                   Priority priority,
                                   Boolean isCompleted,
                                   String sortBy,
                                   String sortDir) {
        Specification<Task> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (priority != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("priority"), priority));
            }
            if (isCompleted != null){
                predicate = cb.and(predicate,
                        cb.equal(root.get("isCompleted"), isCompleted));
            }
            return predicate;
        };
        //Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Sort sort;
        if(sortBy.equals("priority")){
            sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by("priority").ascending()
                    : Sort.by("priority").descending();
        } else {
            sort = sortDir.equalsIgnoreCase("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }
        return taskRepository.findAll(spec, sort);
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
        Priority priority = computePriority(newTask);
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
    }
    private Priority computePriority(Task task){
        if(task.isCompleted()){
            return Priority.LOW;
        }
        if(task.isCritical()){
            return Priority.HIGH;
        }
        if(task.getDueDate() != null){
            long daysUntilEnd = ChronoUnit.DAYS.between(LocalDate.now(), task.getDueDate());
            if(daysUntilEnd < 3){
                return Priority.HIGH;
            } else if(daysUntilEnd < 7){
                return Priority.MEDIUM;
            } else {
                return Priority.LOW;
            }
        }
        return Priority.LOW;
    }
}
