package com.yanglao.gateway;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Room {
    private String id;
    private String room;
    private String detail;
    private double price;
    private int status;
    private LocalDateTime deadline;
}
