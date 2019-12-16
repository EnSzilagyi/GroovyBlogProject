package com.example.elastictest.article.model

class Author {

    String name

    Author(){
    }

    Author(String name){
        this.name = name
    }

    @Override
    String toString() {
        return "Author{" + "name='" + name + '\'' + '}'
    }
}
