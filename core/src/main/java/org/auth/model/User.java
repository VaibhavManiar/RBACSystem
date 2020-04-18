package org.auth.model;

public class User {
    private final String id;
    private final String name;
    private final String userName;
    private String password;

    public User(String name, String userName, String password) {
        this.id = IdGenerator.generateUserId();
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
