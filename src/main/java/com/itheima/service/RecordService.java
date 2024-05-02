package com.itheima.service;

import com.github.pagehelper.Page;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

public interface RecordService {
    //新镇借阅记录
    Integer addRecord(Record record);

    //查询借阅记录
    PageResult searchRecord(Record record, User user, Integer pageNum,Integer pageSize);
}
