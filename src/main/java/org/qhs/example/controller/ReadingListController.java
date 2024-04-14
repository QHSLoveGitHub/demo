package org.qhs.example.controller;

import org.qhs.example.config.AmazonProperties;
import org.qhs.example.dao.ReadingListRepository;
import org.qhs.example.entity.Book;
import org.qhs.example.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
//@ConfigurationProperties(prefix="amazon")
public class ReadingListController {

    //注入dao层接口:这里才有一个巧妙的方法
    private ReadingListRepository repository;

//    @Autowired
    private AmazonProperties amazonProperties;

    /**
     * 读取配置字段
    private String associateId;


    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }
     **/

    //接口没有声明bean，在controller里注入
    @Autowired
    public  ReadingListController(
            ReadingListRepository repository,
            AmazonProperties amazonProperties){
        this.repository = repository;
        this.amazonProperties = amazonProperties;
    }

//    @RequestMapping(value = "/{reader}",method = RequestMethod.GET)
//    public String readerBooks(@PathVariable("reader") String reader, Model model){
//        List<Book> books = repository.findByReader(reader);
//        //将数据渲染到Model
//        if(books != null && !books.isEmpty()){
//            model.addAttribute("books", books);
//            model.addAttribute("reader",reader);
//        }
//        //model已经渲染到页面
//        return "readingList";
//    }
    /**根据读者获取阅读列表,使用security用户**/
    @RequestMapping(method = RequestMethod.GET)
    public String readerBooks(Reader reader, Model model){
        List<Book> books = repository.findByReader(reader.getUsername());
        //将数据渲染到Model
        if(books != null && !books.isEmpty()){
            model.addAttribute("books", books);
            model.addAttribute("reader",reader);
            //向页面传入amazonID
            model.addAttribute("amazonID", amazonProperties.getAssociateId());
        }
        //model已经渲染到页面
        return "readingList";
    }

    /**向读者的阅读列表下面添加一本书**/
//    @RequestMapping(value = "/{reader}",method = RequestMethod.POST)
//    public String addToReadingList(@PathVariable("reader") String reader,Book book){
//        //
//        book.setReader(reader);
//        repository.save(book);
//        //将页面重定向到阅读列表
//        return "redirect:/{reader}";
//    }
    @RequestMapping(method=RequestMethod.POST)
    public String addToReadingList(Reader reader, Book book) {
        book.setReader(reader.getUsername());
        repository.save(book);
        return "redirect:/";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }
}
