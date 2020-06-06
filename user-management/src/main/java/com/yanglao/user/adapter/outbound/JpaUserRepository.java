package com.yanglao.user.adapter.outbound;

import com.yanglao.user.application.port.outbound.UserRepository;
import com.yanglao.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
interface JpaUserRepository extends UserRepository, JpaRepository<Users, Integer> {

    @Override
    default void add(Users user) {
        this.save(user);
    }

    @Override
    default Users queryUser(int userid) {
        return this.findById(userid).orElse(null);
    }

    @Override
    default void update(Users user){
        this.save(user);
    }


}
