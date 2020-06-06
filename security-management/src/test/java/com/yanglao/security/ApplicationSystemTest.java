package com.yanglao.security;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@DisplayName("安全认证管理系统的系统测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationSystemTest {

    private static RestTemplate restTemplate;
    private String id;

    //整个测试类的生命周期里只初始化一次
    @BeforeAll
    static void initAll(){
        restTemplate=new RestTemplate();
    }

    @Test
    @DisplayName("合法输入密码，姓名，手机号码，地址完成用户注册，对应-注册-用例①")
    @Order(1)
    void test_security_register(){
        String json="{\"password\":\"dengz\",\"name\":\"邓志辉\",\"phone\":\"15822828380\",\"address\":\"重庆市\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,headers);
        id =restTemplate.postForObject("http://localhost:54000/api/security/register",request,String.class);
    }

    @Test
    @DisplayName("合法的用户名和密码实现登录，对应-登录-用例①")
    @Order(2)
    void test_security_login(){
        String url="http://localhost:54000/api/security/login/1/dengz";
        restTemplate.getForObject(url,String.class);
    }
    @Test
    @DisplayName("不合法的登录-用户名不存在，对应-登录-用例②")
    @Order(3)
    void test_security_login_invalid_username(){
        String url="http://localhost:54000/api/security/login/0/dengz";
        Assertions.assertThrows(Exception.class,()->restTemplate.getForObject(url,String.class));
    }
    @Test
    @DisplayName("不合法的登录-密码错误，对应-登录-用例③")
    @Order(4)
    void test_security_register_error_password(){
        String url="http://localhost:54000/api/security/login/1/deng";
        Assertions.assertThrows(Exception.class,()->restTemplate.getForObject(url,String.class));
    }


}
