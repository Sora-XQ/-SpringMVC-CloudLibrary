package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecordMapper {
    //新增借阅记录
    Integer addRecord(Record record);

    //查询借阅记录
    Page<Record> searchRecord(Record record);
}
