package com.kidsnara.library.constant;

public enum Role implements EnumModel{
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role_user;

    Role(String role_user){
        this.role_user = role_user;
    }

    @Override
    public String getKey(){
        return name();
    }

    @Override
    public String getValue(){
        return role_user;
    }

}
