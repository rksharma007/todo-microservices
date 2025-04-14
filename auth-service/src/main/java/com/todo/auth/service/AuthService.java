package com.todo.auth.service;

import com.todo.auth.model.User;

public interface AuthService {

    String register(User user);

    String login(User user);
}
