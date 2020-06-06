package com.yanglao.orders.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("Orders的单元测试")
public class OrdersTests {

    private static String orderid;

    @Test
    @DisplayName("测试获取订单的状态")
    void test_order_status(){
        Deadline deadline = Mockito.mock(Deadline.class);
        System.out.print("下订单的日期为"+deadline.value());
        Orders order = new Orders(1, "邓志辉","2c569360-43e2-4d9c-92e3-6423f50c25ef",1, 100, deadline);
        Assertions.assertEquals(Status.CREATED, order.status());
        orderid=order.makeOrdersDTO().getId();
    }

    @Test
    @DisplayName("测试支付订单")
    void test_order_paid(){
        Deadline deadline = Mockito.mock(Deadline.class); // dummy, stub, mock, fake
        Orders order = new Orders(1, "邓志辉","2c569360-43e2-4d9c-92e3-6423f50c25ef",1, 100, deadline);
        order.paid();
        Assertions.assertEquals(Status.PAID, order.status());
    }

    @Test
    @DisplayName("测试关闭订单")
    void test_order_closed(){
        Deadline deadline = Mockito.mock(Deadline.class);
        Orders order = new Orders(1, "邓志辉","2c569360-43e2-4d9c-92e3-6423f50c25ef",1, 100, deadline);
        order.close();
        Assertions.assertEquals(Status.CLOSED, order.status());

    }
}
