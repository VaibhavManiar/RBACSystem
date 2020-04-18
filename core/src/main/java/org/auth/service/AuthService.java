package org.auth.service;

import org.auth.api.Role;

import java.util.Set;

public interface AuthService {
    boolean isAuthorized(String userId, String resourceId, Role role);
    boolean assignAuth(String userId, String resourceId, Role role);
    Set<Role> getRoles(String userId, String resourceId);
    void registerResource(String resourceId);
    boolean isResourcesRegistered(String resourceId);
}
