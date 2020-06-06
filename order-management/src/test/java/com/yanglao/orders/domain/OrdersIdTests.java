package com.yanglao.orders.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OrdersId的单元测试")
public class OrdersIdTests {

    @Test
    @DisplayName("测试房间的ID号码是否合法")
    void test_order_id_is_valid() {
        OrdersId roomsId = new OrdersId();
        Assertions.assertNotNull(OrdersId.of(roomsId.value()));
    }
}
