package com.kanban.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Kanban {
    @Id
    private String id;
    /*
    * email
    * to save kanban one place
    * otherwise copy of kanban saved
    * for every assigned user
    */
    private String email;
    private String kanbanName;
    private List<Board> boards = new ArrayList<>();
    private Set<String> teamMembers = new HashSet<>();
}


