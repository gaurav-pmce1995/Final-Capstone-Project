package com.kanban.authentication.security;

import com.kanban.authentication.model.User;

import java.util.Map;

public interface JwtToken {
    Map<String, String> generateToken(User user);
}
