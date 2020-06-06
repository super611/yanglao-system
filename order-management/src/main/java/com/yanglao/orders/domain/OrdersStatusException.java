package com.yanglao.orders.domain;

/**
 * 注意可见修饰符为包可见
 */
class OrdersStatusException extends OrdersException {
    public OrdersStatusException(String message) {
        super(message);
    }
}
