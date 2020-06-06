package com.yanglao.user.application.port.outbound;

import com.yanglao.user.domain.Users;

public interface UserRepository {
    void add(Users users);
    Users queryUser(int id);
    void update(Users users);
}
