package com.yanglao.paymanagement.domain;

import com.yanglao.paymanagement.adapter.inbound.PayDTO;

import javax.persistence.*;

@Entity
@Table(name = "pays")
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String payway;

    public PayDTO makeOrdersDTO() {
        PayDTO dto = new PayDTO();
        dto.setId(this.id);
        dto.setPayway(this.payway);
        return dto;
    }
}
