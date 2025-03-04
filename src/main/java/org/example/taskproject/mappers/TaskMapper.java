package org.example.taskproject.mappers;

import org.example.taskproject.dtos.in.TaskInDto;
import org.example.taskproject.models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskMapper(){
    }
    public Task fromDto(TaskInDto taskInDto){
        Task task = new Task();
        task.setTitle(taskInDto.getTitle());
        task.setDescription(taskInDto.getDescription());
        task.setDueDate(taskInDto.getDueDate());
        task.setCritical(taskInDto.getCritical());
        task.setCompleted(Boolean.FALSE);
        return task;
    }
}
