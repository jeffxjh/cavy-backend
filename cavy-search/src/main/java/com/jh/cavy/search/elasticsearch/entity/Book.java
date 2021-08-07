package com.jh.cavy.search.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
@Data
@Document(indexName = "atguigu")
public class Book {
    private String id;
    private String name;
    private String author;
}
