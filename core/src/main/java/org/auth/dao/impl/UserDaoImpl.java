package org.auth.dao.impl;

import org.auth.dao.UserDao;
import org.auth.db.UserDB;
import org.auth.model.User;

import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final UserDB userDB;

    public UserDaoImpl(UserDB userDB) {
        this.userDB = userDB;
    }

    @Override
    public boolean addUser(User user) {
        this.userDB.addUser(user);
        return Boolean.TRUE;
    }

    @Override
    public Optional<User> getUser(String userId) {
        return this.userDB.getUserIfAvailable(userId);
    }

    @Override
    public Optional<User> getUserByUserNameIfAvailable(String userName) {
        return this.userDB.getUserByUserNameIfAvailable(userName);
    }
}
