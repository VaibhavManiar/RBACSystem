package org.auth.service.impl;

import org.auth.dao.UserDao;
import org.auth.model.AuthDetails;
import org.auth.model.User;
import org.auth.service.UserService;
import org.auth.util.PasswordEncoder;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao dao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.dao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(String name, String userName, String password) {
        if (!dao.getUserByUserNameIfAvailable(userName).isPresent()) {
            User user = new User(name, userName, this.passwordEncoder.encode(password));
            dao.addUser(user);
            return user;
        }
        throw new RuntimeException("User already present with user name : [" + userName + "]");
    }

    @Override
    public Optional<User> getUser(String userId) {
        return dao.getUser(userId);
    }

    @Override
    public Optional<User> getUserByUserNameIfAvailable(String userName) {
        return this.dao.getUserByUserNameIfAvailable(userName);
    }

    @Override
    public boolean validateAdminUserNameAndPassword(AuthDetails authDetails) {
        return this.getUserByUserNameIfAvailable(authDetails.getUserName()).filter(user -> user.getPassword().equalsIgnoreCase(authDetails.getPassword())).isPresent();
    }

    @Override
    public Optional<User> getValidUserIfAvailable(AuthDetails authDetails) {
        return this.getUserByUserNameIfAvailable(authDetails.getUserName()).
                filter(user -> user.getPassword().equalsIgnoreCase(passwordEncoder.encode(authDetails.getPassword())));
    }
}