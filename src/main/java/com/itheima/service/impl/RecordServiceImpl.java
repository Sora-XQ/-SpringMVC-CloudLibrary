package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.mapper.RecordMapper;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    /**
     * 查询借阅记录
     * @param record 借阅记录的查询条件
     * @param user 当前登录的用户
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult searchRecord(Record record, User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //如果不是管理员，则查询条件中的借阅人设置为当前用户
        if (!"ADMIN".equals(user.getRole())){
            record.setBorrower(user.getName());
        }
        Page<Record> page = recordMapper.searchRecord(record);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
