package org.auth.model;

import java.util.UUID;

public class IdGenerator {
    public static String generateUserId() {
        return "USER_" + UUID.randomUUID() + "_" + System.currentTimeMillis();
    }

    public static String generateResourceId(String resourceType, String resourceURL) {
        return resourceType + "_" + resourceURL.hashCode();
    }
}
