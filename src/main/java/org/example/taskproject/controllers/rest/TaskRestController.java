package org.example.taskproject.controllers.rest;

import jakarta.validation.Valid;
import org.example.taskproject.dtos.in.TaskInDto;
import org.example.taskproject.enums.Priority;
import org.example.taskproject.mappers.TaskMapper;
import org.example.taskproject.models.Task;
import org.example.taskproject.services.contracts.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    @Autowired
    public TaskRestController(TaskService taskService, TaskMapper taskMapper){
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }
    @PostMapping
    public Task createTask(@RequestBody @Valid TaskInDto taskInDto) {
        Task task = taskMapper.fromDto(taskInDto);
        return taskService.create(task);
    }
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer id) {
        return taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
    @GetMapping
    public List<Task> getTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Boolean isCompleted,
            @RequestParam(defaultValue = "priority") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return taskService.getAllTasks(
                title, description, priority, isCompleted, sortBy, sortDirection
        );
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @Valid @RequestBody TaskInDto taskInDto) {
        Task task = taskMapper.fromDto(taskInDto);
        return taskService.updateTask(id, task);
    }
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }
}
