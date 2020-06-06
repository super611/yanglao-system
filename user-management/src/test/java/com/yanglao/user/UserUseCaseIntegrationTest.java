package com.yanglao.user;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@DisplayName("用户管理服务集成测试")
public class UserUseCaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private static String id = "";

    @Test
    @Order(1)
    @DisplayName("添加用户信息")
    void test_add_user() throws Exception {
        String json = "{\"password\": \"dengz\", \"name\": \"邓志辉\", \"phone\": \"15822828380\",\"address\": \"重庆市\"}";
        MvcResult result = mockMvc.perform(
                post("/api/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        id = result.getResponse().getContentAsString();
        assertNotNull(id);
    }

    @Test
    @Order(2)
    @DisplayName("非法创建用户信息")
    void test_add_invalid_user() throws Exception {
        String json = "{\"password\": \"dengz\", \"name\": \"\", \"phone\": \"15822828380\",\"address\": \"重庆市\"}";
        mockMvc.perform(
                post("/api/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(3)
    @DisplayName("按ID查找用户")
    void test_get_user() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(handler().methodName("getUser"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(4)
    @DisplayName("测试ID不合法的查询用户")
    void test_get_invalid_user() throws Exception {
        mockMvc.perform(get("/api/users/0"))
                .andExpect(handler().methodName("getUser"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    @DisplayName("修改密码")
    void test_modify_password() throws Exception {
        mockMvc.perform(put("/api/users/modify/"+id+"/dengzhi"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("修改密码不合法-用户ID号码小于1")
    void test_modify_password_less_than_one() throws Exception {
        mockMvc.perform(put("/api/users/modify/-1/dengzhi"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
      }

    @Test
    @Order(7)
    @DisplayName("修改密码不合法-用户ID号码大于1且不存在")
    void test_modify_password_less_than_100() throws Exception {
        mockMvc.perform(put("/api/users/modify/100/dengzhi"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}
