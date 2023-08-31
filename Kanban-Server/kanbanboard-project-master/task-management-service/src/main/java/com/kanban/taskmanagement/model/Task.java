package com.kanban.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {
    private String title;
    private String description;
    private List<String> subTasks;
    private LocalDate createDate;
    private LocalDate dueDate;
    private String priority;
    private Set<String> assignTo = new HashSet<>();
    private User assignBy;
    private String color;
}
