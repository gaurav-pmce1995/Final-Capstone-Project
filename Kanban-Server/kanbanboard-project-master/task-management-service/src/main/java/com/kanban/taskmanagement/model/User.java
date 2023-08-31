package com.kanban.taskmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
// to check already register
// if not send registration email
@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private String email;
    private String username;
}
