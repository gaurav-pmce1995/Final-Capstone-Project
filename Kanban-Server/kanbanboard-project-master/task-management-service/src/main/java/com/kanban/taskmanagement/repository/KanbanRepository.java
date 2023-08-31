package com.kanban.taskmanagement.repository;

import com.kanban.taskmanagement.model.Board;
import com.kanban.taskmanagement.model.Kanban;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface KanbanRepository extends MongoRepository<Kanban, String> {
     List<Kanban> findByEmail(String email);

     Kanban findByKanbanName(String kanbanName);

     List<Kanban> findByTeamMembers(String email);
}
