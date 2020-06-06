package com.yanglao.room.adapter.inbound;

import com.yanglao.room.domain.Deadline;
import com.yanglao.room.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class RoomsDTO {

    private String id;
    @NotBlank(message = "房间号码不能为空")
    private String room;
    @NotBlank(message = "描述房间信息的字符串长度不能小于10")
    private String detail;
    @Min(value = 100,message = "房间的价格不能小于100")
    private double price;
    private Status status;
    private LocalDateTime deadlinereserve;
    private Deadline deadline;
}
