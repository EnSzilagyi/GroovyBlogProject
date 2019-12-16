package com.example.elastictest.security

import com.example.elastictest.security.dto.JwtUser
import com.example.elastictest.user.model.Authority
import com.example.elastictest.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import java.util.stream.Collectors

class JwtUserFactory {
    static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.isEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map({ authority -> new SimpleGrantedAuthority(authority.getValue()) })
                .collect(Collectors.toList());
    }

}
