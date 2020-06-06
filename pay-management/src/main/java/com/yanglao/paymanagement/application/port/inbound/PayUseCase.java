package com.yanglao.paymanagement.application.port.inbound;

import com.yanglao.paymanagement.adapter.inbound.PayDTO;
import com.yanglao.paymanagement.domain.Pays;

import java.util.List;

public interface PayUseCase {

    List<PayDTO> queryall();
}
