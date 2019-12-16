package com.example.elastictest.article.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.InnerField
import org.springframework.data.elasticsearch.annotations.MultiField

import static org.springframework.data.elasticsearch.annotations.FieldType.*

@Document(indexName = "blog", type = "article")
class Article {

    @Id
    String id;

    @MultiField(mainField = @Field(type = Text, fielddata = true), otherFields =  @InnerField(suffix = "verbatim", type = Keyword) )
    String title;

    @Field(type = Text)
    String story

    @Field(type = Nested, includeInParent = true)
    List<Author> authors;

    @Field(type = Keyword)
    String tags;

    @Override
    String toString() {
        return "Article{" + "id='" + id + '\'' + ", title='" + title + '\'' + ", authors=" + authors + ", tags=" + Arrays.toString(tags) + '}';
    }
}
