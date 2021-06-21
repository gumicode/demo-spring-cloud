package com.example.userservice;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApplicationRunner implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("test1");
        userEntity.setName("test1");
        userEntity.setPwd("1234");
        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserId("test2");
        userEntity2.setName("test2");
        userEntity2.setPwd("1234");
        userRepository.save(userEntity2);

    }
}
