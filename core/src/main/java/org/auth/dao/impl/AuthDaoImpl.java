package org.auth.dao.impl;

import org.auth.Resource;
import org.auth.api.Role;
import org.auth.dao.AuthDao;
import org.auth.db.AuthDB;

import java.util.Set;

public class AuthDaoImpl implements AuthDao {

    private final AuthDB authDB;

    public AuthDaoImpl(AuthDB authDB) {
        this.authDB = authDB;
    }

    @Override
    public Set<Role> getUserRoles(String resourceId, String userId) {
        return authDB.getUserRoles(resourceId, userId);
    }

    @Override
    public void provideRoleToUser(String resourceId, String userId, Set<Role> roles) {
        this.authDB.provideRoleToUser(resourceId, userId, roles);
    }

    @Override
    public void addRoleToUser(String resourceId, String userId, Role role) {
        this.authDB.addRoleToUser(resourceId, userId, role);
    }

    @Override
    public void registerResource(String resourceId) {
        this.authDB.registerResource(resourceId);
    }

    @Override
    public Set<String> getRegisteredResource() {
        return this.authDB.getRegisteredResourceIds();
    }
}
