package com.yanglao.room.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

/**
 * 值对象
 */
@Embeddable
public class RoomsId implements Serializable {
    @Column(name = "id")
    private String value;

    public RoomsId() {
        this.value = UUID.randomUUID().toString();
    }

    public static RoomsId of(String id) {
        RoomsId roomsId = new RoomsId();
        try {
            roomsId.value = UUID.fromString(id).toString();
            return roomsId;
        }catch (Exception e){
            throw new RoomsException("该客房ID号码" + id+"不合法！");
        }
    }

    /**
     * 此处没有使用 getValue
     * 也没有使用 Lombok
     */
    public String value() {
        return this.value;
    }

}
