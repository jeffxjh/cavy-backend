package com.jh.cavy.search.elasticsearch.repositories;

import com.jh.cavy.search.elasticsearch.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book,String> {
    Book findByName(String name);
    List<Book> findByAuthor(String author);
    Book findBookById(String id);
}