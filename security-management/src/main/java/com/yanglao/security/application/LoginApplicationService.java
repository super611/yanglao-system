package com.yanglao.security.application;

import com.yanglao.security.adapter.inbound.LoginDTO;
import com.yanglao.security.application.port.inbound.LoginUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class LoginApplicationService implements LoginUseCase {

    private final RestTemplate restTemplate;


    @Override
    public int login(int id, String password) {
        String url = "http://user-management/api/users/" +id;
       try {
           LoginDTO loginDTO=restTemplate.getForObject(url, LoginDTO.class);
           if(loginDTO!=null) {
               if(!password.equals(loginDTO.getPassword())){
                   return 1;
               }
               return 4;
           }else{
               return 2;
           }
       }catch (Exception e){
           return 3;
       }

    }

    @Override
    public String register(LoginDTO loginDTO) {
        String url = "http://user-management/api/users";
        return restTemplate.postForObject(url,loginDTO, String.class);

    }
}
