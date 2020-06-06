package com.yanglao.room.application.port.outbound;

import com.yanglao.room.domain.Rooms;
import com.yanglao.room.domain.RoomsId;

import java.util.List;

public interface RoomRepository {
    List<Rooms> queryAll();

    void add(Rooms rooms);

    void update(Rooms roons);

    Rooms queryById(RoomsId id);
}
