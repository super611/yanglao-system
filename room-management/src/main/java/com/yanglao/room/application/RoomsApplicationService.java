package com.yanglao.room.application;

import com.yanglao.room.adapter.inbound.RoomsDTO;
import com.yanglao.room.application.port.inbound.RoomsUseCase;
import com.yanglao.room.application.port.outbound.RoomRepository;
import com.yanglao.room.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
class RoomsApplicationService implements RoomsUseCase {
    private final RoomRepository repository;

    @Override
    public String createRoom(RoomsDTO rooms) {
        String room = rooms.getRoom();
        String detail = rooms.getDetail();
        double price = rooms.getPrice();
        LocalDateTime localDateTime= LocalDateTime.now().plus(8, ChronoUnit.HOURS);

        Deadline deadline = new Deadline(localDateTime);
        Rooms entity = new Rooms(room,detail,price,deadline,localDateTime);
        repository.add(entity);

        //返回刚刚新建的房间id号码
        return entity.makeRoomsDTO().getId();
    }

    @Override
    public List<RoomsDTO> getAllRooms() {
        return repository.queryAll().stream()
                .map(Rooms::makeRoomsDTO)
                .collect(Collectors.toList());
    }

    private Rooms roomFor(String id) {
        Rooms rooms = repository.queryById(RoomsId.of(id));
        if(rooms!=null){
            rooms.status();//判断房间是否到期，再返回状态
        }
        return rooms;
    }

    @Override
    public void emptyRoom(String id) {
        Rooms rooms = roomFor(id);
       if(rooms!=null){
            rooms.empty();
            repository.update(rooms);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"该客房号码不存在");
        }

    }

    @Override
    public void reserveRoom(String id, int term) {
        Rooms rooms = roomFor(id);
        if(rooms!=null){
            //预约时，当前的时间
            LocalDateTime localDateTimeReserve=LocalDateTime.now().plus(8, ChronoUnit.HOURS);

            //租房截止的时间
            LocalDateTime localDateTime=LocalDateTime.now().plus(8, ChronoUnit.HOURS);
            Deadline newDeadline=new Deadline(localDateTime.plus(term, ChronoUnit.MONTHS));

            rooms.changeDeadlineReserve(localDateTimeReserve.plus(30, ChronoUnit.MINUTES));
            rooms.changeDeadline(newDeadline);
            rooms.reserve(term);
            repository.update(rooms);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"该客房号码不存在");
        }
    }

    @Override
    public void leasedRoom(String id) {
        Rooms room_leased = roomFor(id);
        if(room_leased!=null){
            room_leased.leased();
            repository.update(room_leased);
        }else{
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"该客房号码不存在");
        }
    }

    @Override
    public void removeRoom(String id) {
        Rooms rooms_removed = roomFor(id);
        if(rooms_removed!=null){
            rooms_removed.remove();
            repository.update(rooms_removed);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"该客房号码不存在");
        }
    }

    @Override
    public String statusOf(String id) {
        return roomFor(id).status().toString();
    }

}
