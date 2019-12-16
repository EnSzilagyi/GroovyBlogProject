package com.example.elastictest.user.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User Doesn't Exist")
class UserNotFoundException extends Exception {
}
