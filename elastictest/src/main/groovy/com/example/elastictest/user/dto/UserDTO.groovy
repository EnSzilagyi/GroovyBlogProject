package com.example.elastictest.user.dto

class UserDTO {
    def ud
    def username
    def password
    def enabled
    def lastPasswordResetDate

    UserDTO(ud, username, password, enabled, lastPasswordResetDate) {
        this.ud = ud
        this.username = username
        this.password = password
        this.enabled = enabled
        this.lastPasswordResetDate = lastPasswordResetDate
    }
}
