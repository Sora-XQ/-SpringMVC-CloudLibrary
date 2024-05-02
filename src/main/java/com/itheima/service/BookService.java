package com.itheima.service;


import com.github.pagehelper.Page;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import org.apache.ibatis.plugin.Interceptor;

public interface BookService {
    PageResult selectNewBooks(Integer pageNum,Integer pageSize);

    //根据id查图书信息
    Book findById(String id);

    //借阅图书
    Integer borrowBook(Book book);

    //分页查询
    PageResult search(Book book, Integer pageNum, Integer pageSize);

    //新增图书
    Integer addBook(Book book);

    //编辑图书
    Integer editBook(Book book);

    //查询当前借阅的图书
    PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize);

    //归还图书
    boolean returnBook(String id, User user);

    //归还确认
    Integer returnConfirm(String id);
}
