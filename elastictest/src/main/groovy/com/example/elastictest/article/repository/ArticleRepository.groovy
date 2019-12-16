package com.example.elastictest.article.repository

import com.example.elastictest.article.model.Article
import com.example.elastictest.article.model.Author
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository extends ElasticsearchRepository<Article, String> {
    Page<Article> findByAuthorsName(Author name, Pageable pageable)

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<Article> findByAuthorsNameUsingCustomQuery(Author name, Pageable pageable)
}
