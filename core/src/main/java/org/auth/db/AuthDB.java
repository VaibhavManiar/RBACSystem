package org.auth.db;

import org.auth.Resource;
import org.auth.api.Role;

import java.util.Set;

public interface AuthDB {
    Set<Role> getUserRoles(String resourceId, String userId);
    void provideRoleToUser(String resourceId, String userId, Set<Role> roles);
    void addRoleToUser(String resourceId, String userId, Role role);
    void registerResource(String resourceId);
    Set<String> getRegisteredResourceIds();
}
