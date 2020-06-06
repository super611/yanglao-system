package com.yanglao.room.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RoomsId的单元测试")
public class RoomsIdTests {

    @Test
    @DisplayName("测试房间的ID号码是否合法")
    void test_room_id_is_valid() {
        RoomsId roomsId = new RoomsId();
        Assertions.assertNotNull(RoomsId.of(roomsId.value()));
        Assertions.assertThrows(RoomsException.class,
                ()->RoomsId.of("122121"));
    }
}
