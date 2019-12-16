package com.example.elastictest.article.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Article doesn't exist")
class ArticleNotFoundException extends Exception {
}
