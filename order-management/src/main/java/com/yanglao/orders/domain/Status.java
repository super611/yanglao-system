package com.yanglao.orders.domain;

/**
 * 如果状态管理过于复杂，可以使用状态模式进行重构
 */
public enum Status {
    CREATED,//创建订单
    PAID,//已支付
    CLOSED;//关闭

    public Status changeTo(Status newStatus) {
        // 如果当前状态处于 PAID，那么它只能变为CLOSED
        if (this == Status.PAID) {
            if (newStatus != Status.CLOSED) {
                throw new OrdersStatusException("当前只能修改为关闭状态");
            }
        }
        // 如果当前状态处于 ClOSED，那么它不可以变成其他状态
        if (this == Status.CLOSED) {
            throw new OrdersStatusException("不能修改已关闭的订单状态");
        }
        return newStatus;
    }
}
