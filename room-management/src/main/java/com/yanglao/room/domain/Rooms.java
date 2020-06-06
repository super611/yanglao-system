package com.yanglao.room.domain;

import com.yanglao.room.adapter.inbound.RoomsDTO;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "rooms")
public class Rooms {

    @EmbeddedId
    private RoomsId id;//房间ID
    @NotBlank(message = "房间号码不能为空")
    private String room;//房间号
    @Size(min = 10, message = "描述文字不能少于10字")
    private String detail;//描述
    @Min(value = 100,message = "价格不低于100")
    private double price;//价格
    private Status status;//状态
    private LocalDateTime deadlinereserve;//租房预约到期时间
    @Embedded
    private Deadline deadline;//租房到期时间
    // JPA
    public Rooms() {
    }

    // Builder， Factory 创建型模式
    public Rooms(String room, String detail, double price, Deadline deadline,LocalDateTime deadlineReserve) {
        this.id = new RoomsId();
        this.room = room;
        this.detail = detail;
        this.price = price;
        this.status = Status.CREATED;
        this.deadline = deadline;
        this.deadlinereserve=deadlineReserve;
    }

    // 迎合显示用的
    public RoomsDTO makeRoomsDTO() {
        RoomsDTO dto = new RoomsDTO();
        dto.setRoom(this.room);
        dto.setDetail(this.detail);
        dto.setPrice(this.price);
        dto.setStatus(this.status);
        dto.setId(id.value());
        dto.setDeadlinereserve(this.deadlinereserve);
        dto.setDeadline(this.deadline);
        return dto;
    }
    public void empty() {
        status = status.changeTo(Status.EMPTY);
    }
    public void leased() {
        status = status.changeTo(Status.LEASED);
    }
    public void reserve(int term) {
        if(term<=0){
            throw new RoomsException("租用时长至少为一个月");
        }
        status = status.changeTo(Status.RESERVE);
    }
    public void remove() {status = status.changeTo(Status.REMOVED);}

    public Status status() {
        //表示如果房间租用或者预约租用时间到了截止日期，就将会将房间变为空闲
        if(status==Status.LEASED){
            boolean bool=deadline.isExpired();
            if(bool) {
                status = status.changeTo(Status.EMPTY);
                return status;
            }
        }
        if(status==Status.RESERVE){
            boolean bool=deadlinereserve.isBefore(LocalDateTime.now().plus(8, ChronoUnit.HOURS));
            if(bool){
                status = status.changeTo(Status.EMPTY);
                return status;
            }
          }
        return status;
    }
    public void changeDeadline(Deadline deadline){
        this.deadline =deadline;
    }
    public void changeDeadlineReserve(LocalDateTime deadline){
        this.deadlinereserve = deadline;
    }
}
