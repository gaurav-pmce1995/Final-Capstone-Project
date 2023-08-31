package com.kanban.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
    private String boardName;
    private List<TaskStatus> taskStatuses = new ArrayList<>();
}
