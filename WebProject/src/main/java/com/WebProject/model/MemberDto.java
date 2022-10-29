package com.WebProject.model;

public class MemberDto {

    private String email;

    private String name;

    private String password;

    private long birth;

    private long number;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public long getBirth() {
        return birth;
    }

    public long getNumber() {
        return number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
