package com.yanglao.room.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@DisplayName("Deadline的单元测试")
public class DeadlineTests {

    @Test
    @DisplayName("测试是否过期")
    void test_is_expired() {
        Deadline deadline = new Deadline(LocalDateTime.now().plusDays(3));
        Assertions.assertFalse(deadline.isExpired());
    }
}
