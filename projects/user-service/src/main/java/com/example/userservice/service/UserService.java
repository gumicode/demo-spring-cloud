package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.domain.UserEntity;
import com.example.userservice.domain.UserRepository;
import com.example.userservice.dto.UserDto;
import com.example.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public void createUser(UserDto userDto) {

        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPwd(passwordEncoder.encode(userEntity.getPwd()));
        userRepository.save(userEntity);
    }

    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new RuntimeException("user not found");
        }
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        log.info("Before call orders microservice");

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
        List<ResponseOrder> orders = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());

        log.info("After call orders microservice");

        userDto.setOrders(orders);
        return userDto;
    }

    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userEntity.getEmail(), userEntity.getPwd(), true, true, true, true, new ArrayList<>());
    }

    public UserDto getUserDetailsByEmail(String username) {
        UserEntity userEntity = userRepository.findByEmail(username);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }
}
