package org.example.taskproject.dtos.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDate;

public class TaskInDto {
    @Size(min = 2, max = 50, message = "Title must be between 2 and 50 characters long")
    private String title;
    @Size(min = 20, max = 225, message = "Description must be between 20 and 225 characters long")
    private String description;
    private LocalDate dueDate;
    @JsonProperty("isCritical")
    private Boolean isCritical;
    @JsonProperty("isCompleted")
    private Boolean isCompleted;
    public TaskInDto(){
    }

    public Boolean getCompleted() {
        return isCompleted;
    }
    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCritical() {
        return isCritical;
    }

    public void setCritical(Boolean isCritical) {
        this.isCritical = isCritical;
    }
}
