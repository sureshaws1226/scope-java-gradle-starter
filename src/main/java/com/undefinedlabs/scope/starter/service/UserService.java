package com.undefinedlabs.scope.starter.service;

import com.undefinedlabs.scope.starter.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User save(User user);
}
