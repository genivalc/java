package com.genival.pocredesocial.service;

import com.genival.pocredesocial.domain.User;
import com.genival.pocredesocial.mapper.UserMapper;
import com.genival.pocredesocial.repository.UserRepository;
import com.genival.pocredesocial.requests.UserPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    public User save(UserPostRequest userPostRequest ) {
        return userRepository.save(UserMapper.INSTANCE.toUser(userPostRequest));
    }

}
