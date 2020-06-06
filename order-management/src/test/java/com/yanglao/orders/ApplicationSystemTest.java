package com.yanglao.orders;

import org.junit.jupiter.api.*;
import org.omg.CORBA.Object;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@DisplayName("订单管理系统的系统测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationSystemTest {
    static RestTemplate restTemplate;
    private static String orderid="4ac7afcd-09cd-4176-b4e5-a9b1bfafed50";
    private static String roomid="2c569360-43e2-4d9c-92e3-6423f50c25ef";
    //整个测试类的生命周期里只初始化一次
    @BeforeAll
    static void initAll() {
        restTemplate = new RestTemplate();
    }

    @Test
    @DisplayName("创建合法的新的订单，对应-创建新的订单-用例①")
    @Order(1)
    void test_order_add() {
        String json = "{\"userid\":\"1\",\"username\":\"邓志辉\",\"roomid\":\"fe52c336-9684-4df9-94fc-48fb006aa2a6\",\"term\":\"2\",\"sum\":\"200\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, header);
        orderid = restTemplate.postForObject("http://localhost:61000/api/orders", request, String.class);

        System.out.println("系统测试里面创建订单后的号码："+orderid);
    }

    @Test
    @DisplayName("创建不合法的订单-用户ID为空，对应-创建新的订单-用例②")
    @Order(2)
    void test_order_add_null_userid() {
        String json = "{\"userid\":\"\",\"username\":\"邓志辉\",\"roomid\":"+roomid+",\"term\":\"2\",\"sum\":\"200\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, header);
        Assertions.assertThrows(Exception.class, () -> restTemplate.postForObject("http://localhost:61000/api/orders", request, String.class));
    }

    @Test
    @DisplayName("创建不合法的订单-用户ID号码不合法，对应-创建新的订单-用例③")
    @Order(3)
    void test_order_add_invalid_userid() {
        String json = "{\"userid\":\"-1\",\"username\":\"邓志辉\",\"roomid\":"+roomid+",\"term\":\"2\",\"sum\":\"200\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, header);
        Assertions.assertThrows(Exception.class, () -> restTemplate.postForObject("http://localhost:61000/api/orders", request, String.class));
    }

    @Test
    @DisplayName("创建不合法的订单-用户的姓名为空，对应-创建新的订单-用例④")
    @Order(4)
    void test_order_empty_username() {
        String json = "{\"userid\":\"1\",\"username\":\"\",\"roomid\":"+roomid+",\"term\":\"2\",\"sum\":\"200\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, header);
        Assertions.assertThrows(Exception.class, () -> restTemplate.postForObject("http://localhost:61000/api/orders", request, String.class));
    }

    @Test
    @DisplayName("创建不合法的订单-房间的ID号码不是UUID类型，对应-创建新的订单-用例⑤")
    @Order(5)
    void test_order_add_invalide_roomid() {
        String json = "{\"userid\":\"1\",\"username\":\"邓志辉\",\"roomid\":\"123456789\",\"term\":\"2\",\"sum\":\"200\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, header);
        Assertions.assertThrows(Exception.class, () -> restTemplate.postForObject("http://localhost:61000/api/orders", request, String.class));
    }

    @Test
    @DisplayName("创建不合法的订单-租用房间时长小于1个月，对应-创建新的订单-用例⑥")
    @Order(6)
    void test_order_add_less_than_one_month() {
        String json = "{\"userid\":\"1\",\"username\":\"邓志辉\",\"roomid\":"+roomid+",\"term\":\"0\",\"sum\":\"0\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, header);
        Assertions.assertThrows(Exception.class, () -> restTemplate.postForObject("http://localhost:61000/api/orders", request, String.class));
    }

    @Test
    @DisplayName("合法支付订单，对应-支付订单-用例①")
    @Order(7)
    void test_order_paid() {
        String url = "http://localhost:61000/api/orders/paid/"+orderid;
        ResponseEntity<java.lang.Object> responseEntity= restTemplate.exchange(url, HttpMethod.PUT,null, java.lang.Object.class);
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("合法关闭订单，对应-关闭订单-用例①")
    @Order(8)
    void test_order_close() {
        String url = "http://localhost:61000/api/orders/close/" + orderid;
        //Assertions.assertDoesNotThrow(() -> restTemplate.put(url, String.class));
        ResponseEntity<Object> responseEntity= restTemplate.exchange(url, HttpMethod.PUT,null, (Class<Object>) null);
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("查询订单状态，对应-查询订单状态-用例①")
    @Order(9)
    void test_order_status() {
        String url = "http://localhost:61000/api/orders/"+orderid+"/status";
        //Assertions.assertEquals("CLOSED", restTemplate.getForObject(url,String.class));
        ResponseEntity<String> responseEntity= restTemplate.exchange(url, HttpMethod.GET,null, String.class);
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }
}