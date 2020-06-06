package com.yanglao.orders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * 值对象
 */
@Embeddable
public class OrdersId implements Serializable {
    @Column(name = "id")
    private String value;

    public OrdersId() {
        this.value = UUID.randomUUID().toString();
    }

    public static OrdersId of(String id) {
        OrdersId ordersId = new OrdersId();
        ordersId.value = UUID.fromString(id).toString();
        return ordersId;
    }

    /**
     * 此处没有使用 getValue
     * 也没有使用 Lombok
     */
    public String value() {
        return this.value;
    }

}
