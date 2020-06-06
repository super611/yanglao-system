package com.yanglao.paymanagement.adapter.inbound;

import com.yanglao.paymanagement.application.PayApplicationService;
import com.yanglao.paymanagement.domain.Pays;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class PayController {

    private final PayApplicationService payApplicationService;
    @GetMapping(value = "/api/pay")
    List<PayDTO> getAll(){
        return payApplicationService.queryall();
    }
}
