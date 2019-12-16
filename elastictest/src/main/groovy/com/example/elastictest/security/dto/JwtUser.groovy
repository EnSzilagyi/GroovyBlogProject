package com.example.elastictest.security.dto


import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUser implements UserDetails {

    String id

    String username

    String password

    private Collection<? extends GrantedAuthority> authorities
    boolean enabled

    String lastPasswordResetDate

    JwtUser(String id,String username, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled, String lastPasswordResetDate) {
        this.id = id
        this.username = username
        this.password = password
        this.authorities = authorities
        this.enabled = enabled
        this.lastPasswordResetDate = lastPasswordResetDate
    }

    JwtUser() {
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}
