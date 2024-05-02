package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.mapper.BookMapper;
import com.itheima.mapper.RecordMapper;
import com.itheima.service.BookService;
import com.itheima.service.RecordService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RecordService recordService;

    /**
     * 根据当前页码和每页需要展示的数据条数，查询最新上架的图书信息
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        //设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.selectNewBooks();
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id查图书信息
     * @param id 图书id
     */
    @Override
    public Book findById(String id) {
        return bookMapper.findByIdBook(id);
    }

    /**
     * 借阅图书
     * @param book 申请借阅的图书
     */
    @Override
    public Integer borrowBook(Book book) {
        //根据id查询出需要借阅的完整图书信息
        Book b = this.findById(book.getId() + "");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置当天为借阅时间
        book.setBorrowTime(dateFormat.format(new Date()));
        //设置所借阅的图书状态为借阅中
        book.setStatus("1");
        //将图书的价格设置在Book对象中
        book.setPrice(b.getPrice());
        //将图书的上架设置在book对象中
        book.setUploadTime(b.getUploadTime());
        return bookMapper.editBook(book);
    }

    /**
     * @param book     封装查询条件对象
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page = bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增图书
     * @param book 页面提交的新增图书信息
     * @return
     */
    @Override
    public Integer addBook(Book book) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        book.setUploadTime(dateFormat.format(new Date()));
        book.setIsbn(formatIsbn(book.getIsbn()));
        return bookMapper.addBook(book);
    }

    /**
     * 编辑图书信息
     * @param book 图书信息
     */
    @Override
    public Integer editBook(Book book) {
        book.setIsbn(formatIsbn(book.getIsbn()));
        return bookMapper.editBook(book);
    }

    /**
     * 查询用户当前借阅的图书
     * @param book     封装了查询条件的对象
     * @param user     当前登录对象
     * @param pageNum  当前页码
     * @param pageSize 每页显示数量
     */
    @Override
    public PageResult searchBorrowed(Book book, User user, Integer pageNum, Integer pageSize) {
        //设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum, pageSize);
        Page<Book> page;
        //将当前登录的用户放入查询条件中
        book.setBorrower(user.getName());
        //如果是管理员，查询当前用户借阅但未归还的图书和所有待确认归还的图书
        if ("ADMIN".equals(user.getRole())) {
            page = bookMapper.selectBorrowed(book);
        } else {
            //如果是普通用户，查询当前用户借阅但未归还的图书
            page = bookMapper.selectMyBorrowed(book);
        }
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 归还图书
     * @param id 归还图书的id
     * @param user 归还的人员 也就是当前图书的借阅者
     */
    @Override
    public boolean returnBook(String id, User user) {
        //根据图书id查询出图书的完整信息
        Book book = this.findById(id);
        //再次核验但钱登录人员和图书借阅者是不是同一个人
        boolean rb = book.getBorrower().equals(user.getName());
        //如果是同一个人，允许归还
        if (rb){
            book.setStatus("2");
            bookMapper.editBook(book);
        }
        return rb;
    }

    /**
     * 确认归还
     * @param id 待归还确认的图书id
     */
    @Override
    public Integer returnConfirm(String id) {

        Book book = this.findById(id);
        Record record = this.setRecord(book);
        book.setStatus("0");
        book.setBorrower("");
        book.setBorrowTime("");
        book.setReturnTime("");
        Integer count = bookMapper.editBook(book);
        if (count == 1){
            return recordService.addRecord(record);
        }
        return 0;
    }

    /**
     * 根据图书信息设置借阅记录的信息
     * @param book 借阅的图书信息
     * @return
     */
    private Record setRecord(Book book){
        Record record = new Record();
        record.setBookName(book.getName());
        record.setBookIsbn(book.getIsbn());
        record.setBorrower(book.getBorrower());
        record.setBorrowTime(book.getBorrowTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        record.setRemandTime(dateFormat.format(new Date()));
        return record;
    }

    /**
     * 格式化图书isbn
     */
    public String formatIsbn(String isbn) {
        return isbn.substring(0, 3) + "-" +
                isbn.substring(3, 4) + "-" +
                isbn.substring(4, 6) + "-" +
                isbn.substring(6, 12) + "-" +
                isbn.substring(12);
    }


}
