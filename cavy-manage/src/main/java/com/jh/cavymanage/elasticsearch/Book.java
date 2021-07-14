package com.jh.cavymanage.elasticsearch;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
@Data
@Document(indexName = "atguigu")
public class Book {
    private String id;
    private String name;
    private String author;
}
