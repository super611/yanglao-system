package com.yanglao.orders.adapter.inbound;

import com.yanglao.orders.application.port.inbound.OrdersUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
class OrdersController {
    private final OrdersUseCase usecase;

    // DTO(Data Transfer Object) 模式
    @PostMapping("/api/orders")
    ResponseEntity<Object> newOrder(
            @Valid @RequestBody OrdersDTO order1, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usecase.createOrder(order1));
    }

    @PutMapping("/api/orders/paid/{id}")
    void paidOrders(@PathVariable String id) {
        usecase.paidOrder(id);
    }

    @PutMapping("/api/orders/close/{id}")
    void closeOrders(@PathVariable String id) {
        usecase.closeOrder(id);
    }

    @GetMapping("/api/orders/{id}/status")
    String getOrdersStatus(@PathVariable String id) {
        return usecase.statusOf(id);
    }

    @GetMapping("/api/orders/all")
    List<OrdersDTO> getAllOrders() {
        return usecase.getAllOrders();
    }
}
