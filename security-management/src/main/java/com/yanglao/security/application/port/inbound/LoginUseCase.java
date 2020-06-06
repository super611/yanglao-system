package com.yanglao.security.application.port.inbound;

import com.yanglao.security.adapter.inbound.LoginDTO;

public interface LoginUseCase {

    int login(int id,String password);
    String register(LoginDTO loginDTO);

}
