package com.yanglao.gateway;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomHandler {

    public Mono<ServerResponse> getOrders(ServerRequest serverRequest) {
        Flux<Room> rooms = WebClient.create().get()
                .uri("http://room-service:53000/a/rooms")
                .exchange()
                .flatMapMany(resp -> resp.bodyToFlux(Room.class));

        Flux<Order> orders = WebClient.create().get()
                .uri("http://order-service:52000/a/orders")
                .exchange()
                .flatMapMany(resp -> resp.bodyToFlux(Order.class));

        orders = orders.map(order -> {
            return order;
        });

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(orders, Order.class));
    }

}
