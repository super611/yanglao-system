package com.yanglao.orders.domain;

import com.yanglao.orders.adapter.inbound.OrdersDTO;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {

    @EmbeddedId
    private OrdersId id;//订单ID
    private int userid;//用户ID
    private String username;//用户名
    private String roomid;//客房id
    private int term;//租用时长（月）
    private double sum;//总的价格
    @Embedded
    private Deadline datetime;//下订单时间
    private Status status;//订单状态

    // JPA
    public Orders() {
    }

    // Builder， Factory 创建型模式
    public Orders(int userid, String username,String roomid,int term, double sum, Deadline datetime) {
        this.id = new OrdersId();
        this.userid = userid;
        this.username = username;

        this.roomid = roomid;
        this.term=term;
        this.sum = sum;

        this.datetime = datetime;
        this.status = Status.CREATED;
    }

    // 迎合显示用的
    public OrdersDTO makeOrdersDTO() {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(id.value());
        dto.setUserid(this.userid);
        dto.setUsername(this.username);
        dto.setRoomid(this.roomid);
        dto.setTerm(this.term);
        dto.setSum(this.sum);
        dto.setDatetime(this.datetime);
        dto.setStatus(this.status);
        return dto;
    }

    public void close() {
        status = status.changeTo(Status.CLOSED);
    }

    public void paid() {
        status = status.changeTo(Status.PAID);
    }

    public Status status() {
        return status;
    }

   /* public void changeStatus(Status newStatus) {
        this.status = this.status.changeTo(newStatus);
    }*/
}
