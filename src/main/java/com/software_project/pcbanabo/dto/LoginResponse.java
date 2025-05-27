package com.software_project.pcbanabo.dto;

public class LoginResponse {
    private String token;
    private UserData user;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public UserData getUser() { return user; }
    public void setUser(UserData user) { this.user = user; }

    public static class UserData {
        private Integer id;
        private String userName;
        private String email;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}