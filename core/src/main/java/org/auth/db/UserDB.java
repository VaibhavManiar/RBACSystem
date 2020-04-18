package org.auth.db;

import org.auth.model.User;

import java.util.Optional;

public interface UserDB {
    Optional<User> getUserIfAvailable(String userId);
    void addUser(User user);
    Optional<User> getUserByUserNameIfAvailable(String userName);
}
