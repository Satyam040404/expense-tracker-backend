package com.satyam.expensetracker.security;

public class CurrentUserHolder {

    private static final ThreadLocal<String> currentUser =
            new ThreadLocal<>();

    public static void setEmail(String email) {
        currentUser.set(email);
    }

    public static String getEmail() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}