package com.example.elastictest.user.service


import com.example.elastictest.user.exception.UserNotFoundException
import com.example.elastictest.user.model.User
import com.example.elastictest.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.function.Supplier


@Service
class UserService {
    UserRepository userRepository

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository
    }
    User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException.&new)
    }

    String getUsername(){
        return authenticationFacade.getAuthentication()
    }

}
