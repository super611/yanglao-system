package com.yanglao.room.application.port.inbound;

import com.yanglao.room.adapter.inbound.RoomsDTO;

import java.util.List;

public interface RoomsUseCase {
    String createRoom(RoomsDTO roomsDTO);
    void emptyRoom(String id);
    void reserveRoom(String id,int term);
    void leasedRoom(String id);
    void removeRoom(String id);
    List<RoomsDTO> getAllRooms();
    String statusOf(String id);
}
