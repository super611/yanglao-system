package com.yanglao.orders;

import com.yanglao.orders.application.port.outbound.OrderRepository;
import com.yanglao.orders.domain.Deadline;
import com.yanglao.orders.domain.Orders;
import com.yanglao.orders.domain.OrdersId;
import com.yanglao.orders.domain.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("订单管理服务数据库集成测试")
public class OrderRepositoryIntegrationTest {

    @Autowired
    OrderRepository orderRepository;

    private static String roomid="2c569360-43e2-4d9c-92e3-6423f50c25ef";
    private static String orderid;
    @Test
    void test_order_add() {
        Orders order = new Orders(1, "邓志辉",roomid,1, 100, new Deadline(LocalDateTime.now().plusHours(8)));
        orderRepository.add(order);
        List<Orders> list = orderRepository.queryAll();
        int size=list.size();
        Assertions.assertEquals(order.makeOrdersDTO().getUsername(), list.get(size-1).makeOrdersDTO().getUsername());
        orderid =order.makeOrdersDTO().getId();
    }

    @Test
    void test_update() {
        Orders users= orderRepository.queryById(OrdersId.of(orderid));
        Assertions.assertNotNull(users);
        users.paid();
        orderRepository.update(users);
        assertEquals(Status.PAID, orderRepository.queryById(OrdersId.of(orderid)).makeOrdersDTO().getStatus());

    }

}
