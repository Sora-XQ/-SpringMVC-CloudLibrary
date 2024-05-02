package com.itheima.mapper;

import com.github.pagehelper.Page;
import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookMapper {
    //查询最新图书列表
    Page<Book> selectNewBooks();
    //根据id查询图书信息
    Book findByIdBook(String id);
    //编辑图书信息
    Integer editBook(Book book);
    //查询图书
    Page<Book> searchBooks(Book book);
    //新增图书
    Integer addBook(Book book);

    //查询借阅但未归还的图书和所有待确认归还的图书 管理员
    Page<Book> selectBorrowed(Book book);

    //查询借阅但未归还的图书 普通用户
    Page<Book> selectMyBorrowed(Book book);
}
