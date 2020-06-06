package com.yanglao.security.adapter.inbound;

import com.yanglao.security.application.port.inbound.LoginUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
public class LoginController  {

    private final LoginUseCase loginUseCase;

    //登录
    @ExceptionHandler(SecurityException.class)
    @GetMapping("/api/security/login/{id}/{password}")
    ResponseEntity<Object> login(@PathVariable int id, @PathVariable String password) {
       switch (loginUseCase.login(id,password)){
           case 1:
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败！密码错误");
           case 2:
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败！该账号不存在");
           case 3:
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录验证失败！");
           default:
               return ResponseEntity.status(HttpStatus.CREATED).body("登录成功");
       }

    }

    //注册
    @PostMapping("/api/security/register")
    public ResponseEntity<Object> register(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(loginUseCase.register(loginDTO));

    }

}
