package com.example.userservice;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApplicationRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("test1");
        userEntity.setEmail("test1@naver.com");
        userEntity.setName("test1");
        userEntity.setPwd(passwordEncoder.encode("1234"));
        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserId("test2");
        userEntity2.setEmail("test2@naver.com");
        userEntity2.setName("test2");
        userEntity2.setPwd(passwordEncoder.encode("1234"));
        userRepository.save(userEntity2);

    }
}
