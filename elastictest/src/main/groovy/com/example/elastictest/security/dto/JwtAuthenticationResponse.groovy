package com.example.elastictest.security.dto


class JwtAuthenticationResponse {
    String token
    Long expiration

    JwtAuthenticationResponse(){
    }


    JwtAuthenticationResponse(String token, Long expiration) {
        this.token = token
        this.expiration = expiration
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        JwtAuthenticationResponse that = (JwtAuthenticationResponse) o

        if (expiration != that.expiration) return false
        if (token != that.token) return false

        return true
    }

    int hashCode() {
        int result
        result = (token != null ? token.hashCode() : 0)
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return "JwtAuthenticationResponse{" +
                "token='" + token + '\'' +
                ", expiration=" + expiration +
                '}';
    }
}
