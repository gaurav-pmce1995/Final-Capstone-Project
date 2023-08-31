package com.kanban.taskmanagement.repository;

import com.kanban.taskmanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
