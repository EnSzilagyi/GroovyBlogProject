package com.example.elastictest.security.dto

class JwtAuthenticationRequest {
    String username
    String password

    JwtAuthenticationRequest(){
    }

    JwtAuthenticationRequest(username, password) {
        this.username = username
        this.password = password
    }

}
