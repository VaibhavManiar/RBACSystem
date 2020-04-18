package org.auth;

public class Bootstrap {

    private static void start() {
        RBACEngine.start();
    }

    public static void main(String[] args) {
        start();
    }
}
