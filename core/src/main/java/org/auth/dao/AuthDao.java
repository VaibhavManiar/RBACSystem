package org.auth.dao;

import org.auth.Resource;
import org.auth.api.Role;

import java.util.Set;

public interface AuthDao {
    Set<Role> getUserRoles(String resourceId, String userId);
    void provideRoleToUser(String resourceId, String userId, Set<Role> roles);
    void addRoleToUser(String resourceId, String userId, Role role);
    void registerResource(String resourceId);
    Set<String> getRegisteredResource();
}
