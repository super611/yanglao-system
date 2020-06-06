package com.yanglao.orders;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@DisplayName("订单管理服务集成测试")
public class OrdersUseCaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private static String orderid = "";

    @Test
    @Order(1)
    @DisplayName("测试创建新订单")
    void test_add_user() throws Exception {
        String deadline= LocalDateTime.now().plusHours(8).toString();
        String json="{\"userid\":\"1\",\"username\":\"邓志辉\",\"roomid\":\"2c569360-43e2-4d9c-92e3-6423f50c25ef\",\"term\":\"1\",\"sum\":\"100\"}";
        MvcResult result = mockMvc.perform(
                post("/api/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        orderid = result.getResponse().getContentAsString();
        assertNotNull(orderid);
    }
    @Test
    @Order(2)
    @DisplayName("测试非法创建新订单-姓名为空")
    void test_add_user_null_name() throws Exception {
        String deadline= LocalDateTime.now().plusHours(8).toString();
        String json="{\"userid\":\"1\",\"username\":\"\",\"roomid\":\"100\",\"term\":\"1\",\"sum\":\"100\",\"deadline\""+deadline+"}";
        mockMvc.perform(
                post("/api/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    @DisplayName("测试支付订单")
    void test_paid_order() throws Exception {
        mockMvc.perform(put("/api/orders/paid/"+ orderid))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("测试非法支付订单")
    void test_paid_invalid_user() throws Exception {
        mockMvc.perform(put("/api/orders/paid/965b0025-1176-47da-8a9c-04b45b097de"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
/*    @Test
    @Order(5)
    @DisplayName("测试非法支付订单-过期")
    void test_paid_order_expired() throws Exception {
        mockMvc.perform(put("/api/orders/paid/1b01bfbc-7163-4ffb-8891-79a05a6c1b7b"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }*/
    @Test
    @Order(6)
    @DisplayName("测试关闭订单")
    void test_get_user() throws Exception {
        mockMvc.perform(put("/api/orders/close/"+orderid))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(7)
    @DisplayName("测试非法创建新订单-该房间不是空闲状态")
    void test_add_user_no_empty() throws Exception {
        String deadline= LocalDateTime.now().plusHours(8).toString();
        String json="{\"userid\":\"1\",\"username\":\"邓志辉\",\"roomid\":\"fe52c336-9684-1dfa-94fc-48fb006aa299\",\"term\":\"1\",\"sum\":\"100\",\"deadline\""+deadline+"}";
        mockMvc.perform(
                post("/api/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(8)
    @DisplayName("测试非法创建新订单-客房服务未响应")
    void test_add_user_no_rooom_response() throws Exception {
        String deadline= LocalDateTime.now().plusHours(8).toString();
        String json="{\"userid\":\"1\",\"username\":\"邓志辉\",\"roomid\":\"\",\"term\":\"1\",\"sum\":\"100\",\"deadline\""+deadline+"}";
       mockMvc.perform(
                post("/api/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(9)
    @DisplayName("测试获取状态")
    void test_get_invalid_user() throws Exception {
        String response = mockMvc.perform(get("/api/orders/" + orderid + "/status"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("CLOSED", response);
    }

    @Test
    @Order(10)
    @DisplayName("获取所有订单")
    void test_modify_password() throws Exception {
        mockMvc.perform(get("/api/orders/all"))
                .andExpect(handler().methodName("getAllOrders"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
