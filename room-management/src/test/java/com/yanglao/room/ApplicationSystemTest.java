package com.yanglao.room;

import org.junit.jupiter.api.*;
import org.omg.CORBA.Object;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@DisplayName("客房管理系统的系统测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationSystemTest {
    private static RestTemplate restTemplate;
    private static String roomid;

    //整个测试类的生命周期里只初始化一次
    @BeforeAll
    static void initAll(){
        restTemplate=new RestTemplate();
    }

    @Test
    @DisplayName("创建合法的新的房间，对应-创建新的房间-用例①")
    @Order(1)
    void test_room_add(){
        String json="{\"room\":\"508\",\"detail\":\"房间带有一张大床和浴室\",\"price\":\"200\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        roomid =restTemplate.postForObject("http://localhost:50000/api/rooms",request,String.class);

        System.out.println("roomid:++++++++"+roomid);
    }

    @Test
    @DisplayName("创建不合法的房间-房间号码的字符串长度为0，对应-创建新的房间-用例②")
    @Order(2)
    void test_room_add_null_roomid(){
        String json="{\"room\":\"\",\"detail\":\"房间带有一张大床和浴室\",\"price\":\"100\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->restTemplate.postForObject("http://localhost:50000/api/rooms",request,String.class));
    }

    @Test
    @DisplayName("创建不合法的房间-房间为空字符串，对应-创建新的房间-用例③")
    @Order(3)
    void test_room_add_empty_roomid(){
        String json="{\"room\":\"   \",\"detail\":\"房间带有一张大床和浴室\",\"price\":\"100\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->restTemplate.postForObject("http://localhost:50000/api/rooms",request,String.class));
    }

    @Test
    @DisplayName("创建不合法的房间-描述房间信息的字符串长度小于10个字符串，对应-创建新的房间-用例④")
    @Order(4)
    void test_room_add_(){
        String json="{\"room\":\"508\",\"detail\":\"房间中带有一张大床\",\"price\":\"100\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->restTemplate.postForObject("http://localhost:50000/api/rooms",request,String.class));
    }

    @Test
    @DisplayName("创建不合法的房间-描述房间信息的字符串长度大于10个字符串，对应-创建新的房间-用例⑤")
    @Order(5)
    void test_room_add_detail_more_than_10(){
        String json="{\"room\":\"508\",\"detail\":\"           \",\"price\":\"100\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->restTemplate.postForObject("http://localhost:50000/api/rooms",request,String.class));
    }

    @Test
    @DisplayName("创建不合法的房间-房间价格小于100元，对应-创建新的房间-用例⑥")
    @Order(6)
    void test_room_add_price_less_than_100(){
        String json="{\"room\":\"508\",\"detail\":\"房间带有一张大床和浴室\",\"price\":\"99\"}";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON) ;
        HttpEntity<String> request = new HttpEntity<>(json,header);
        Assertions.assertThrows(Exception.class,()->restTemplate.postForObject("http://localhost:50000/api/rooms",request,String.class));
    }

    @Test
    @DisplayName("合法的转置为空闲房间，对应-房间转置为空闲-用例①")
    @Order(7)
    void test_room_empty(){
       ResponseEntity<Object> responseEntity= restTemplate.exchange("http://localhost:50000/api/rooms/empty/"+roomid, HttpMethod.PUT,null, (Class<Object>) null);
       Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("合法预约房间，对应-预约房间-用例①")
    @Order(8)
    void test_room_reserve(){
        ResponseEntity<Object> responseEntity= restTemplate.exchange("http://localhost:50000/api/rooms/reserve/"+roomid+"/1", HttpMethod.PUT,null, (Class<Object>) null);
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("合法租用房间，对应-租用房间-用例①")
    @Order(9)
    void test_room_leased(){
        ResponseEntity<Object> responseEntity= restTemplate.exchange("http://localhost:50000/api/rooms/leased/"+roomid, HttpMethod.PUT,null, (Class<Object>) null);
        Assertions.assertEquals(200,responseEntity.getStatusCode().value());
    }

    @Test
    @DisplayName("不合法暂停房间-房间状态为已租用状态，对应-暂停房间-用例④")
    @Order(10)
    void test_room_remove(){
        String url="http://localhost:50000/api/rooms/removed/"+roomid;
        Assertions.assertThrows(Exception.class,()->restTemplate.put(url,null));
      }

    //其余类似
}
