package com.yanglao.room;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@DisplayName("房间管理服务集成测试")
public class RoomsUseCaseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    private static String roomid = "";

    @Test
    @Order(1)
    @DisplayName("创建新的房间")
    void test_add_room() throws Exception {
        String json="{\"room\":\"508\",\"detail\":\"房间带有一张大床和浴室\",\"price\":\"100\"}";
        MvcResult result = mockMvc.perform(
                post("/api/rooms")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        roomid = result.getResponse().getContentAsString();
        assertNotNull(roomid);
    }
    @Test
    @Order(2)
    @DisplayName("不合法的创建新的房间")
    void test_add_invalid_room() throws Exception {
        String json = "{\"room\":\"\",\"detail\":\"房间带有一张大床和浴室\",\"price\":\"100\"}";
        mockMvc.perform(
                post("/api/rooms")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    @Order(3)
    @DisplayName("空闲房间")
    void test_empty_room() throws Exception {
        mockMvc.perform(put("/api/rooms/empty/"+ roomid))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("测试不合法空闲房间-改客房号码不存在")
    void test_no_exit_empty_room() throws Exception {
        mockMvc.perform(put("/api/rooms/empty/2f529361-43e2-4d9c-92e3-6423f50c05ef"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(5)
    @DisplayName("测试正常预约房间")
    void test_reserve_room() throws Exception {
        mockMvc.perform(put("/api/rooms/reserve/"+roomid+"/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    @Order(6)
    @DisplayName("不合法预约房间-房间号码不存在")
    void test_no_exit_reserve_room() throws Exception {
        mockMvc.perform(put("/api/rooms/reserve/2f529361-43e2-4d9c-92e3-6423f50c05ef/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /*@Test
    @Order(7)
    @DisplayName("")
    void test_leased_room_less_than_one() throws Exception {
        mockMvc.perform(put("/api/rooms/reserve/2f529361-43e2-4d9c-92e3-6423f50c05ef/0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }*/

    @Test
    @Order(7)
    @DisplayName("测试正常租用房间")
    void test_leased_room() throws Exception {
        mockMvc.perform(put("/api/rooms/leased/"+roomid))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    @DisplayName("测试不合法租用房间-房间号码不存在")
    void test_no_exit_leased_room() throws Exception {
        mockMvc.perform(put("/api/rooms/leased/2f529361-43e2-4d9c-92e3-6423f50c05ef"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(9)
    @DisplayName("测试正常暂停房间")
    void test_removed_room() throws Exception {
        mockMvc.perform(put("/api/rooms/removed/fe52c336-9684-4df9-94fc-48fb006aa2a6"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Order(10)
    @DisplayName("测试不合法暂停房间-房间号码不存在")
    void test_removed_invalid__room() throws Exception {
        mockMvc.perform(put("/api/rooms/removed/2f529361-43e2-4d9c-92e3-6423f50c05ef"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(11)
    @DisplayName("测试正常查询房间状态")
    void test_get_room_status() throws Exception {
        String response = mockMvc.perform(get("/api/rooms/" + roomid + "/status"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals("LEASED",response);
    }

    @Test
    @Order(12)
    @DisplayName("测试正常查询所有房间")
    void test_room_get_all() throws Exception {
        mockMvc.perform(get("/api/rooms/all"))
                .andExpect(handler().methodName("getAllRooms"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
