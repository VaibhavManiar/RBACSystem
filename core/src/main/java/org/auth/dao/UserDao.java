package org.auth.dao;

import org.auth.model.User;

import java.util.Optional;

public interface UserDao {
    boolean addUser(User user);
    Optional<User> getUser(String userId);
    Optional<User> getUserByUserNameIfAvailable(String userName);
}
