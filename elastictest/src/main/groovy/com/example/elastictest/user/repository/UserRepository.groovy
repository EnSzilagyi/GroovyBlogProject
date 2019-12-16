package com.example.elastictest.user.repository

import com.example.elastictest.user.model.User
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface UserRepository extends ElasticsearchRepository<User,String> {
    Optional<User> findByUsername(String Username)
    Boolean existsByUsername(String username)
}
