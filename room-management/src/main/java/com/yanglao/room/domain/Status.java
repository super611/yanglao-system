package com.yanglao.room.domain;


/**
 * 如果状态管理过于复杂，可以使用状态模式进行重构
 */
public enum Status {
    CREATED,//创建房间
    EMPTY,//空闲
    RESERVE,//已预约
    LEASED,//已租用
    REMOVED;//暂停出租

    public Status changeTo(Status newStatus) {
        // 如果当前状态不是 EMPTY， 那么不允许预约
        if(newStatus == Status.RESERVE){
            if (this != Status.EMPTY) {
                throw new RoomsStatusException("不能预约不是空闲的房间");
            }
        }
        // 如果当前状态不是 RESERVE， 那么不允许租用
        if(newStatus == Status.LEASED){
            if (this != Status.RESERVE){
                throw new RoomsStatusException("不能租用不是预约状态的房间");
            }
        }
        //不能改为创建状态
        if(newStatus == Status.CREATED){
            throw new RoomsStatusException("不能改为创建状态");
        }

        //如果当前状态不是空闲状态，那么不允许停用
        if(newStatus == Status.REMOVED){
            if (this != Status.EMPTY) {
                throw new RoomsStatusException("只有空闲状态才能停用房间");
            }
        }
        return newStatus;
    }
}
