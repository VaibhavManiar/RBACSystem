package org.auth.service;

import org.auth.model.AuthDetails;
import org.auth.model.User;

import java.util.Optional;

public interface UserService {
    User addUser(String name, String userName, String password);
    Optional<User> getUser(String userId);
    Optional<User> getUserByUserNameIfAvailable(String userName);
    boolean validateAdminUserNameAndPassword(AuthDetails authDetails);
    Optional<User> getValidUserIfAvailable(AuthDetails authDetails);
}
