package com.yanglao.gateway;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private String id;
    private int userid;
    private String username;
    private String roomid;
    private int term;
    private double sum;
    private LocalDateTime deadline;
    private int status;
}
