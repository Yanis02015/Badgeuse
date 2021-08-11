package com.msy.badgeuse.control;

public class Employees {
    private String identifier;
    private String firstName;
    private String lastName;
    private String role;

    public Employees(String identifier, String firstName, String lastName, String role) {
        this.setIdentifier(identifier);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setRole(role);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        // TODO : Configure controllers
        this.identifier = identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        // TODO : Configure controllers
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        // TODO : Configure controllers
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String fontion) {
        // TODO : Configure controllers
        this.role = fontion;
    }
}
