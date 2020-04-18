package org.resource.file;

import org.auth.AuthDetails;
import org.auth.api.Role;

import java.net.URL;

public interface AccessEngine {
    boolean addFileAccessUser(String name, String userName, String password);
    boolean assignRoleToUser(AuthDetails authDetails, URL url, String userName, Role role);
}
