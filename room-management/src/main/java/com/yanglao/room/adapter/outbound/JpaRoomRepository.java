package com.yanglao.room.adapter.outbound;

import com.yanglao.room.application.port.outbound.RoomRepository;
import com.yanglao.room.domain.Rooms;
import com.yanglao.room.domain.RoomsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface JpaRoomRepository extends RoomRepository, JpaRepository<Rooms, RoomsId> {
    @Override
    default void add(Rooms orders) {
        this.save(orders);
    }

    @Override
    default void update(Rooms orders) {
        this.save(orders);
    }

    @Override
    default List<Rooms> queryAll() {
        return this.findAll();
    }

    @Override
    default Rooms queryById(RoomsId id) {
        return this.findById(id).orElse(null);
    }
}
