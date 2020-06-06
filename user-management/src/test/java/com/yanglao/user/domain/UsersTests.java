package com.yanglao.user.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UsersTests {

    @Test
    @DisplayName("修改用户密码")
    void test_modify_password(){
        Users user = new Users(2, "dengz", "邓志辉","15822828380","重庆市");
        user.modifyPassword("dengzhihui");
        Assertions.assertEquals("dengzhihui",user.makeUsersDTO().getPassword());
        Assertions.assertThrows(UsersException.class,()-> user.modifyPassword("den"));
    }
}
