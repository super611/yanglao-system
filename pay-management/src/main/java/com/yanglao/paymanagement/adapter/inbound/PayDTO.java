package com.yanglao.paymanagement.adapter.inbound;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class PayDTO {

    private int id;
    private String payway;
}
