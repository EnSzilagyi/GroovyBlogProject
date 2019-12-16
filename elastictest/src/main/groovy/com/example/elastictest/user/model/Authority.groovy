package com.example.elastictest.user.model

enum Authority {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    static final String USER = "USER";
    static final String ADMIN = "ADMIN";

    final String value;

    Authority(String value) {
        this.value = value;
    }



}
