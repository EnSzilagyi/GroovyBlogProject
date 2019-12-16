package com.example.elastictest.security.dto

class JwtError {
    Boolean success
    String message

    JwtError(Boolean success, String message) {
        this.success = success
        this.message = message
    }
}
