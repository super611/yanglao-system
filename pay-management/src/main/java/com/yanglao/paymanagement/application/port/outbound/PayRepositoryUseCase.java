package com.yanglao.paymanagement.application.port.outbound;

import com.yanglao.paymanagement.domain.Pays;

import java.util.List;

public interface PayRepositoryUseCase {

    List<Pays> queryall();
}
