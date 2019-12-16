package com.example.elastictest.security

import org.springframework.security.core.userdetails.UserDetails

interface AuthenticationFacadeInterface {
    UserDetails getAuthentication();
}
