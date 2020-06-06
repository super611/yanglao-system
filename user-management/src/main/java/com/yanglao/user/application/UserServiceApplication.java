package com.yanglao.user.application;

import com.yanglao.user.adapter.inbound.UserDTO;
import com.yanglao.user.application.port.inbound.UserUseCase;
import com.yanglao.user.application.port.outbound.UserRepository;
import com.yanglao.user.domain.Users;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
class UserServiceApplication implements UserUseCase {

    private final UserRepository userRepository;

    @Override
    public String addUser(UserDTO userDTO) {
        Users users=new Users(0,userDTO.getPassword(),userDTO.getName(),userDTO.getPhone(),userDTO.getAddress());
        userRepository.add(users);
        return String.valueOf(users.makeUsersDTO().getId());
    }

    @Override
    public UserDTO queryUser(int id) {
        Users users= UsersFor(id);
        return users.makeUsersDTO();
    }

    @Override
    public void modifyCode(int id,String password) {
        Users users=UsersFor(id);
        if(users==null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"用户ID不存在");

        }
        users.modifyPassword(password);
        userRepository.update(users);
    }

    private Users UsersFor(int id) {
        Users user = userRepository.queryUser(id);
        return user;
    }
}
