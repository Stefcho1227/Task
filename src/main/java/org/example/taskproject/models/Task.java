package org.example.taskproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import org.example.taskproject.enums.Priority;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "due_date")
    @FutureOrPresent
    private LocalDate dueDate;

    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;
    @Column(name = "is_critical")
    private Boolean isCritical;

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    @JsonIgnore
    public Boolean isCritical() {
        return isCritical;
    }

    public void setCritical(Boolean isCritical) {
        this.isCritical = isCritical;
    }
}
