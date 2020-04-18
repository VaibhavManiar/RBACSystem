package org.auth.service.impl;

import org.auth.dao.AuthDao;
import org.auth.api.ActionType;
import org.auth.api.Role;
import org.auth.service.AuthService;

import java.util.Set;
import java.util.stream.Collectors;

public class ResourceAuthServiceImpl implements AuthService {

    private final AuthDao dao;
    public ResourceAuthServiceImpl(AuthDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean isAuthorized(String userId, String resourceId, Role role) {
        Set<Role> roles = this.dao.getUserRoles(resourceId, userId);
        Set<ActionType> actionTypes = roles.stream().map(r-> r.getActionType().stream()).flatMap(f->f).collect(Collectors.toSet());
        return actionTypes.containsAll(role.getActionType());
    }

    @Override
    public boolean assignAuth(String userId, String resourceId, Role role) {
        this.dao.addRoleToUser(resourceId, userId, role);
        return Boolean.TRUE;
    }

    @Override
    public Set<Role> getRoles(String userId, String resourceId) {
        return this.dao.getUserRoles(resourceId, userId);
    }

    @Override
    public void registerResource(String resourceId) {
        this.dao.registerResource(resourceId);
    }

    @Override
    public boolean isResourcesRegistered(String resourceId) {
        return this.dao.getRegisteredResource().contains(resourceId);
    }
}
