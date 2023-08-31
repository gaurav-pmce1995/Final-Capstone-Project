package com.kanban.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
* without this class
* we have to fetch all task
* to count task in specific status
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskStatus {
    private String taskStatusName;
    private List<Task> tasks = new ArrayList<>();
    private boolean taskLimitEnabled;

}
