package com.jh.cavymanage.elasticsearch.repositories;

import com.jh.cavymanage.elasticsearch.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book,String> {
    Book findByName(String name);
    List<Book> findByAuthor(String author);
    Book findBookById(String id);
}