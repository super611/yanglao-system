package com.yanglao.paymanagement.adapter.outbound;

import com.yanglao.paymanagement.application.port.outbound.PayRepositoryUseCase;
import com.yanglao.paymanagement.domain.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayJpaRepositoryService extends PayRepositoryUseCase, JpaRepository<Pays,Integer> {

    @Override
    default List<Pays> queryall() {
        return this.findAll();
    }


}
