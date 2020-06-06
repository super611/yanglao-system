package com.yanglao.orders.application.port.outbound;

import com.yanglao.orders.domain.Orders;
import com.yanglao.orders.domain.OrdersId;

import java.util.List;

public interface OrderRepository {
    List<Orders> queryAll();

    void add(Orders orders);

    void update(Orders orders);

    Orders queryById(OrdersId id);
}
