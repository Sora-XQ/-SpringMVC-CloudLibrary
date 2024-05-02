package com.itheima.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult implements Serializable {
    private long total;
    private List rows;
    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
}
