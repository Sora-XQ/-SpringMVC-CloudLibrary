package com.itheima.domain;

import lombok.Data;

@Data
public class Record {
    private Integer id;
    private String bookName;
    private String bookIsbn;
    private String borrower;
    private String borrowTime;
    private String remandTime;
}
