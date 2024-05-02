package com.itheima.domain;

import lombok.Data;

@Data
public class Book {
    private Integer id;
    private String name;
    private String isbn;
    private String press;
    private String author;
    private Integer pagination;
    private Double price;
    private String uploadTime;
    private String status;
    private String borrower;
    private String borrowTime;
    private String returnTime;
}
