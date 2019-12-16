package com.example.elastictest.article.service

import com.example.elastictest.article.model.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ArticleService {
    Article save(Article article);

    Article findOne(String id);

    Iterable<Article> findAll();

    Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable);

    Page<Article> findByFilteredTagQuery(String tag, Pageable pageable);

    Page<Article> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable);


    long count();

    void delete(String id)
}
