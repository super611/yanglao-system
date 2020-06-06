package com.yanglao.user;

import com.yanglao.user.application.port.outbound.UserRepository;
import com.yanglao.user.domain.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("用户管理服务数据库集成测试")
public class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("添加用户信息")
    void test_add_user() {
        Users users = new Users(1, "dengz","邓志辉","15822828380","重庆市");
        userRepository.add(users);
        Users usersRepository = userRepository.queryUser(1);
        assertEquals(users.makeUsersDTO(),usersRepository.makeUsersDTO());
    }

    @Test
    @DisplayName("按照ID查询用户")
    void test_queryUser() {
        Users users= userRepository.queryUser(1);
        Assertions.assertNotNull(users);
    }

    @Test
    @DisplayName("修改密码")
    void test_update() {
        Users users = new Users(1, "dengzhihui","邓志辉","15822828380","重庆市");
        userRepository.update(users);
        Users usersRepository = userRepository.queryUser(1);
        Assertions.assertEquals("dengzhihui",usersRepository.makeUsersDTO().getPassword());
    }
}
