package com.yanglao.orders.application.port.inbound;

import com.yanglao.orders.adapter.inbound.OrdersDTO;
import java.util.List;

public interface OrdersUseCase {
    String createOrder(OrdersDTO order);
    void paidOrder(String id);
    void closeOrder(String id);
    List<OrdersDTO> getAllOrders();
    String statusOf(String id);
}
