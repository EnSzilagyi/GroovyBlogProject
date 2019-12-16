package com.example.elastictest.article.service

import com.example.elastictest.article.exception.ArticleNotFoundException
import com.example.elastictest.article.model.Article
import com.example.elastictest.article.model.Author
import com.example.elastictest.article.repository.ArticleRepository
import com.example.elastictest.security.AuthenticationFacadeInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository
    private final AuthenticationFacadeInterface authenticationFacade


    @Autowired
    ArticleServiceImpl(ArticleRepository articleRepository, AuthenticationFacadeInterface authenticationFacade) {
        this.articleRepository = articleRepository
        this.authenticationFacade = authenticationFacade
    }

    @Override
    Article save(Article article) {
        Author author = new Author(authenticationFacade.getAuthentication().username)
        List<Author> authors = new ArrayList<>()
        println(author.name)
        authors.push(author)
        article.setAuthors(authors)
        articleRepository.save(article)
        return article
    }

    Article updateBlog(Article article) {
        articleRepository.save(article)
    }

    @Override
    Article findOne(String id) throws ArticleNotFoundException {
        return articleRepository.findById(id).orElseThrow(ArticleNotFoundException.&new)
    }


    @Override
    Iterable<Article> findAll() {
        return articleRepository.findAll()
    }

    Page<Article> findByLoggedInUser() {
        return findByAuthorNameUsingCustomQuery(authenticationFacade.getAuthentication().username, PageRequest.of(0, 5))
    }


    @Override
    Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable) {
        Author author = new Author()
        author.name = name
        return articleRepository.findByAuthorsNameUsingCustomQuery(author, pageable)
    }

    @Override
    Page<Article> findByFilteredTagQuery(String tag, Pageable pageable) {
        return articleRepository.findByFilteredTagQuery(tag, pageable)
    }

    @Override
    Page<Article> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable) {
        return articleRepository.findByAuthorsNameAndFilteredTagQuery(name, tag, pageable)
    }

    @Override
    long count() {
        return articleRepository.count()
    }

    @Override
    void delete(String id) throws ArticleNotFoundException {
        Article article
        article = articleRepository.findById(id).get()
        articleRepository.delete(article)
    }

    Boolean isUsersArticle(String id) {
        for (author in articleRepository.findById(id).get().getAuthors()) {
            if (authenticationFacade.getAuthentication().username == author.getName()) {
                return true
            }
        }
        return false
    }

}
