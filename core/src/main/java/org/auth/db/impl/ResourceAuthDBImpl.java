package org.auth.db.impl;

import org.auth.Resource;
import org.auth.db.AuthDB;
import org.auth.api.Role;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceAuthDBImpl implements AuthDB {
    /**
     * Resource Id vs user id vs roles
     * This provides user which has any access to the resource, once you have user id you can get the roles for that user for that resource
     * and once you will get the roles you can get the access for that resource.
     */
    private final Map<String, Map<String, Set<Role>>> accessControlDBMap;


    /**
     *
     */
    public ResourceAuthDBImpl() {
        this.accessControlDBMap = new ConcurrentHashMap<>();
    }

    /**
     * Get user role for the resource.
     *
     * @param resourceId
     * @param userId
     * @return
     */
    public Set<Role> getUserRoles(String resourceId, String userId) {
        return Optional.ofNullable(this.accessControlDBMap.get(resourceId)).map(m -> m.get(userId)).orElse(Collections.emptySet());
    }

    /**
     * Set the roles for resources.
     *
     * @param resourceId
     * @param userId
     * @param roles
     */
    @Override
    public void provideRoleToUser(String resourceId, String userId, Set<Role> roles) {
        if (this.accessControlDBMap.containsKey(resourceId)) {
            this.accessControlDBMap.get(resourceId).put(userId, roles);
        } else {
            Map<String, Set<Role>> rolesMap = new HashMap<>();
            rolesMap.put(userId, roles);
            this.accessControlDBMap.put(resourceId, rolesMap);
        }
    }

    @Override
    public void addRoleToUser(String resourceId, String userId, Role role) {
        if (this.accessControlDBMap.containsKey(resourceId)) {
            Map<String, Set<Role>> rolesMap = this.accessControlDBMap.get(resourceId);
            if (rolesMap.containsKey(userId)) {
                rolesMap.get(userId).add(role);
            } else {
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                rolesMap.put(userId, roles);
                this.accessControlDBMap.put(resourceId, rolesMap);
            }
            return;
        }
        throw new RuntimeException("Resource not found with id : " + resourceId);
    }

    @Override
    public void registerResource(String resourceId) {
        if(!this.accessControlDBMap.containsKey(resourceId)) {
            this.accessControlDBMap.put(resourceId, new HashMap<>());
            return;
        }
        throw new RuntimeException("Resource already registered.");
    }

    @Override
    public Set<String> getRegisteredResourceIds() {
        return this.accessControlDBMap.keySet();
    }
}
