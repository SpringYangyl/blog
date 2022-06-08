package com.yyl.entity;

import org.springframework.security.core.GrantedAuthority;

public class Permission implements GrantedAuthority {
    private final String role;

    public Permission(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {

        return "admin";
    }
}
