package com.software_project.pcbanabo.dto;

public class UserDto {
    private Integer id;
    private String userName;
    private String email;
    private String userType;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
