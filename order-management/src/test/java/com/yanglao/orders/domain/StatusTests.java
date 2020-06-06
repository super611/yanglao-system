package com.yanglao.orders.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Status 的单元测试")
public class StatusTests {

    @Test
    @DisplayName("如果当前状态处于CREATED，那么它只能变为PAID")
    void test_status_all() {
        Stream.of(Status.CREATED,Status.PAID,Status.CLOSED).forEach(
                status -> assertDoesNotThrow(() -> Status.CREATED.changeTo(status))
        );

        Stream.of(Status.CREATED,Status.PAID,Status.CLOSED).forEach(
                status -> assertThrows(OrdersStatusException.class,() -> Status.CLOSED.changeTo(status))
        );

        Stream.of(Status.CREATED,Status.PAID).forEach(
                status -> assertThrows(OrdersStatusException.class,() -> Status.PAID.changeTo(status))
        );
    }
}
