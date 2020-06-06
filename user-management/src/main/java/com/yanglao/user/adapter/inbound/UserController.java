package com.yanglao.user.adapter.inbound;

import com.yanglao.user.application.port.inbound.UserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
class UserController {

    private final UserUseCase userUseCase;

    //添加用户
    @PostMapping("/api/users")
    ResponseEntity<Object> adduser(
            @Valid @RequestBody UserDTO newUser,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userUseCase.addUser(newUser));
    }

    //按ID查询用户
    @GetMapping("/api/users/{id}")
    ResponseEntity<Object> getUser(@PathVariable int id) {
        if (id<=0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户的ID号码至少为1");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userUseCase.queryUser(id));
    }

    //修改密码
    @PutMapping("/api/users/modify/{id}/{password}")
    void modify(@PathVariable int id,@PathVariable String password) {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"用户ID号码最小为1");
        }
        userUseCase.modifyCode(id,password);
    }
}
