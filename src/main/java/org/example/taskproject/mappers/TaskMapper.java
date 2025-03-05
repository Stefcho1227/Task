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
        if(taskInDto.getCompleted() == null){
            task.setCompleted(Boolean.FALSE);
        } else{
            task.setCompleted(taskInDto.getCompleted());
        }
        return task;
    }
}
