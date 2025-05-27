// src/main/java/com/software_project/pcbanabo/dto/VerifyResponse.java
package com.software_project.pcbanabo.dto;

public class VerifyResponse {
    private String message;

    public VerifyResponse(String message) {
        this.message = message;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
