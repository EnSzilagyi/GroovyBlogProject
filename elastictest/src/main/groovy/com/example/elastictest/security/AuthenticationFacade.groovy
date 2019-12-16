package com.example.elastictest.security

import com.example.elastictest.security.dto.JwtUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade implements AuthenticationFacadeInterface{

    @Autowired
    JwtAuthenticationFacade() {
    }


    @Override
    JwtUser getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() as JwtUser
    }
}
