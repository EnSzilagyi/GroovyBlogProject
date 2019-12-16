package com.example.elastictest.article.controller

import com.example.elastictest.article.exception.ArticleNotFoundException
import com.example.elastictest.article.model.Article
import com.example.elastictest.article.service.ArticleServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = "/api/article")
@CrossOrigin(allowedHeaders = "*")
class ArticleController {
    ArticleServiceImpl articleService

    @Autowired
    ArticleController(ArticleServiceImpl articleService){
        this.articleService = articleService
    }

    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Article create(@RequestBody Article article) throws IOException {
        return articleService.save(article)
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    def getOneArticle(@PathVariable ("id") String id) throws IOException {
        articleService.findOne(id)
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @Transactional
    void updateBlog(@RequestBody Article article) throws ArticleNotFoundException {
        articleService.updateBlog(article)
    }


    @GetMapping(value = "/check/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    def isUsersArticle(@PathVariable ("id") String id) throws IOException {
        articleService.isUsersArticle(id)
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    def getArticles(){
        return articleService.findAll()
    }
    @GetMapping(value = "/author",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    def getAuthorsArticles(){
        return articleService.findByLoggedInUser()
    }

    @DeleteMapping(value = "/remove/{id}")
    def removeArticle(@PathVariable("id")String id){
        articleService.delete(id)
    }
}
