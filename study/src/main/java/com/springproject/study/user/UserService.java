package com.springproject.study.user;

import com.springproject.study.utils.CommonQueryDSL;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final CommonQueryDSL commonQueryDSL;
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return commonQueryDSL.selectList(QUser.user);
    }

    public User saveUser(User user) {
        this.isDuplicatedUser(user);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public void isDuplicatedUser(User user) {
        Optional<User> register = userRepository.findUserByEmail(user.getEmail());
        if (register.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
