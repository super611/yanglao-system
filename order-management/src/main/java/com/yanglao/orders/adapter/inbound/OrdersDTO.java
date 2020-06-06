package com.yanglao.orders.adapter.inbound;

import com.yanglao.orders.domain.Deadline;
import com.yanglao.orders.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrdersDTO {

    private String id;
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1,message = "用户ID号码不合法")
    private int userid;

    @NotBlank(message = "姓名不能为空")
    private String username;

    @NotBlank(message = "房间ID号码不能为空")
    private String roomid;

    @NotNull(message = "ID不能为空")
    @Min(value=1,message = "租用房间时长至少为1个月")
    private int term;

    @NotNull(message = "ID不能为空")
    @Min(value = 0,message = "订单总额不能为空")
    private double sum;

    private Deadline datetime;//下单日期
    private Status status;
}
