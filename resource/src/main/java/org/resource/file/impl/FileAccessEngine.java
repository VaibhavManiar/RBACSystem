package org.resource.file.impl;

import org.auth.AuthDetails;
import org.auth.RBACEngine;
import org.auth.api.Role;
import org.resource.file.AccessEngine;

import java.net.URL;

public class FileAccessEngine implements AccessEngine {
    private final RBACEngine rbacEngine;

    public FileAccessEngine(RBACEngine rbacEngine) {
        this.rbacEngine = rbacEngine;
    }

    @Override
    public boolean addFileAccessUser(String name, String userName, String password) {
        this.rbacEngine.addUser(name, userName, password);
        return Boolean.TRUE;
    }

    @Override
    public boolean assignRoleToUser(AuthDetails authDetails, URL url, String userName, Role role) {
        this.rbacEngine.assignRole(convertToRBACEngineAdminAuthDetails(authDetails), new FileResource(url), userName, role);
        return Boolean.TRUE;
    }

    private org.auth.model.AuthDetails convertToRBACEngineAdminAuthDetails(AuthDetails authDetails) {
        return new org.auth.model.AuthDetails(authDetails.getUserName(), authDetails.getPassword());
    }
}
