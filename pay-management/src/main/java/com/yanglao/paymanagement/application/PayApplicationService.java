package com.yanglao.paymanagement.application;

import com.yanglao.paymanagement.adapter.inbound.PayDTO;
import com.yanglao.paymanagement.adapter.outbound.PayJpaRepositoryService;
import com.yanglao.paymanagement.application.port.inbound.PayUseCase;
import com.yanglao.paymanagement.domain.Pays;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PayApplicationService implements PayUseCase {

    private final PayJpaRepositoryService payJpaRepositoryService;

    @Override
    public List<PayDTO> queryall() {
        return payJpaRepositoryService.queryall().stream()
                .map(Pays::makeOrdersDTO)
                .collect(Collectors.toList());
    }
}
