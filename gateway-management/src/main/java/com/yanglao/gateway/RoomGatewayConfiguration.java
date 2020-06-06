package com.yanglao.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RoomGatewayConfiguration {

    /*
    * 只有路由的功能：
    * 首先配置谓词predicate，path("")里面包含请求的路径。
    * filter.stripPrefix(1)表示过滤掉第一个路径的名称
    * url：转发的目标地址
    * */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("room1 service", predicate -> predicate
                        .path("/gateway/api/rooms/all", "/gateway/api/rooms/**").and()
                        .method(HttpMethod.GET)
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("http://room:50001"))
                .route("room2 service", predicate -> predicate
                        .path("/gateway/api/orders").and()
                        .method(HttpMethod.POST)
                        .filters(filter -> filter.stripPrefix(1))
                        .uri("http://order:51001"))
                .build();
    }

    /*
    * 请求的功能：
    * */
    @Bean
    public RouterFunction<ServerResponse> orderHandlerRouting(RoomHandler handler) {
        return RouterFunctions.route(GET("/api/select/allroom"), handler::getOrders);
    }
}
