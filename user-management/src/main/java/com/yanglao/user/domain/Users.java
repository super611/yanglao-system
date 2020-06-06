package com.yanglao.user.domain;

import com.yanglao.user.adapter.inbound.UserDTO;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    private String password;
    private String name;
    private String phone;
    private String address;
    public Users(){}
    public Users(int id,String password,String name,String phone,String address){
        this.id=id;
        this.password=password;
        this.name=name;
        this.phone=phone;
        this.address=address;
    }

    // 迎合显示用的
    public UserDTO makeUsersDTO() {
        UserDTO dto = new UserDTO();
        dto.setId(this.id);
        dto.setPassword(this.password);
        dto.setName(this.name);
        dto.setPhone(this.phone);
        dto.setAddress(this.address);
        return dto;
    }

    //修改密码
    public void modifyPassword(String newPassword){
        if(newPassword.length()<4){
            throw new UsersException("密码长度至少为4个字符");
        }
        password=newPassword;
    }
}
