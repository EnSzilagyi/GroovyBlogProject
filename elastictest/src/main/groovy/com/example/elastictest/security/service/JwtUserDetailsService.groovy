package com.example.elastictest.security.service

import com.example.elastictest.security.JwtUserFactory
import com.example.elastictest.user.model.User
import com.example.elastictest.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService implements UserDetailsService {
    UserService userService

    @Autowired
    JwtUserDetailsService(UserService userService) {
        this.userService = userService
    }

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
           return JwtUserFactory.create(userService.getUserByUsername(username) as User)
        } catch (UsernameNotFoundException ignored) {
            throw new UsernameNotFoundException("No user found with name of " + username)
        }
    }

}
