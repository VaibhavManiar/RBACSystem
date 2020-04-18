package org.resource.file;

import org.auth.AuthDetails;
import org.auth.RBACEngine;
import org.auth.Resource;
import org.auth.api.*;
import org.resource.file.impl.FileAccessEngine;
import org.resource.file.impl.FileResource;

import java.net.URL;

public class Bootstrap {
    private final RBACEngine engine;
    private final AccessEngine fileAccessEngine;

    public Bootstrap(String adminName, String adminUserName, String password) {
        this.engine = RBACEngine.start();
        this.engine.addUser(adminName, adminUserName, password);
        this.fileAccessEngine = new FileAccessEngine(this.engine);
    }

    public AccessEngine getFileAccessEngine() {
        return fileAccessEngine;
    }

    public void assignReadAccess(URL url, AuthDetails authDetails, String userName) {
        this.engine.assignRole(convertToRBACEngineAdminAuthDetails(authDetails), new FileResource(url), userName, new ReadRole());
    }

    public void assignWriteAccess(URL url, AuthDetails authDetails, String userName) {
        this.engine.assignRole(convertToRBACEngineAdminAuthDetails(authDetails), new FileResource(url), userName, new WriteRole());
    }

    public void assignManagerAccess(URL url, AuthDetails authDetails, String userName) {
        this.engine.assignRole(convertToRBACEngineAdminAuthDetails(authDetails), new FileResource(url), userName, new ManagerRole());
    }

    public void assignAdminAccess(URL url, AuthDetails authDetails, String userName) {
        this.engine.assignRole(convertToRBACEngineAdminAuthDetails(authDetails), new FileResource(url), userName, new AdminRole());
    }

    private void assignAdminAccess(URL url, String userName) {
        this.engine.assignAdminRole(new FileResource(url), userName);
    }

    public String readFile(URL url, AuthDetails authDetails) {
        Resource resource = new FileResource(url);
        if (resource instanceof Readable) {
            if (this.engine.isAuthorized(convertToRBACEngineAdminAuthDetails(authDetails), resource.getId(), ActionType.READ)) {
                return new String(((Readable) resource).read());
            } else {
                throw new RuntimeException("User : [" + authDetails.getUserName() + "] don't have " + ActionType.READ + " access.");
            }
        } else {
            throw new RuntimeException("Resource is not readable.");
        }
    }

    public boolean amendFile(URL url, String data, AuthDetails authDetails) {
        Resource resource = new FileResource(url);
        if (resource instanceof Writable) {
            if (this.engine.isAuthorized(convertToRBACEngineAdminAuthDetails(authDetails), resource.getId(), ActionType.WRITE)) {
                return ((Writable) resource).write(data);
            } else {
                throw new RuntimeException("User : [" + authDetails.getUserName() + "] don't have " + ActionType.WRITE + " access.");
            }
        } else {
            throw new RuntimeException("Resource is not writable.");
        }
    }

    public boolean createFile(URL url, AuthDetails authDetails) {
        FileResource resource = new FileResource(url);
        resource.create();
        this.engine.registerResource(resource.getId());
        this.assignAdminAccess(url, authDetails.getUserName());
        return Boolean.TRUE;
    }

    public boolean deleteFile(URL url, AuthDetails authDetails) {
        Resource resource = new FileResource(url);
        if (resource instanceof Deletable) {
            if (this.engine.isAuthorized(convertToRBACEngineAdminAuthDetails(authDetails), resource.getId(), ActionType.DELETE)) {
                return ((Deletable) resource).delete();
            } else {
                throw new RuntimeException("User : [" + authDetails.getUserName() + "] don't have " + ActionType.DELETE + " access.");
            }
        } else {
            throw new RuntimeException("Resource is not deletable.");
        }
    }

    private org.auth.model.AuthDetails convertToRBACEngineAdminAuthDetails(AuthDetails authDetails) {
        return new org.auth.model.AuthDetails(authDetails.getUserName(), authDetails.getPassword());
    }
}
