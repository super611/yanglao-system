package com.yanglao.user.application.port.inbound;

import com.yanglao.user.adapter.inbound.UserDTO;

public interface UserUseCase {

    String addUser(UserDTO userDTO);
    UserDTO queryUser(int id);
    void modifyCode(int id,String password);
}
