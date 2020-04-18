package org.auth.db.impl;

import org.auth.db.UserDB;
import org.auth.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Always create a single instance of User DB.
 */
public class UserDBImpl implements UserDB {
    private final Map<String, User> userIdVsUserMap;
    private final Map<String, User> userNameVsUserMap;

    public UserDBImpl() {
        this.userIdVsUserMap = new ConcurrentHashMap<>();
        this.userNameVsUserMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<User> getUserIfAvailable(String userId) {
        return userIdVsUserMap.containsKey(userId) ? Optional.of(userIdVsUserMap.get(userId)) : Optional.empty();
    }

    @Override
    public void addUser(User user) {
        if(userIdVsUserMap.values().stream().map(User::getUserName).filter(userName-> user.getUserName().equalsIgnoreCase(userName)).collect(Collectors.toSet()).size() > 0) {
            throw new RuntimeException("User with user name : [" + user.getUserName() + "] already exists.");
        }
        this.userIdVsUserMap.put(user.getId(), user);
        this.userNameVsUserMap.put(user.getUserName(), user);
    }

    @Override
    public Optional<User> getUserByUserNameIfAvailable(String userName) {
        return userNameVsUserMap.containsKey(userName) ? Optional.of(userNameVsUserMap.get(userName)) : Optional.empty();
    }
}