package com.example.elastictest.security.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class JwtSignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    String username


    @NotBlank
    @Size(min = 6, max = 20)
    String password;

    JwtSignUpRequest(){

    }

    JwtSignUpRequest(String username, String password) {
        this.username = username
        this.password = password
    }
}
