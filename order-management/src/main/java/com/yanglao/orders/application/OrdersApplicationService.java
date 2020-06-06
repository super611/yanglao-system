package com.yanglao.orders.application;

import com.yanglao.orders.adapter.inbound.OrdersDTO;
import com.yanglao.orders.application.port.inbound.OrdersUseCase;
import com.yanglao.orders.application.port.outbound.OrderRepository;
import com.yanglao.orders.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
class OrdersApplicationService implements OrdersUseCase {
    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    private final static String logs="OrdersApplicationService----》";

    @Override
    public String createOrder(OrdersDTO order) {
        //注意此处不能只写服务名称room,k8s中与Nacos服务发现不一样，一定在服务名后面把端口写上room:50001
        String url = "http://room:50000/api/rooms/" + order.getRoomid() + "/status";
        //String url = "http://room-management/api/rooms/" + order.getRoomid() + "/status";
        String status;
        status = restTemplate.getForObject(url, String.class);
        System.out.println(logs+"先查询需要需要的当前的房间状态是：【"+status+"】");
       /* try{
            status = restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            throw new OrdersException("客房管理服务未响应");
        }*/
        if(!status.equalsIgnoreCase("empty")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"该房间不是空闲状态，暂时无法下单！");
        }
        int userid = order.getUserid();
        String username = order.getUsername();
        String roomid = order.getRoomid();
        int term=order.getTerm();
        double sum=order.getSum();
        LocalDateTime localDateTime=LocalDateTime.now().plus(8, ChronoUnit.HOURS);
        Deadline deadline = new Deadline(localDateTime);

        Orders entity = new Orders(userid,username,roomid,term,sum,deadline);
        System.out.print(logs+"entity为："+entity.makeOrdersDTO().toString());
        repository.add(entity);
        String url2 = "http://room:50000/api/rooms/reserve/" + order.getRoomid()+"/"+order.getTerm();

        //String url2 = "http://room-management/api/rooms/reserve/" + order.getRoomid()+"/"+order.getTerm();
        restTemplate.put(url2,null);

        return entity.makeOrdersDTO().getId();
    }
    @Override
    public List<OrdersDTO> getAllOrders() {
        return repository.queryAll().stream()
                .map(Orders::makeOrdersDTO)
                .collect(Collectors.toList());
    }

    private Orders orderFor(String id) {
        Orders orders = repository.queryById(OrdersId.of(id));
        if (orders == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"没有找到指定【id】的订单：" + id);
        }
        return orders;
    }

    @Override
    public void paidOrder(String id) {
        Orders order = orderFor(id);
        OrdersDTO ordersDTO=order.makeOrdersDTO();
        String url = "http://room:50001/api/rooms/" + ordersDTO.getRoomid() + "/status";
        //String url = "http://room-management/api/rooms/" + ordersDTO.getRoomid() + "/status";
        String status = restTemplate.getForObject(url, String.class);
        if(!status.equalsIgnoreCase("reserve")) {
            //支付失败，关闭订单
            order.close();
            repository.update(order);
            new ResponseStatusException(HttpStatus.BAD_REQUEST,"超过订单付款时间，支付失败！");
        }
        order.paid();
        repository.update(order);
        String url2 = "http://room:50001/api/rooms/leased/"+ordersDTO.getRoomid();
        //String url2 = "http://room-management/api/rooms/leased/"+ordersDTO.getRoomid();
        restTemplate.put(url2,String.class);
    }

    @Override
    public void closeOrder(String id) {
        Orders orders = orderFor(id);
        orders.close();
        repository.update(orders);
    }

    @Override
    public String statusOf(String id) {
        return orderFor(id).status().toString();
    }

}
