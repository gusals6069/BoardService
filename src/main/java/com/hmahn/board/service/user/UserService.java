package com.hmahn.board.service.user;

import com.hmahn.board.domain.user.User;
import com.hmahn.board.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }
}
