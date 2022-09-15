package com.kidsnara.library.dto;

import lombok.Getter;

@Getter
public class UserCreateRequest {

    private String name;
    private String email;

    public UserCreateRequest(){}

    public UserCreateRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
