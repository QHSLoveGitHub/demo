package com.qhs.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.qhs.example.entity.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//静态导入
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request. MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SpringSecurityTest {
    //
    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc(){
        //
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
                .apply(springSecurity())
                .build();
    }

    /**
     不需要用户验证
     **/
    @Test
    public void homePage_unauthenticatedUser() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","http://localhost/login"));
    }

    /**
     使用用户验证
     **/
    @Test
    @WithUserDetails("test")
    public void homePage_authenticatedUser() throws Exception{

        Reader expectedReader = new Reader();
        expectedReader.setUsername("test");
        expectedReader.setPassword("123456");
        expectedReader.setFullname("Craig Walls");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attribute("reader",samePropertyValuesAs(expectedReader)))
                .andExpect(model().attribute("books", hasSize(0)));

    }

}
