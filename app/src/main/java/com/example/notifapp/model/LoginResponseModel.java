package com.example.notifapp.model;

public class LoginResponseModel {

    private String status;
    private String message;
    private String name;
    private String email;
    private String id;

    // Default constructor
    public LoginResponseModel() {
    }

    // Parameterized constructor
    public LoginResponseModel(String status, String message, String name, String email, String id) {
        this.status = status;
        this.message = message;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "LoginResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}