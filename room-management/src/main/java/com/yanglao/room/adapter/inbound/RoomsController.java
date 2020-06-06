package com.yanglao.room.adapter.inbound;

import com.yanglao.room.application.port.inbound.RoomsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
class RoomsController {
    private final RoomsUseCase usecase;

    // DTO(Data Transfer Object) 模式
    //创建新的房间
    @PostMapping("/api/rooms")
    public ResponseEntity<Object> newRoom(@Valid @RequestBody RoomsDTO newRoom, BindingResult bindingResult) {
        System.out.println("创建房间");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usecase.createRoom(newRoom));
    }

    //查询所有房间
    @GetMapping("/api/rooms/all")
    List<RoomsDTO> getAllRooms() {
        return usecase.getAllRooms();
    }

    //房间空闲
    @PutMapping("/api/rooms/empty/{id}")
    void publishRooms(@PathVariable String id) {
        usecase.emptyRoom(id);
    }

    //房间已预约
    @PutMapping("/api/rooms/reserve/{id}/{terms}")
    void reserveRooms(@PathVariable String id,@PathVariable int terms) {
        usecase.reserveRoom(id,terms);
    }

    //房间租用
    @PutMapping("/api/rooms/leased/{id}")
    public void leasedRooms(@PathVariable String id) {
        usecase.leasedRoom(id);
    }

    //房间暂停租用
    @PutMapping("/api/rooms/removed/{id}")
    void removeRooms(@PathVariable String id) {usecase.removeRoom(id);}

    //查询房间状态
    @GetMapping("/api/rooms/{id}/status")
    String getRoomsStatus(@PathVariable String id, HttpServletRequest request) {
        return usecase.statusOf(id);
    }


}
