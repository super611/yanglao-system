package com.yanglao.user;

import com.yanglao.user.adapter.inbound.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@DisplayName("用户信息管理系统的系统测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationSystemTest {

    private static RestTemplate restTemplate;
    private static int id;

    //整个测试类的生命周期里只初始化一次
    @BeforeAll
    static void initAll(){
        restTemplate=new RestTemplate();
    }

    @Test
    @DisplayName("合法的输入密码，姓名，手机号码，地址完成添加用户信息，对应-添加用户信息-用例①")
    @Order(1)
    void test_user_add(){
        String json="{\"password\":\"dengz\",\"name\":\"邓志辉\",\"phone\":\"15822828380\",\"address\":\"重庆市\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        id =Integer.parseInt(restTemplate.postForObject("http://localhost:52000/api/users",request,String.class));
        System.out.print("创建的ID号码："+id);
    }

    @Test
    @DisplayName("添加用户信息不合法-密码字符串长度小于4，对应-添加用户信息-用例②")
    @Order(2)
    void test_user_add_error_password(){
        String json="{\"password\":\"den\",\"name\":\"邓志辉\",\"phone\":\"15822828380\",\"address\":\"重庆市\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->Integer.parseInt(restTemplate.postForObject("http://localhost:52000/api/users",request,String.class)));
    }

    @Test
    @DisplayName("添加用户信息不合法-姓名为空，对应-添加用户信息-用例③")
    @Order(3)
    void test_user_add_empty_name(){
        String json="{\"password\":\"dengz\",\"name\":\" \",\"phone\":\"15822828380\",\"address\":\"重庆市\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->Integer.parseInt(restTemplate.postForObject("http://localhost:52000/api/users",request,String.class)));
    }

    @Test
    @DisplayName("添加用户信息不合法-手机号码为空，对应-添加用户信息-用例④")
    @Order(4)
    void test_user_add_empty_phone(){
        String json="{\"password\":\"dengz\",\"name\":\"邓志辉\",\"phone\":\" \",\"address\":\"重庆市\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->Integer.parseInt(restTemplate.postForObject("http://localhost:52000/api/users",request,String.class)));
    }

    @Test
    @DisplayName("添加用户信息不合法-地址为空，对应-添加用户信息-用例⑤")
    @Order(5)
    void test_user_add_empty_address(){
        String json="{\"password\":\"dengz\",\"name\":\"邓志辉\",\"phone\":\"15822828380\",\"address\":\" \"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->Integer.parseInt(restTemplate.postForObject("http://localhost:52000/api/users",request,String.class)));
    }

    @Test
    @DisplayName("合法修改用户密码，对应-修改密码-用例①")
    @Order(6)
    void test_user_modify_password(){
        String url= "http://localhost:52000/api/users/modify/"+id+"/dengzhihui";
        restTemplate.put(url,String.class);
    }
    @Test
    @DisplayName("不合法修改用户密码-用户名ID小于1，对应-修改密码-用例②")
    @Order(7)
    void test_user_modify_userid_length_less_than_one(){
        String url= "http://localhost:52000/api/users/modify/-1/dengz";
        Assertions.assertThrows(Exception.class,()->restTemplate.put(url,String.class));

    }

    @Test
    @DisplayName("不合法修改用户密码-用户ID大于0且不存在，对应-修改密码-用例③")
    @Order(8)
    void test_user_modify_userid_no_exit(){
        String url= "http://localhost:52000/api/users/modify/100/dengz";
        Assertions.assertThrows(Exception.class,()->restTemplate.put(url,String.class));
    }


    @Test
    @DisplayName("不合法修改用户密码-密码字符串长度小于4，对应-修改密码-用例④")
    @Order(9)
    void test_user_modify_password_length_less_than_four(){
        String url= "http://localhost:52000/api/users/modify/"+id+"/den";
        Assertions.assertThrows(Exception.class,()->restTemplate.put(url,String.class));

    }
    @Test
    @DisplayName("合法查询用户信息，对应-查询用户信息-用例①")
    @Order(10)
    void test_user_get_user(){
        String url="http://localhost:52000/api/users/"+id;
        Assertions.assertNotNull(restTemplate.getForObject(url, UserDTO.class));
    }
    @Test
    @DisplayName("不合法查询用户信息-用户ID号码小于1，对应-查询用户信息-用例②")
    @Order(11)
    void test_user_invalid_get_user(){
        String url="http://localhost:52000/api/users/0";
        Assertions.assertThrows(Exception.class,()->restTemplate.getForObject(url, UserDTO.class));
    }
}
