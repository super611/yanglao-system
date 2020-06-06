package com.yanglao.orders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 值对象
 */
@Embeddable
public class Deadline {
    @Column(name = "datetime")
    private LocalDateTime datetime;

    public Deadline() {
    }

    public Deadline(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public boolean isExpired() {
        return this.datetime.isBefore(LocalDateTime.now().plusHours(8));
    }

    public LocalDateTime value() {
        return this.datetime;
    }

}
