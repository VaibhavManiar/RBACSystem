package org.auth;

import org.auth.api.ActionType;
import org.auth.api.AdminRole;
import org.auth.api.CustomRole;
import org.auth.api.Role;
import org.auth.dao.impl.AuthDaoImpl;
import org.auth.dao.impl.UserDaoImpl;
import org.auth.db.impl.ResourceAuthDBImpl;
import org.auth.db.impl.UserDBImpl;
import org.auth.model.AuthDetails;
import org.auth.model.User;
import org.auth.service.AuthService;
import org.auth.service.UserService;
import org.auth.service.impl.ResourceAuthServiceImpl;
import org.auth.service.impl.UserServiceImpl;
import org.auth.util.PasswordEncoder;
import org.auth.util.SimpleBase64Encoder;

import java.util.Optional;
import java.util.Set;

public class RBACEngine {

    private final UserService userService;
    private final AuthService resourceAuthService;

    public static RBACEngine start() {
        return new RBACEngine();
    }

    private RBACEngine() {
        this.userService = new UserServiceImpl(new UserDaoImpl(new UserDBImpl()), new SimpleBase64Encoder());
        this.resourceAuthService = new ResourceAuthServiceImpl(new AuthDaoImpl(new ResourceAuthDBImpl()));
    }

    public RBACEngine assignRole(AuthDetails authDetails, Resource resource, String userName, Role role) {
        if (this.isAuthorizedToAssignAccess(authDetails, resource.getId())) {
            assignRole(resource, userName, role);
        } else {
            throw new RuntimeException("User : [" + authDetails.getUserName() + "] is not authorized.");
        }
        return this;
    }

    public RBACEngine assignAdminRole(Resource resource, String userName) {
        assignRole(resource, userName, new AdminRole());
        return this;
    }

    public void registerResource(String resourceId) {
        this.resourceAuthService.registerResource(resourceId);
    }

    private void assignRole(Resource resource, String userName, Role role) {
        this.userService.getUserByUserNameIfAvailable(userName).
                map(user -> resourceAuthService.assignAuth(user.getId(), resource.getId(), role)).
                orElseThrow(() -> new RuntimeException("User not found for user name : " + userName));
    }

    public RBACEngine assignRoles(AuthDetails authDetails, Resource resource, String userName, Set<Role> roles) {
        if (this.isAuthorizedToAssignAccess(authDetails, resource.getId())) {
            Optional<User> userOptional = this.userService.getUserByUserNameIfAvailable(userName);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                roles.parallelStream().forEach(role -> resourceAuthService.assignAuth(user.getId(), resource.getId(), role));
            } else {
                throw new RuntimeException("User not found for user name : " + userName);
            }
        } else {
            throw new RuntimeException("User : [" + authDetails.getUserName() + "] is not authorized.");
        }
        return this;
    }

    public boolean isAuthorized(AuthDetails authDetails, String resourceId, ActionType actionType) {
        CustomRole customRole = new CustomRole("CUSTOM").addActionType(actionType);
        if (!this.resourceAuthService.isResourcesRegistered(resourceId)) {
            throw new RuntimeException("Resource is not registered : [" + resourceId + "]");
        }
        Optional<User> userOptional = this.userService.getValidUserIfAvailable(authDetails);
        if (!userOptional.isPresent())
            throw new RuntimeException("Not a valid user : [" + authDetails.getUserName() + "]");
        else
            return resourceAuthService.isAuthorized(userOptional.get().getId(), resourceId, customRole);
    }


    public boolean isAuthorizedToAssignAccess(AuthDetails authDetails, String resourceId) {
        return this.isAuthorized(authDetails, resourceId, ActionType.ASSIGN_ROLE);
    }

    public User addUser(String name, String userName, String password) {
        return userService.addUser(name, userName, password);
    }
}
