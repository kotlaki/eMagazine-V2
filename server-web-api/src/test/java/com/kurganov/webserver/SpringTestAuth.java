package com.kurganov.webserver;

import com.kurganov.webserver.aop.CartRecalculateAspect;
import com.kurganov.webserver.utils.ShoppingCart;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringTestAuth {

    @Autowired
    private MockMvc mockMvc;

    //проверяем валидность входа пользователя
    @Test
    public void logIn() throws Exception {
       mockMvc.perform(formLogin("/login").user("admin").password("123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    // как вытащить информацию о ролях пользователя?
    @Test
    public void securityAccessDeniedTest() throws Exception {
//        mockMvc.perform(get("http://localhost:8188/shop/add/0"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost:8188/login"));
        mockMvc.perform(formLogin("http://localhost:8188/shop/add/0").user("admin").password("123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8188/shop/add/0"));
    }

}
