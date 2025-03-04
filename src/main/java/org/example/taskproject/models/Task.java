package org.example.taskproject.models;

import jakarta.persistence.*;

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
    private LocalDate dueDate;


    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "priority")
    private String priority;
    @Column(name = "is_critical")
    private boolean isCritical;

}
