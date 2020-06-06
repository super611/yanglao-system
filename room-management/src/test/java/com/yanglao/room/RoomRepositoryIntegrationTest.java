package com.yanglao.room;

import com.yanglao.room.application.port.outbound.RoomRepository;
import com.yanglao.room.domain.Deadline;
import com.yanglao.room.domain.Rooms;
import com.yanglao.room.domain.RoomsId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("客房管理服务数据库集成测试")
public class RoomRepositoryIntegrationTest {

    @Autowired
    RoomRepository roomRepository;

    private static String roomid;

    @Test
    void test_add() {
       /* new Deadline(LocalDateTime.now().plusHours(8))*/
        Rooms room = new Rooms("A508","房间带有一张大床和浴室", 100,null,LocalDateTime.now().plusHours(8));
        roomRepository.add(room);

        List<Rooms> list = roomRepository.queryAll();
        int size=list.size();
        assertEquals(room.makeRoomsDTO().getRoom(),list.get(0).makeRoomsDTO().getRoom());
        roomid=list.get(size-1).makeRoomsDTO().getId();
    }
    @Test
    void test_update() {
        Rooms rooms = roomRepository.queryById(RoomsId.of(roomid));
        Assertions.assertNotNull(rooms);

        LocalDateTime localDateTime=LocalDateTime.now().plusDays(1);
        Deadline deadline=new Deadline(LocalDateTime.now().plusHours(8).plusDays(1));
        rooms.changeDeadline(deadline);
        rooms.changeDeadlineReserve(localDateTime);

        roomRepository.update(rooms);
        Assertions.assertEquals(localDateTime.getMinute(),roomRepository.queryById(RoomsId.of(roomid)).makeRoomsDTO().getDeadlinereserve().getMinute());
    }

}
