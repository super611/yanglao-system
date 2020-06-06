package com.yanglao.security.adapter.inbound;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private int id;
    @Size(min = 4, message = "密码长度至少为4个字符")
    private String password;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "手机号填写错误")
    private String phone;
    @NotBlank(message = "地址不能为空")
    private String address;
}
