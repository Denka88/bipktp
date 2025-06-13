package com.denka88.bipktp.model;

import lombok.Getter;

@Getter
public enum Role {

    ADMIN("Администратор"),
    TEACHER("Преподаватель");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

}
