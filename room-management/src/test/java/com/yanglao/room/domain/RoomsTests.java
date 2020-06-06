package com.yanglao.room.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//使用Mockito.mock（）测试
@DisplayName("Rooms的单元测试")
public class RoomsTests {

    @Test
    @DisplayName("测试转置为空闲的房间")
    void test_empty(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        room.empty();
        Assertions.assertEquals(Status.EMPTY, room.status());
    }

    @Test
    @DisplayName("测试预约房间")
    void test_reserve(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        room.empty();
        room.reserve(1);
        Assertions.assertEquals(Status.RESERVE, room.status());
    }
    @Test
    @DisplayName("测试不合法预约房间-测试不合法预约房间-租用房间少于一个月")
    void test_invalid_reserve(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        room.empty();
        Assertions.assertThrows(RoomsException.class,()->room.reserve(0));
    }
    @Test
    @DisplayName("测试租用房间")
    void test_leased(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        room.empty();
        room.reserve(1);
        room.leased();
        Assertions.assertEquals(Status.LEASED, room.status());
    }
    @Test
    @DisplayName("测试暂停房间")
    void test_remove(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        room.empty();
        room.remove();
        Assertions.assertEquals(Status.REMOVED, room.status());
    }

    @Test
    @DisplayName("测试查询房间的状态-为预约状态已过期")
    void test_get_room_status_reserve_expired(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        // LocalDateTime reserveLocaltime=Mockito.mock(LocalDateTime.class);
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        Assertions.assertEquals(Status.CREATED, room.status());
        room.empty();
        room.reserve(1);
        room.changeDeadlineReserve(LocalDateTime.now().minus(8, ChronoUnit.DAYS));
       // Mockito.when(reserveLocaltime.isBefore(LocalDateTime.now().plus(8, ChronoUnit.HOURS))
        Assertions.assertEquals(Status.EMPTY, room.status());
    }

    @Test
    @DisplayName("测试查询房间的状态-为租用状态已过期")
    void test_get_room_status_leased_expired(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);
        Assertions.assertEquals(Status.CREATED, room.status());
        room.empty();
        room.reserve(1);
        room.leased();
        Mockito.when(deadline.isExpired()).thenReturn(true);
        Assertions.assertEquals(Status.EMPTY, room.status());
    }

    @Test
    @DisplayName("测试改变预约截止日期")
    void test_get_room_changeDeadlineReserve(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        LocalDateTime reserveLocaltime=LocalDateTime.now().plusHours(8).plusMinutes(30);
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,deadline, reserveLocaltime);

        room.changeDeadlineReserve(reserveLocaltime.plusDays(1));

        Assertions.assertEquals(reserveLocaltime.plusDays(1), room.makeRoomsDTO().getDeadlinereserve());
    }



}
