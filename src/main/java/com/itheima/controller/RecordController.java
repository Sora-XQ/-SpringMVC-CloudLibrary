package com.itheima.controller;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * 查询借阅记录
     * @param record 借阅记录的查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     */
    @RequestMapping("/searchRecords")
    public ModelAndView searchRecords(Record record, Integer pageNum, Integer pageSize, HttpServletRequest request){
        if (pageNum == null){
            pageNum=1;
        }
        if (pageSize==null){
            pageSize = 1;
        }
        User user = (User)request.getSession().getAttribute("USER_SESSION");
        PageResult pageResult = recordService.searchRecord(record,user,pageNum,pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("record");
        modelAndView.addObject("pageResult",pageResult);
        modelAndView.addObject("search",record);
        modelAndView.addObject("pageNum",pageNum);
        modelAndView.addObject("gourl",request.getRequestURI());
        return modelAndView;
    }
}
