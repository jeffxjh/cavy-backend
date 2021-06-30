package com.jh.cavybackend.elasticsearch.repositories;

import com.jh.cavybackend.elasticsearch.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book,String> {
    Book findByName(String name);
    List<Book> findByAuthor(String author);
    Book findBookById(String id);
}