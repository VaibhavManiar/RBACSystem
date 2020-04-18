package org.test.client.cmd;

import org.auth.AuthDetails;
import org.auth.api.DeleteRole;
import org.auth.api.ReadRole;
import org.auth.api.WriteRole;

import java.net.MalformedURLException;
import java.net.URL;

public class Bootstrap {

    public static final String documentsFolderPath = "file://Users/vaibhavmaniar/Documents/Test";

    public static void main(String[] args) throws MalformedURLException {
        org.resource.file.Bootstrap fileResourceBootstrap = new org.resource.file.Bootstrap("Admin", "admin", "admin");

        AuthDetails authDetails = new AuthDetails("admin", "admin");

        URL resourceURL = new URL(documentsFolderPath + "/" + System.currentTimeMillis() + ".txt");

        fileResourceBootstrap.createFile(resourceURL, authDetails);
        fileResourceBootstrap.getFileAccessEngine().addFileAccessUser("Vaibhav", "vaibhav", "vaibhav");

        fileResourceBootstrap.getFileAccessEngine().assignRoleToUser(authDetails, resourceURL, "vaibhav", new ReadRole());
        fileResourceBootstrap.readFile(resourceURL, new AuthDetails("vaibhav", "vaibhav"));

        fileResourceBootstrap.getFileAccessEngine().assignRoleToUser(authDetails, resourceURL, "vaibhav", new WriteRole());
        fileResourceBootstrap.amendFile(resourceURL, "Data", new AuthDetails("vaibhav", "vaibhav"));

        fileResourceBootstrap.getFileAccessEngine().assignRoleToUser(authDetails, resourceURL, "vaibhav", new DeleteRole());
        fileResourceBootstrap.deleteFile(resourceURL, new AuthDetails("vaibhav", "vaibhav"));
    }
}