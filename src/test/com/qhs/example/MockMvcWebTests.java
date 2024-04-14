package com.qhs.example;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qhs.example.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MockMvcWebTests {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc(){
        //
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .build();
    }

    @Test
    public void homePage() throws Exception{
        //MockMvcRequestBuilders创建get请求
        mockMvc.perform(MockMvcRequestBuilders.get("/readingList"))
                //观察响应码
                .andExpect(MockMvcResultMatchers.status().isOk())
                //查看视图名称
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                //查看数据模型
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                //数据模型是否为空
                .andExpect(MockMvcResultMatchers.model().attribute("books",
                        Matchers.is(Matchers.empty())));


    }
    @Test
    public void postBook() throws Exception {
        //MockMvcRequestBuilders创建post请求
        mockMvc.perform(MockMvcRequestBuilders.post("/readingList")
                //请求类型设置为application/x-www-form-urlencoded，提交表单
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //
                .param("title", "BOOK TITLE")
                //
                .param("author", "BOOK AUTHOR")
                //
                .param("isbn", "1234567890")
                .param("description", "DESCRIPTION"))
                //
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/readingList"));

        //验证post请求保存图书的结果
        Book expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setReader("craig");
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("DESCRIPTION");

        //模拟发送get请求获取book
        mockMvc.perform(MockMvcRequestBuilders.get("/readingList"))
                //观察响应码
                .andExpect(MockMvcResultMatchers.status().isOk())
                //查看视图名称
                .andExpect(MockMvcResultMatchers.view().name("readingList"))
                //查看数据模型
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(1)))
                //数据模型是否为期望的Book对象
                .andExpect(MockMvcResultMatchers.model().attribute("books",
                        Matchers.contains(Matchers.samePropertyValuesAs(expectedBook))));


    }
}
