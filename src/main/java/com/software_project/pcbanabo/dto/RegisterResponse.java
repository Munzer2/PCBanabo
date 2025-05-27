// src/main/java/com/software_project/pcbanabo/dto/RegisterResponse.java
package com.software_project.pcbanabo.dto;

public class RegisterResponse {
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
