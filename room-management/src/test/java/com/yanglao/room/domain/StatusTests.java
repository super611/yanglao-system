package com.yanglao.room.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Status 的单元测试")
public class StatusTests {

    @Test
    @DisplayName("已空闲状态才能转换为暂停状态")
    void test_status_all() {

        Stream.of(Status.CREATED,Status.EMPTY,Status.RESERVE, Status.LEASED,Status.REMOVED).forEach(
                status -> assertDoesNotThrow(() -> status.changeTo(Status.EMPTY))
        );

        // 如果当前状态不是 EMPTY， 那么不允许预约
        Stream.of(Status.CREATED,Status.RESERVE, Status.LEASED,Status.REMOVED).forEach(
                status -> assertThrows(RoomsStatusException.class,
                        () -> status.changeTo(Status.RESERVE))
        );

        // 如果当前状态不是 RESERVE， 那么不允许租用
        Stream.of(Status.CREATED,Status.EMPTY, Status.LEASED,Status.REMOVED).forEach(
                status -> assertThrows(RoomsStatusException.class,
                        () -> status.changeTo(Status.LEASED))
        );

        // 不允许改为创建状态
        Stream.of(Status.CREATED,Status.EMPTY,Status.RESERVE, Status.LEASED,Status.REMOVED).forEach(
                status -> assertThrows(RoomsStatusException.class,
                        () -> status.changeTo(Status.CREATED))
        );

        // //如果当前状态不是空闲状态，那么不允许停用
        Stream.of(Status.CREATED,Status.RESERVE, Status.LEASED,Status.REMOVED).forEach(
                status -> assertThrows(RoomsStatusException.class,
                        () -> status.changeTo(Status.REMOVED))
        );


    }
}
