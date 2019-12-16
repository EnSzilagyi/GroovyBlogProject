package com.example.elastictest.user.model


import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field

import static org.springframework.data.elasticsearch.annotations.FieldType.Text

@Document(indexName = "authentication", type = "user")
class User {
    @Id
    String id

    @Field(type = Text)
    String username

    @Field(type = Text)
    String password

    @Field(type = Text)
    boolean enabled

    String lastPasswordResetDate

    @Transient
    List<Authority> authorities = Arrays.asList(Authority.ROLE_ADMIN);

    User(){
    }

    User(String username, String password, boolean enabled, Date lastPasswordResetDate) {
        this.username = username
        this.password = password
        this.enabled = enabled
        this.lastPasswordResetDate = lastPasswordResetDate
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        User user = (User) o

        if (enabled != user.enabled) return false
        if (id != user.id) return false
        if (lastPasswordResetDate != user.lastPasswordResetDate) return false
        if (password != user.password) return false
        if (username != user.username) return false

        return true
    }

    int hashCode() {
        int result
        result = id.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + (enabled ? 1 : 0)
        result = 31 * result + lastPasswordResetDate.hashCode()
        return result
    }

    @Override
    String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", lastPasswordResetDate=" + lastPasswordResetDate +
                ", authorities=" + authorities +
                '}';
    }
}
