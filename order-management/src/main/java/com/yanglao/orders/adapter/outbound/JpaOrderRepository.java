package com.yanglao.orders.adapter.outbound;

import com.yanglao.orders.application.port.outbound.OrderRepository;
import com.yanglao.orders.domain.Orders;
import com.yanglao.orders.domain.OrdersId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

interface JpaOrderRepository extends OrderRepository, JpaRepository<Orders, OrdersId> {
    @Override
    default void add(Orders orders) {
        this.save(orders);
    }

    @Override
    default void update(Orders orders) {
        this.save(orders);
    }

    @Override
    default List<Orders> queryAll() {
        return this.findAll();
    }

    @Override
    default Orders queryById(OrdersId id) {
        return this.findById(id).orElse(null);
    }
}
